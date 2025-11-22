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
    private ParticulierModel pm;
    // Liste tampon pour stocker les demandes en attente
    private List<DemandeCollecte> demandesTampon = new ArrayList<>();
    private final int SEUIL = 10; // Nombre de demandes à attendre avant exécution

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

    //METHODE n°4 : Orchestration complète de la collecte répondant à la liste de demandes
    // Renvoie l'itinéraire optimisé que le camion doit suivre.
    public Itineraire CollecteDemande(List<DemandeCollecte> demandes) throws ExceptionPersonnalisable
    {
        if (demandes == null || demandes.isEmpty())
        {
            return null; // aucune demande
        }

        List<Arc> arcsTotaux = new ArrayList<>();
        Station depart = courant; // départ du camion (dépôt)
        List<DemandeCollecte> demandesRestantes = new ArrayList<>(demandes);

        while (!demandesRestantes.isEmpty())
        {
            // Trouver la demande la plus proche du point de départ actuel
            DemandeCollecte plusProche = null;
            double minDistance = Double.MAX_VALUE;

            for (DemandeCollecte d : demandesRestantes)
            {
                Station s = p.getStationP(d.getRue(), d.getNumero());
                // Pour simplifier on compare les numéros (notation américaine)
                double distanceApprox = Math.abs(d.getNumero() - 0); // approximation simple
                if (distanceApprox < minDistance)
                {
                    minDistance = distanceApprox;
                    plusProche = d;
                }
            }

            // Calculer le chemin jusqu'à cette demande
            Station stationArrivee = p.getStationP(plusProche.getRue(), plusProche.getNumero());
            Itineraire chemin = bfsPlusCourtChemin(depart.getNom(), stationArrivee.getNom());
            arcsTotaux.addAll(chemin.getChemin()); // ajouter les arcs de ce chemin à l'itinéraire total

            // Marquer la demande comme traitée
            defilerDemande(plusProche);
            demandesRestantes.remove(plusProche);

            // Mettre à jour le point de départ pour la prochaine boucle
            depart = stationArrivee;
        }

        // Retourner l’itinéraire complet (depuis dépôt jusqu’au dernier point)
        return new Itineraire(courant, depart, arcsTotaux);
    }

    //METHODE n°5 : Retirer une demande après exécution
    public void defilerDemande(DemandeCollecte demande) throws ExceptionPersonnalisable
    {
        Queue<DemandeCollecte> liste = pm.getDemande();
        if (!liste.remove(demande))
        {
            throw new ExceptionPersonnalisable("Demande non trouvée dans la file !");
        }
    }

}
