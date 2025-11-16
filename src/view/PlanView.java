package view;

import model.map.Arc;
import model.map.Station;
import model.map.Plan;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PlanView {

    private Scanner scanner;

    public PlanView() {
        this.scanner = new Scanner(System.in);
    }

    public void afficherReseau(Plan plan) { //affiche le plan de la ville chargée
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

}
