package controller;
import exceptions.ExceptionPersonnalisable;
import model.Commune;
import model.EntrepriseModel;
import model.map.Arc;
import model.map.Itineraire;
import model.map.Plan;
import model.map.Station;
import model.particulier.DemandeCollecte;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class EntrepriseController extends Commune
{
    // ATTRIBUTS
    private Plan p;
    private Station courant;  // où se trouve le camion
    private EntrepriseModel em;
    public EntrepriseController(EntrepriseModel em,Plan p)
    {
        this.p = p;
        this.em = em;
        this.courant = p.getStation("D"); // dépôt
    }
    // L'entreprise récupère la demande du particulier, exécute la demande, et renvoie l'itinéraire au camion.
    //METHODE n°4 : Orchestration complète de la collecte répondant à la liste de demandes
    // Renvoie l'itinéraire optimisé que le camion doit suivre.
    public Itineraire CollecteDemande(Queue<DemandeCollecte> demandes) throws ExceptionPersonnalisable
    {
        if (demandes == null || demandes.isEmpty())
        {
            return null; // aucune demande
        }

        List<Arc> arcsTotaux = new ArrayList<>();
        Station depart = courant; // départ du camion (dépôt)
        List<DemandeCollecte> demandesRestantes = new ArrayList<>(demandes);

        // Boucle while qui parcourt la liste des demandes
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
            Itineraire chemin = em.bfsPlusCourtChemin(depart.getNom(), stationArrivee.getNom());
            arcsTotaux.addAll(chemin.getChemin()); // ajouter les arcs de ce chemin à l'itinéraire total

            // Marquer la demande comme traitée
            em.defilerDemande(plusProche);
            demandesRestantes.remove(plusProche);

            // Mettre à jour le point de départ pour la prochaine boucle
            depart = stationArrivee;
        }

        // Renvoie au Camion le chemin à faire.
        return new Itineraire(courant, depart, arcsTotaux);
    }
}
