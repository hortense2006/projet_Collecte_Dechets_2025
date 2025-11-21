package model;
import exceptions.ExceptionPersonnalisable;
import model.map.*;
import model.particulier.*;
import java.util.*;

public class EntrepriseModel
{
    // ATTRIBUTS
    private Plan p;
    private Station courant;  // où se trouve le camion
    private ParticulierModel pm;    // demandes restantes

    // CONSTRUCTEUR
    public EntrepriseModel(Plan p)
    {
        this.p = p;
        this.courant = p.getStation("D"); // dépôt
    }

    // METHODE n°1 : Calcul du plus court chemin  ( méthode bsf)
    public Itineraire bfsPlusCourtChemin (String nomDepart, String nomArrivee) throws ExceptionPersonnalisable
    {
        Station depart = verifierStations(nomDepart, nomArrivee); // verifie qu'il n'y a pas d'exception
        Station destination = p.getStation(nomArrivee); // arc par lequel on découvre la station
        Map<Station, Arc> predecesseurs = new HashMap<>(); //mémoire pour garder toutes les stations passées
        Queue<Arc> fileArcs = new LinkedList<>();
        predecesseurs.put(depart, null);
        fileArcs.addAll(depart.getArcsSortants());
        Station stationArriveeTrouvee = null;
        while (!fileArcs.isEmpty() && stationArriveeTrouvee == null)
        { // tant que le fichier n'est pas vide et qu'on est pas arrivé
            Arc arcCourant = fileArcs.poll(); //initialise l'arc sur lequel on est
            Station successeur = arcCourant.getArrivee(); // regarde la station qui arrive
            if (!predecesseurs.containsKey(successeur))
            { //pour ne pas tourner en rond
                predecesseurs.put(successeur, arcCourant); //met en mémoir l'arc qu'on vient de fair et la station d'avant
                if (successeur.equals(destination))
                { // arrête si l'arrivé est trouver
                    stationArriveeTrouvee = successeur;
                }
                else
                {
                    fileArcs.addAll(successeur.getArcsSortants()); //sinon on continue
                }
            }
        }
        return reconstituerChemin(depart, destination, stationArriveeTrouvee, predecesseurs);//pour afficher grace à la méthode dans afficher itinéraire
    }


    // METHODE n°2 : Vérification des stations
    public Station verifierStations(String nomDepart, String nomArrivee) throws ExceptionPersonnalisable
    {
        Station depart = p.getStation(nomDepart);
        Station arrivee = p.getStation(nomArrivee);
        if (depart == null)
        {
            throw new ExceptionPersonnalisable("Station de départ inconnue: " + nomDepart);
        }
        if (arrivee == null)
        {
            throw new ExceptionPersonnalisable("Station de d'arrivée inconnue: " + nomArrivee);
        }
        if (depart.equals(arrivee))
        {
            throw new ExceptionPersonnalisable("Départ et Arrivée identiques.");
        }
        return arrivee;
    }

    // METHODE n°3 : reconstitution du trajet parcouru
    private Itineraire reconstituerChemin(Station depart, Station arrivee, Station stationArriveeTrouvee, Map<Station, Arc> predecesseurs)
    { //méthode qui servira à l'affichage d'un chemin
        if (stationArriveeTrouvee == null)
        {
            return new Itineraire(depart, arrivee, Collections.emptyList());
        }
        LinkedList<Arc> cheminInverse = new LinkedList<>(); //retrace le chemin à l'envers
        Station courant = arrivee;
        while (predecesseurs.get(courant) != null)
        { //si on a une information avant
            Arc arcEntrant = predecesseurs.get(courant);
            cheminInverse.addFirst(arcEntrant);
            courant = arcEntrant.getDepart();
        }
        return new Itineraire(depart, arrivee, new ArrayList<>(cheminInverse)); //retourne pour l'affichage
    }
    //METHODE n°4 : Exécuter la demande
    // Deux cas possibles : exécution immédiate ou au bout de 5 requêtes
    public Itineraire executerDemande(DemandeCollecte demande,String Nomarrivee)
    {
        // Récupérer la maison du particulier (rue)
        Station depart = p.getStationP(demande.getRue(), demande.getNumero());

        // Récupérer la station d'arrivée
        Station arrivee = p.getStation(Nomarrivee); // Station d'arrivée

        // Calcul du plus court chemin
        Itineraire itineraire = bfsPlusCourtChemin(depart.getNom(), arrivee.getNom());

        // Retourner l'itinéraire
        return itineraire;
    }

    // METHODE n°5 :  Méthode dijkstra
    // Méthode Dijkstra qui renvoie directement la station demandée la plus proche
    /*public String dijkstra(Station depart, List<DemandeCollecte> demandes)
    {
        // 1. Calculer les distances depuis la station de départ
        Map<Station, Double> dist = dijkstraStations(depart);

        DemandeCollecte demandePlusProche = null;
        double distanceMin = Double.MAX_VALUE;

        // 2. Parcourir toutes les demandes pour trouver la plus proche
        for (DemandeCollecte d : demandes) {
            double dProfil = distanceVersProfil(dist, d.getProfil());
            if (dProfil < distanceMin) {
                distanceMin = dProfil;
                demandePlusProche = d;
            }
        }

        // 3. Retourner le nom de la station la plus proche sur l'arc du profil
        if (demandePlusProche != null) {
            Profil p = demandePlusProche.getProfil();
            Arc rue = p.getRue();
            double pos = p.getPosition();
            double distDebut = dist.get(rue.getDepart()) + pos;
            double distFin   = dist.get(rue.getArrivee()) + (rue.getDistance() - pos);
            Station arrivee = (distDebut <= distFin) ? rue.getDepart() : rue.getArrivee();

            return arrivee.getNom(); // ou getId() selon ce que tu veux afficher
        }

        return null; // Aucun résultat
    }*/

    public Map<Station, Double> dijkstraStations(Station depart)
    {
        Map<Station, Double> dist = new HashMap<>();
        PriorityQueue<Station> pq = new PriorityQueue<>(Comparator.comparingDouble(dist::get));

        // Initialisation
        for (Station s : p.getStations().values()) {
            dist.put(s, Double.MAX_VALUE);
        }
        dist.put(depart, 0.0);
        pq.add(depart);

        while (!pq.isEmpty())
        {
            Station courant = pq.poll();

            for (Arc arc : courant.getArcsSortants())
            {
                Station voisin = arc.getArrivee();
                double newDist = dist.get(courant) + arc.getDistance();

                if (newDist < dist.get(voisin))
                {
                    dist.put(voisin, newDist);
                    pq.remove(voisin);
                    pq.add(voisin);
                }
            }
        }

        return dist;
    }
    /*public double distanceVersProfil(Map<Station, Double> dist, Profil profil)
    {
        Arc rue = profil.getRue();
        double pos = profil.getPosition();

        // Distances jusqu’aux extrémités de l’arc
        double distDebut = dist.get(rue.getDepart());
        double distFin   = dist.get(rue.getArrivee());

        // Distance interne dans la rue
        double viaDebut = distDebut + pos;
        double viaFin   = distFin + (rue.getDistance() - pos);

        return Math.min(viaDebut, viaFin);
    }*/
}
