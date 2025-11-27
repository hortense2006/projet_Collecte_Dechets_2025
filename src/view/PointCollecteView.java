package view;

import java.util.Scanner;

public class PointCollecteView {

    private Scanner sc;

    public PointCollecteView() {
        this.sc = new Scanner(System.in);
    }

    // Affiche un message simple
    public void afficherMessage(String message) {
        System.out.println(message);
    }


    public String demanderNomPoint() { // Demande à l'habitant le nom du point
        System.out.println("Entrez le nom du point de collecte oùu vous allez jeter vos déchets : ");
        return sc.nextLine().trim();
    }


    public int demanderQuantite() { // Demande à l'habitant la quantité
        System.out.println ("Entrez la quantité de déchet que vous allez jeter : ");
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1; // Code d'erreur si ce n'est pas un nombre
        }
    }
}
