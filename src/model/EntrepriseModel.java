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

    //METHODE n°4 : Exécuter la demande
    // Deux cas possibles : exécution immédiate ou au bout de 5 requêtes
    public Itineraire executerDemande(DemandeCollecte demande)
    {
        // Ajouter la demande à la liste tampon
        demandesTampon.add(demande);
        // Récupérer la maison du particulier (rue)
        Station depart = p.getStationP(demande.getRue(), demande.getNumero());
        if(demandesTampon.size()>= SEUIL)
        {
            // On part de la position du camion (au dépôt)
            // Récupérer la station d'arrivée
            String arrivee = dijkstra(depart.getNom()); // Station d'arrivée
            // Calcul du plus court chemin
            Itineraire itineraire = bfsPlusCourtChemin(depart.getNom(), arrivee);
            // Retourner l'itinéraire
            return itineraire;
        }
        return null;
    }

    public String dijkstra(String depart)
    {
        // File prioritaire
        PriorityQueue<DemandeCollecte> maisonArrivee = new PriorityQueue<>(Comparator.comparingDouble(DemandeCollecte::getNumero));

        // Charger la liste des demandes
        Queue<DemandeCollecte> liste = pm.getDemande();
        // On récupère la station de départ
        Station stationDepart = p.getStation(depart);

        if (liste == null || liste.isEmpty())
        {
            return null;
        }
        // On remplit une liste prioritaire avec les stations d'arrivée
        // Chaque demande est associée à une lieu (rue+ numero)

        // Méthode Dijkstra
        while (!liste.isEmpty()) // Tant que la liste des demandes est pas vide
        {
            // On classe les demandes de la plus proche à la plus lointaine (en fonction de numero)
            // numero est un double
            // On remplit la liste prioritaire maisonArrivee
            maisonArrivee.add(liste.poll());
            // On récupère la première maison (la plus proche)
            DemandeCollecte plusProche = maisonArrivee.peek();
            // On renvoie le nom de la rue de la première demande
            return plusProche.getRue();
        }
        return null;
    }

    //METHODE n°1 : Retirer une demande après exécution
    public void defilerDemande(DemandeCollecte demande) throws ExceptionPersonnalisable
    {
        //DemandeCollecte prochaineDemande = demande.poll(); // retire en FIFO
    }
}
