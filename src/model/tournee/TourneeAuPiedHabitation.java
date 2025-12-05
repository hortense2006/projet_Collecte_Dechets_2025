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
        for (Station s : plan.getStations().values()) {// trouve le depot
            if (s instanceof Depot) {
                here = s;
                depot = (Depot) s;
                break;
            }
        }
        if (here == null) {
            planV.afficherErreurPlan("Erreur : Pas de dépôt trouvé.");
            return null;
        }
        List<Station> aVisiter = secteur.getStationsDuSecteur(plan);// recupere les point du secteur
        if (aVisiter.isEmpty()) {
            planV.afficherErreurPlan("Le secteur " + secteur.getNom() + " est vide (aucune station trouvée).");
            return null;
        }

        planV.afficherMessagePlan("\nDépart tournée pour " + secteur.getNom());

        TourneePointCollecte resultat = new TourneePointCollecte(plan);
        Itineraire cheminUtils = new Itineraire(null, null, new ArrayList<>());
        while (!aVisiter.isEmpty()) { // commence la navigation dans le plan
            ResultatDijkstra res = dijkstra.dijkstra(here);
            Station meilleur = null;
            double minDist = Double.MAX_VALUE;
            for (Station s : aVisiter) { // trouver le point le plus proche de soi
                double d = res.distances.getOrDefault(s, Double.MAX_VALUE);
                if (d < minDist) {
                    minDist = d;
                    meilleur = s;
                }
            }
            if (meilleur == null) break; // si aucun poit on sort d'où nous sommes
            if (!camion.aDeLaPlace(DECHETS_PAR_ARRET)) { // verifie que le camion n'est pas plein
                System.out.println(" Le camion est plein !!! (" + (int)camion.getCapaciteActuelle() + "). Arrêt immédiat et retour au dépot ");
                ResultatDijkstra resRetour = dijkstra.dijkstra(here);// retour au depot
                if (resRetour.distances.getOrDefault(depot, Double.MAX_VALUE) < Double.MAX_VALUE) { //verifie si le retour est possible
                    Itineraire segmentRetour = cheminUtils.reconstituerChemin(here, depot, depot, resRetour.predecesseurs);
                    resultat.listeSeg.add(segmentRetour);
                    resultat.distanceTotale += resRetour.distances.get(depot);
                } else {
                    System.out.println("Erreur critique : Impossible de rentrer au dépôt !");
                }

                here = depot;
                break; // fin de la torunée
            }
            Itineraire segment = cheminUtils.reconstituerChemin(here, meilleur, meilleur, res.predecesseurs);
            resultat.listeSeg.add(segment);
            resultat.distanceTotale += minDist;
            camion.chargerDechets(DECHETS_PAR_ARRET); // collecte des déchets
            System.out.println(" Passage à " + meilleur.getNom() + " (+50)");

            here = meilleur;
            aVisiter.remove(meilleur);
        }
        if (!here.equals(depot)) { // retour final (si on a fait tout le quartier)
            ResultatDijkstra resRetour = dijkstra.dijkstra(here);
            double distRetour = resRetour.distances.getOrDefault(depot, Double.MAX_VALUE);

            if (distRetour < Double.MAX_VALUE) {
                Itineraire segmentFinal = cheminUtils.reconstituerChemin(here, depot, depot, resRetour.predecesseurs);
                resultat.listeSeg.add(segmentFinal);
                resultat.distanceTotale += distRetour;
                System.out.println("Secteur terminé. Retour au dépôt.");
            }
        }
        secteur.setEtat(true); // bloque le secteur
        System.out.println("\n Secteur " + secteur.getNom() + " marqué comme bloqué.");

        return resultat;
    }
}
