package model;

import exceptions.ExceptionPersonnalisable;
import model.map.Arc;
import model.map.Itineraire;
import model.map.Station;

import java.util.*;

public class Camion
{
    // ATTRIBUTS
    private String idCamion;                       // Identifiant unique
    private double capaciteMax;                    // Charge maximale (kg ou L)
    private double capaciteActuelle;               // Charge actuelle
    private boolean disponible; // Statut du camion
    private Station posCamion;

    // CONSTRUCTEUR
    public Camion(String idCamion, double capaciteMax)
    {
        this.idCamion = idCamion;
        this.capaciteMax = capaciteMax;
    }

    // GETTER n°1
    public void getIdCamion(String idCamion)
    {
        this.idCamion = idCamion;
    }
    // GETTER n°2
    public void getCapaciteActuelle(double capaciteActuelle)
    {
        this.capaciteActuelle = capaciteActuelle;
    }

    // METHODE n°1 : Chargement du camion
    public void chargerDechets(double quantite){}
    // METHODE n°2 : Vider le camion
    public void viderCamion(){}
    // METHODE n°3 : Planification de la tournée
    public void planifierTournee(){}
    // METHODE n°4 : Faire la tournée
    public void executerTournee(){}
    // METHODE n°5 : Afficher l'itinéraire
    public void afficherItineraire(){}
    // METHODE n°6 : Affinage du plus court chemin grâce au temps de trajet
    public Itineraire dijkstra(String nomDepart) throws ExceptionPersonnalisable
    {
        // Récupération du réseau de stations de base
        Map<String, Station> quais = p.getGraphe(p); // ta méthode GrapheDesQuais
        Station stationDepart = p.getStation(nomDepart);
        Station stationArrivee = p.getStation(plusProcheVoisin(nomDepart));

        if (stationDepart == null || stationArrivee == null) {
            throw new ExceptionPersonnalisable("Station inconnue : " + nomDepart + " ou " + p.getStation(plusProcheVoisin(nomDepart)));
        }

        // Initialisation des structures de Dijkstra
        Map<Station, Double> distance = new HashMap<>();
        Map<Station, Arc> predecesseur = new HashMap<>();
        PriorityQueue<Station> file = new PriorityQueue<>(Comparator.comparingDouble(distance::get));

        // Initialiser toutes les distances à ∞
        for (Station s : quais.values()) {
            distance.put(s, Double.POSITIVE_INFINITY);
        }

        // Point de départ : tous les quais du départ
        for (String ligne : stationDepart.getIdLignes()) {
            String nomQuai = creerQuai(stationDepart.getNom(), ligne);
            Station quaiDepart = quais.get(nomQuai);
            if (quaiDepart != null) {
                distance.put(quaiDepart, 0.0);
                file.add(quaiDepart);
            }
        }

        // Algorithme de Dijkstra
        Station arriveeTrouvee = null;
        Set<String> quaisArrivee = new HashSet<>();
        for (String ligne : stationArrivee.getIdLignes()) {
            quaisArrivee.add(creerQuai(stationArrivee.getNom(), ligne));
        }

        while (!file.isEmpty()) {
            Station courant = file.poll();

            // Si on atteint un quai de la station d'arrivée → fin
            if (quaisArrivee.contains(courant.getNom())) {
                arriveeTrouvee = courant;
                break;
            }

            for (Arc arc : courant.getArcsSortants()) {
                Station voisin = arc.getArrivee();
                double nouvelleDistance = distance.get(courant) + arc.getDistance();

                if (nouvelleDistance < distance.get(voisin)) {
                    distance.put(voisin, nouvelleDistance);
                    predecesseur.put(voisin, arc);
                    file.remove(voisin);
                    file.add(voisin);
                }
            }
        }

        // Reconstitution du chemin
        if (arriveeTrouvee == null) {
            System.out.println("Aucun itinéraire trouvé entre " + nomDepart + " et " + nomArrivee);
            return new Itineraire(stationDepart, stationArrivee, Collections.emptyList());
        }

        List<Arc> chemin = new ArrayList<>();
        Station actuelle = arriveeTrouvee;
        while (predecesseur.containsKey(actuelle)) {
            Arc arc = predecesseur.get(actuelle);
            chemin.add(0, arc);
            actuelle = arc.getDepart();
        }

        double dureeTotale = distance.get(arriveeTrouvee);
        System.out.printf(" Itinéraire trouvé (temps total = %.2f min)\n", dureeTotale);

        return new Itineraire(stationDepart, stationArrivee, chemin);
    }

}
