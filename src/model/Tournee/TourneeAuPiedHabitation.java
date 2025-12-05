package model.tournee;


import model.CamionModel;
import model.secteurs.Secteurs;
import model.map.Depot;
import model.map.Itineraire;
import model.map.Plan;
import model.map.Station;
import view.PlanView;

import java.util.ArrayList;
import java.util.List;

public class TourneeAuPiedHabitation {

    private Plan plan;
    private Dijkstra dijkstra;
    private PlanView planV = new PlanView(); // Pour les logs algorithmiques
    private final double DECHETS_PAR_ARRET = 50.0;

    public TourneeAuPiedHabitation(Plan plan) {
        this.plan = plan;
        this.dijkstra = new Dijkstra(plan);
    }

    public TourneePointCollecte calculerTournee(CamionModel camion, Secteurs secteur) {
        Station here = null;
        Depot depot = null;
        for (Station s : plan.getStations().values()) { // recupere de depot
            if (s instanceof Depot) {
                here = s;
                depot = (Depot) s;
                break;
            }
        }
        if (here == null) {
            planV.afficherErreurPlan("Pas de dépôt trouvé !"); // si on a pas de depot on arrete
            return null;
        }
        List<Station> aVisiter = secteur.getStationsDuSecteur(plan);
        if (aVisiter.isEmpty()) {
            planV.afficherErreurPlan("Le secteur est vide ou mal lu.");
            return null;
        }

        planV.afficherMessagePlan("\nDépart tournée pour " + secteur.getNom());

        TourneePointCollecte resultat = new TourneePointCollecte(plan);// stocke l'itineraire de ce qu'on fait
        Itineraire cheminUtils = new Itineraire(null, null, new ArrayList<>());

        while (!aVisiter.isEmpty()) { // boucle pour faire le chemin
            ResultatDijkstra res = dijkstra.dijkstra(here);
            Station meilleur = null;
            double minDist = Double.MAX_VALUE;

            for (Station s : aVisiter) {
                double d = res.distances.getOrDefault(s, Double.MAX_VALUE);
                if (d < minDist) {
                    minDist = d;
                    meilleur = s;
                }
            }

            if (meilleur == null) break;

            if (!camion.aDeLaPlace(DECHETS_PAR_ARRET)) { // verification de si le camion est plein ou non
                System.out.println(" Le camion est plein !!! (" + (int)camion.getCapaciteActuelle() + "). Arrêt immédiat et retour au dépot ");

                ResultatDijkstra resRetour = dijkstra.dijkstra(here); // on retourne au depot
                Itineraire segmentRetour = cheminUtils.reconstituerChemin(here, depot, depot, resRetour.predecesseurs);
                resultat.listeSeg.add(segmentRetour);
                resultat.distanceTotale += resRetour.distances.get(depot);

                here = depot;
                break; // fin de la tournée
            }

            // DÉPLACEMENT & COLLECTE
            Itineraire segment = cheminUtils.reconstituerChemin(here, meilleur, meilleur, res.predecesseurs);
            resultat.listeSeg.add(segment);
            resultat.distanceTotale += minDist;

            camion.chargerDechets(DECHETS_PAR_ARRET);
            System.out.println(" Passage à " + meilleur.getNom() + " (+50)");

            here = meilleur;
            aVisiter.remove(meilleur);
        }

        if (!here.equals(depot)) {// Retour au depot
            ResultatDijkstra resRetour = dijkstra.dijkstra(here);
            Itineraire segmentFinal = cheminUtils.reconstituerChemin(here, depot, depot, resRetour.predecesseurs);
            resultat.listeSeg.add(segmentFinal);
            resultat.distanceTotale += resRetour.distances.get(depot);
            System.out.println("Secteur terminé. Retour au dépôt.");
        }

        // Mise à jour de l'état du secteur
        secteur.setEtat(true); // Marqué comme fait/bloqué
        System.out.println("\n Secteur " + secteur.getNom() + " marqué comme bloqué.");

        return resultat; // On renvoie le résultat
    }
}
