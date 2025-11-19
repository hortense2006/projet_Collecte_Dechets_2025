package controller;

import model.map.Arc;
import model.map.Plan;
import model.map.Station;
import view.PlanView;
import view.TourneeView;

import java.util.*;

public class Tournee {

    PlanView planV;
    TourneeView TourneeV;

    public void lancerCalculTournee(Plan plan) {

        List<Station> sommetsImpairs = trouverSommetsImpairs(plan);// Analyse pour identifier le cas
        int nbImpairs = sommetsImpairs.size();

        planV.afficherMessagePlan("Nombre de sommets de degré impair : " + nbImpairs);

        // choix en fonction du type de pla
        if (nbImpairs == 0) {
            planV.afficherMessagePlan("Cas 1 : double sens");
            Station depart = trouverDepot(plan);
            if (depart != null) {
                List<String> tournee = algorithmeHierholzer(plan, depart);
                TourneeV.afficherResultat(tournee);
            } else {
                planV.afficherErreurPlan("Erreur : Aucun dépôt 'D' trouvé pour commencer la tournée.");
            }
        }
        else if (nbImpairs == 2) {
            planV.afficherMessagePlan(" Cas 2 : sens unique");
            System.out.println("Le camion doit partir de " + sommetsImpairs.get(0).getNom() +
                    " et finir à " + sommetsImpairs.get(1).getNom() + " (ou vice-versa).");
            System.out.println("Pour revenir au dépôt, il faudra emprunter une route une deuxième fois.");
        }
        else {
            planV.afficherMessagePlan(" Cas 3 : réaliste");
        }
    }


    private List<String> algorithmeHierholzer(Plan plan, Station depart) {

        Map<String, List<Arc>> grapheTemp = copierGraphe(plan);// copie pour "brûler" les arcs

        List<String> circuit = new ArrayList<>(); // Liste des noms des stations visitées
        Stack<Station> pile = new Stack<>();

        pile.push(depart);
        Station courant = depart;

        while (!pile.isEmpty()) {

            if (grapheTemp.containsKey(courant.getNom()) && !grapheTemp.get(courant.getNom()).isEmpty()) {

                pile.push(courant);
                List<Arc> voisins = grapheTemp.get(courant.getNom());// On prend le premier arc disponible
                Arc arcPris = voisins.get(0);
                supprimerArc(grapheTemp, arcPris);// Supprime l'arc de la copie
                courant = arcPris.getArrivee();// continue
            }
            else {// Si on est bloqué, on dépile et on ajoute au circuit final
                Station s = pile.pop();
                circuit.add(0, s.getNom()); // Ajout au début pour reconstruire dans l'ordre
                if (!pile.isEmpty()) {
                    courant = pile.peek();
                }
            }
        }
        return circuit;
    }

    private List<Station> trouverSommetsImpairs(Plan plan) {
        List<Station> impairs = new ArrayList<>();
        for (Station s : plan.getStations().values()) {

            if (s.getArcsSortants().size() % 2 != 0) {// Si le nombre de routes connectées est impair
                impairs.add(s);
            }
        }
        return impairs;
    }

    private Station trouverDepot(Plan plan) {
        for (Station s : plan.getStations().values()) {
            if (s.getNom().startsWith("D")) return s;
        }
        if (!plan.getStations().isEmpty()) return plan.getStations().values().iterator().next();
        return null;
    }

    // Fait une copie manuelle du graphe
    private Map<String, List<Arc>> copierGraphe(Plan plan) {
        Map<String, List<Arc>> copie = new HashMap<>();
        for (Station s : plan.getStations().values()) {
            // On crée une nouvelle liste contenant les mêmes arcs
            copie.put(s.getNom(), new ArrayList<>(s.getArcsSortants()));
        }
        return copie;
    }

    // Supprime l'arc A->B et son retour B->A (car en HO1, c'est la même rue)
    private void supprimerArc(Map<String, List<Arc>> graphe, Arc arc) {
        String nomA = arc.getDepart().getNom();
        String nomB = arc.getArrivee().getNom();
        if (graphe.containsKey(nomA)) {
            graphe.get(nomA).remove(arc);
        }
        if (graphe.containsKey(nomB)) {
            List<Arc> arcsDeB = graphe.get(nomB);
            Arc arcRetour = null;
            for (Arc a : arcsDeB) {
                if (a.getArrivee().getNom().equals(nomA)) {
                    arcRetour = a;
                    break;
                }
            }
            if (arcRetour != null) {
                arcsDeB.remove(arcRetour);
            }
        }
    }

}
