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

    public void afficherListeSecteurs(List<Secteurs> liste) {
        System.out.println("\n-Choix du secteur à faire");
        for (int i = 0; i < liste.size(); i++) {
            System.out.println((i + 1) + ". " + liste.get(i).toString());
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

    public boolean demanderConfirmation(String nomSecteur) {
        System.out.println("Attention : Le secteur " + nomSecteur + " est déjà marqué comme BLOQUÉ.");
        System.out.println("Voulez-vous quand même le refaire ? (oui/non)");
        String rep = sc.nextLine();
        return rep.equalsIgnoreCase("oui");
    }
}
