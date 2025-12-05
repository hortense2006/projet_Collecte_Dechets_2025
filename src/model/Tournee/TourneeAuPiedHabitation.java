package model.tournee;

import model.CamionModel;
import model.map.*;
import model.secteurs.Secteurs;
import view.PlanView;

import java.util.ArrayList;
import java.util.LinkedList;
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
        for (Station s : plan.getStations().values()) { // tourver le depot
            if (s instanceof Depot) {
                here = s;
                depot = (Depot) s;
                break;
            }
        }
        if (here == null) {
            planV.afficherErreurPlan("Erreur critique : Pas de dépôt trouvé.");
            return null;
        }
        List<Station> aVisiter = secteur.getStationsDuSecteur(plan);
        if (aVisiter.isEmpty()) {
            planV.afficherErreurPlan("Le secteur est vide.");
            return null;
        }
        planV.afficherMessagePlan("\nDépart tournée pour " + secteur.getNom());
        TourneePointCollecte resultat = new TourneePointCollecte(plan);
        while (!aVisiter.isEmpty()) { // veirifie que le camion ne se retrouvera pas bloqué
            ResultatDijkstra res = dijkstra.dijkstra(here);
            Station meilleur = null;
            double minDist = Double.MAX_VALUE;
            for (Station s : aVisiter) { // le meilleur candidat ou nous ne resterons pas bloqué
                double d = res.distances.getOrDefault(s, Double.MAX_VALUE);
                if (d < minDist) {
                    ResultatDijkstra testRetour = dijkstra.dijkstra(s); // verifie que le retour au depot est toujours possible depuis le point suivant
                    double distRetour = testRetour.distances.getOrDefault(depot, Double.MAX_VALUE);

                    if (distRetour < Double.MAX_VALUE) { // on peut y aller et revenir au depot
                        minDist = d;
                        meilleur = s;
                    } else {
                        System.out.println("Point ignoré (pas de retour possible) : " + s.getNom());
                    }
                }
            }
            if (meilleur == null) { // retour au depot si rien n'eest accessible
                System.out.println("Plus aucun point accessible avec retour garanti.");
                break;
            }
            if (!camion.aDeLaPlace(DECHETS_PAR_ARRET)) { // on verifie que le camio n'est pas plein
                System.out.println(" !!! CAMION PLEIN. Retour dépôt.");

                ResultatDijkstra resRetour = dijkstra.dijkstra(here);
                ajouterSegmentAuResultat(here, depot, resRetour, resultat);

                here = depot;
                break; // FIN
            }
            ajouterSegmentAuResultat(here, meilleur, res, resultat);
            camion.chargerDechets(DECHETS_PAR_ARRET); // collecte
            System.out.println(" -> Passage à " + meilleur.getNom() + " (+50)");

            here = meilleur;
            aVisiter.remove(meilleur);
        }
        if (!here.equals(depot)) { // retour final au depot
            ResultatDijkstra resRetour = dijkstra.dijkstra(here);
            if (ajouterSegmentAuResultat(here, depot, resRetour, resultat)) {
                planV.afficherMessagePlan("Secteur terminé. Retour au dépôt.");
            } else {
                planV.afficherErreurPlan("Erreur critique : Blocage inattendu."); // on est resté bloqué
            }
        }
        secteur.setEtat(true);
        planV.afficherMessagePlan("\nBilan : Secteur marqué comme BLOQUÉ.");
        return resultat;
    }

    private boolean ajouterSegmentAuResultat(Station depart, Station arrivee, ResultatDijkstra res, TourneePointCollecte resultatGlobal) { // permet de reconstruire le chemin
        double dist = res.distances.getOrDefault(arrivee, Double.MAX_VALUE);// récupère les distance
        if (dist >= Double.MAX_VALUE) return false;
        LinkedList<Arc> cheminArcs = new LinkedList<>(); // on reconstruit manuellement le chemin
        Station courant = arrivee;
        while (!courant.equals(depart)) {
            Arc arcPrecedent = res.predecesseurs.get(courant);
            if (arcPrecedent == null) {
                break;
            }
            cheminArcs.addFirst(arcPrecedent);
            courant = arcPrecedent.getDepart();
        }
        if (!cheminArcs.isEmpty() || depart.equals(arrivee)) { // ajoute le résultat si le chemin est valide
            Itineraire segment = new Itineraire(depart, arrivee, new ArrayList<>(cheminArcs));
            resultatGlobal.listeSeg.add(segment);
            resultatGlobal.distanceTotale += dist;
            return true;
        }
        return false;
    }
}
