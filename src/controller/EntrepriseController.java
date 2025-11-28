package controller;
import exceptions.ExceptionPersonnalisable;
import model.EntrepriseModel;
import model.map.*;
import model.particulier.DemandeCollecte;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class EntrepriseController
{
    // ATTRIBUTS
    private Plan p;
    private Station courant;  // où se trouve le camion
    private EntrepriseModel em;
    private Maison maison;
    public EntrepriseController(EntrepriseModel em,Plan p,Maison maison)
    {
        this.p = p;
        this.em = em;
        this.maison = maison;
        this.courant = p.getStation("D"); // dépôt
    }

    //METHODE n°4 : Orchestration complète de la collecte répondant à la liste de demandes
    // Renvoie l'itinéraire optimisé que le camion doit suivre.
    // L'entreprise récupère la demande du particulier, exécute la demande, et renvoie l'itinéraire au camion.
    public Itineraire CollecteDemande(Queue<DemandeCollecte> demandes) throws ExceptionPersonnalisable
    {
        if (demandes == null || demandes.isEmpty())
        {
            return null; // aucune demande
        }

        List<Arc> arcsTotaux = new ArrayList<>();
        Station depart = courant; // départ du camion (dépôt)
        List<DemandeCollecte> demandesRestantes = new ArrayList<>(demandes);
        System.out.println("Stations chargées : " + p.getStations().keySet());
        System.out.println("Station D : " + p.getStation("D"));

        if (courant == null)
        {
            throw new ExceptionPersonnalisable("Le dépôt du camion n'est pas défini.");
        }

        // Boucle while qui parcourt la liste des demandes
        while (!demandesRestantes.isEmpty())
        {
            // Trouver la demande la plus proche du point de départ actuel
            DemandeCollecte plusProche = null;
            double minDistance = Double.MAX_VALUE;

            for (DemandeCollecte d : demandesRestantes)
            {
                double distanceApprox = depart.distanceVers(d); // calculer distance depuis depart
                if (distanceApprox < minDistance) {
                    minDistance = distanceApprox;
                    plusProche = d;
                }
            }

            // Calculer le chemin jusqu'à cette demande
            if (plusProche == null)
            {
                throw new ExceptionPersonnalisable("Station nulle.");
            }
            Station stationArrivee = maison.creerMaison(plusProche.getRue(), plusProche.getNumero());
            Itineraire chemin = em.bfsPlusCourtChemin(depart.getNom(), stationArrivee.getNom());
            arcsTotaux.addAll(chemin.getChemin()); // ajouter les arcs de ce chemin à l'itinéraire total

            // Marquer la demande comme traitée
            em.defilerDemande(plusProche); // On la supprime du fichier texte des demandes
            demandesRestantes.remove(plusProche); // On la supprime de la liste des demandes

            // Mettre à jour le point de départ pour la prochaine boucle
            depart = stationArrivee;
        }
        // Renvoie au Camion le chemin à faire.
        return new Itineraire(courant, depart, arcsTotaux);
    }
}
