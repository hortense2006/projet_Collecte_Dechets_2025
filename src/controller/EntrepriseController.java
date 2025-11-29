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
                // CRÉER LA STATION CANDIDATE POUR VÉRIFIER SA COHÉRENCE AVEC LE PLAN
                Station stationCandidate = maison.creerMaison(d.getRue(), d.getNumero());
                // --- VÉRIFICATION CLÉ (Ajout) ---
                if (stationCandidate == null || stationCandidate.getNom().equals(depart.getNom()))
                {
                    // Si la demande est nulle, ou si elle se trouve à la même station que le camion (le dépôt "D" au départ), on l'ignore.
                    continue;
                }
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
            //em.defilerDemande(plusProche); // On la supprime du fichier texte des demandes
            demandesRestantes.remove(plusProche); // On la supprime de la liste des demandes

            // Mettre à jour le point de départ pour la prochaine boucle
            depart = stationArrivee;
        }
        // --- NOUVELLE ÉTAPE : Retour au dépôt ---
        Station stationDepot = p.getStation("D"); // On récupère la station de dépôt
        // Calculer le chemin de la dernière maison visitée ('depart') au dépôt
        Itineraire cheminRetour = em.bfsPlusCourtChemin(depart.getNom(), stationDepot.getNom());
        if (cheminRetour != null)
        {
            arcsTotaux.addAll(cheminRetour.getChemin()); // Ajout des arcs du retour
            depart = stationDepot; // Met à jour 'depart' pour être le dépôt final
        }
        this.courant = depart;
        // Renvoie au Camion le chemin à faire.
        return new Itineraire(courant, depart, arcsTotaux);
    }
}
