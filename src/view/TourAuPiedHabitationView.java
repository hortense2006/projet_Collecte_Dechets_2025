package view;

import model.secteurs.Secteurs;

import java.util.List;
import java.util.Scanner;

public class TourAuPiedHabitationView {

    private Scanner sc;

    public TourAuPiedHabitationView() {
        this.sc = new Scanner(System.in);
    }

    public void afficherMessage(String msg) {
        System.out.println(msg);
    }

    public void afficherErreur(String msg) {
        System.err.println(msg);
    }

    public void afficherListeSecteurs(List<Secteurs> listeSecteurs) {
        System.out.println("\n Choisir le secteur : ");

        for (int i = 0; i < listeSecteurs.size(); i++) {
            Secteurs s = listeSecteurs.get(i);
            String etatInfo = "";
            List<Secteurs> tousLesSecteurs = List.of();
            if (s.getEtat()) {
                etatInfo = " Bloqué";
            } else if (s.aUnVoisinBloque(tousLesSecteurs)) {
                // C'est ici qu'on utilise la 2ème liste pour vérifier les voisins
                etatInfo = " Interdit : le voisin à été fait ";
            } else {
                etatInfo = " Disponible";
            }
            System.out.println((i + 1) + ". " + s.toString() + etatInfo);
        }
    }

    public int demanderChoixSecteur() {
        System.out.print("\nEntrez le numéro du secteur que vous voulez faire : ");
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
