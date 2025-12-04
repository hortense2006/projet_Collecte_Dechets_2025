package view;

import controller.PlanController;
import model.map.Arc;
import model.map.Station;
import model.map.Plan;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PlanView {

    private Scanner sc;

    public PlanView() {
        this.sc = new Scanner(System.in);
    }

    // METHODE n°1 : Affiche le plan de la ville chargée
    public void afficherReseau(Plan plan) {
        System.out.println("Affichage du plan de la ville");
        if (plan.getStations().isEmpty()) {
            System.out.println("Le plan est vide.");
            return;
        }
        for (Station station : plan.getStations().values()) {
            System.out.print("(" + station.getNom() + " - " + station.getClass().getSimpleName() + ") est relié à : "); //affiche un point
            if (station.getArcsSortants().isEmpty()) { // si la route ne mene à rien
                System.out.println("Aucune autre station.");
            } else {
                List<String> voisins = new ArrayList<>();
                for (Arc arc : station.getArcsSortants()) {
                    voisins.add(arc.getArrivee().getNom()); //affiche son voisin
                }
                System.out.println(String.join(", ", voisins)); //continue
            }
        }
    }

    // METHODE n°2 : Affichage du menu du graphe
    public int afficherMenu () {
        int choix;
        Scanner sc = new Scanner(System.in);

        System.out.println("\n" +//permet le choix du type de plan que nous utiliserons
                "Sur quel type de plan voulez vous vous baser : " +
                "\n" +
                "\n 1 : à double sens" +
                "\n 2 : à sens unique" +
                "\n 3 : réaliste");
        choix = sc.nextInt();

        return choix;
    }

    // METHODE n°3 : Afficher une message
    public void afficherMessagePlan (String message) {
        System.out.println(message);
    }

    // METHODE n°4 : Afficher un message d'erreur
    public void afficherErreurPlan (String message) {
        System.err.println(message);
    }

    public Plan ChoixVille (Plan p){
        int choixVille;
        PlanController pc = new PlanController(p, this);
        System.out.println("Veuillez choisir la ville que vous voulez tester : " +
                "\n 1. Ranville" +
                "\n 2. Bordeaux");
        choixVille = sc.nextInt();
        switch (choixVille) {
            case 1:
            {
                p = pc.choixFichierRanville(p);
                return p;
            }
            case 2:
            {
                p = pc.choixFichierBordeaux(p);
                return p;
            }
        }
        return null;
    }

}
