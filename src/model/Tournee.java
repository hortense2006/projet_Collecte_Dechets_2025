package model;

import model.map.Arc;
import model.map.Plan;
import model.map.Station;
import view.PlanView;
import view.TourneeView;

import java.util.*;

public class Tournee {

    PlanView planV;
    TourneeView tourneeV;
    Arc arc;
    CamionModel camionM;

    public void calculTournee (Plan plan) {
        List<Station> sommetsImpairs = trouverSommetsImpairs(plan);
        int nbImpairs = sommetsImpairs.size();

        planV.afficherMessagePlan("Calcul de la tournée");
        planV.afficherMessagePlan("Analyse : " + nbImpairs + " sommets impairs détectés.");

        if (nbImpairs == 0) {
            planV.afficherMessagePlan("Cas 1 : sommets paires");
        }
        else if (nbImpairs == 2) {
            planV.afficherMessagePlan("Cas 2 : sommet paires + 2 impaires ");
        }
        else {
            planV.afficherMessagePlan("Cas 3 : réaliste");
        }
        Station depart = trouverDepot(plan); // récupère la position du dépot
        if (depart == null) {
            planV.afficherErreurPlan("Erreur : Pas de dépôt trouvé !");
            return;
        }
        List<String> resultat = camionM.CamionEnTournee(plan, depart); // départ du camion

        // 4. Affichage via ta vue
        // Si ta TourneeView a une méthode afficherResultat, utilise-la.
        // Sinon, on affiche ici pour l'instant :
        System.out.println("\n--- TRAJET FINAL DU CAMION ---");
        System.out.println(String.join(" -> ", resultat));
        System.out.println("Nombre d'étapes : " + resultat.size());
    }

    // --- ALGORITHME DE DIJKSTRA (Adapté pour trouver la rue sale la plus proche) ---
    public List<Station> dijkstraVersPlusProcheRueSale(Plan plan, Station depart, List<Arc> ruesSales) {

        // 1. Initialisation classique Dijkstra
        Map<Station, Double> distances = new HashMap<>();
        Map<Station, Station> predecesseurs = new HashMap<>();
        PriorityQueue<Station> filePriorite = new PriorityQueue<>(Comparator.comparingDouble(distances::get));

        for (Station s : plan.getStations().values()) {
            distances.put(s, Double.MAX_VALUE);
        }
        distances.put(depart, 0.0);
        filePriorite.add(depart);

        Station cibleTrouvee = null;

        // 2. Boucle Dijkstra
        while (!filePriorite.isEmpty()) {
            Station u = filePriorite.poll();

            // CONDITION D'ARRÊT : Est-ce que 'u' est connecté à une rue sale ?
            for (Arc a : u.getArcsSortants()) {
                if (arc.contientArc(ruesSales, a)) {
                    cibleTrouvee = u;
                    break;
                }
            }
            if (cibleTrouvee != null) break; // On a trouvé le plus proche !

            if (distances.get(u) == Double.MAX_VALUE) break;

            for (Arc voisinArc : u.getArcsSortants()) {
                Station v = voisinArc.getArrivee();
                double newDist = distances.get(u) + voisinArc.getDistance();

                if (newDist < distances.get(v)) {
                    distances.put(v, newDist);
                    predecesseurs.put(v, u);
                    filePriorite.remove(v); // Refresh priorité
                    filePriorite.add(v);
                }
            }
        }

        // 3. Reconstruction du chemin
        List<Station> chemin = new ArrayList<>();
        if (cibleTrouvee != null) {
            Station curr = cibleTrouvee;
            while (curr != depart) {
                chemin.add(0, curr);
                curr = predecesseurs.get(curr);
            }
            // On n'ajoute pas le départ pour ne pas le doubler
        }
        return chemin;
    }

    //permet de trouver uen rue salle plus loin si on a que des rues propres à coté
    public List<Station> dijkstraPrécis(Plan plan, Station depart, Station arrivee) {
        Map<Station, Station> pred = new HashMap<>();
        Map<Station, Double> dist = new HashMap<>();
        PriorityQueue<Station> q = new PriorityQueue<>(Comparator.comparingDouble(dist::get));

        for (Station s : plan.getStations().values()) dist.put(s, Double.MAX_VALUE);
        dist.put(depart, 0.0);
        q.add(depart);

        while (!q.isEmpty()) {
            Station u = q.poll();
            if (u.equals(arrivee)) break; // On s'arrête quand on trouve la cible

            if (dist.get(u) == Double.MAX_VALUE) break;

            for (Arc a : u.getArcsSortants()) {
                Station v = a.getArrivee();
                if (dist.get(u) + a.getDistance() < dist.get(v)) {
                    dist.put(v, dist.get(u) + a.getDistance());
                    pred.put(v, u);
                    q.remove(v);
                    q.add(v);
                }
            }
        }
        List<Station> chemin = new ArrayList<>();
        Station courrant = arrivee;
        while (courrant != null && !courrant.equals(depart)) {
            chemin.add(0, courrant);
            courrant = pred.get(courrant);
        }
        return chemin;
    }

    // Helper pour gérer le fait qu'en HO1, A->B et B->A sont la même rue


    public Station trouverDepot(Plan plan) {
        for (Station s : plan.getStations().values()) {
            if (s.getNom().startsWith("D")) return s;
        }
        if (!plan.getStations().isEmpty()) return plan.getStations().values().iterator().next();
        return null;
    }

    public List<Station> trouverSommetsImpairs(Plan plan) {
        List<Station> impairs = new ArrayList<>();
        for (Station s : plan.getStations().values()) {
            if (s.getArcsSortants().size() % 2 != 0) impairs.add(s);
        }
        return impairs;
    }
}
