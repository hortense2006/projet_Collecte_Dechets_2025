package view;

import java.util.List;

public class TourneeView {

    public void afficherResultat(List<String> circuit) {
        System.out.println("\nTournée calculer (" + (circuit.size()-1) + " trajets) :");
        System.out.println(String.join(" - ", circuit));
        System.out.println("Le camion est revenu à son point de départ.");
    }
}
