package view;

import model.CamionModel;

import java.util.List;
import java.util.Scanner;

public class CamionView {

    private Scanner sc;

    public CamionView() {
        this.sc = new Scanner(System.in);
    }

    public void afficherMessage(String message) {
        System.out.println(message);
    }

    public void afficherErreur(String message) {
        System.err.println(message); // Affiche en rouge dans la console souvent
    }

    public void afficherListeCamions(List<CamionModel> camions) {
        System.out.println("\n-Liste des camions");
        System.out.printf("%-15s | %-15s | %s%n", "ID", "État", "Capacité");
        System.out.println("\n");
        for (CamionModel c : camions) {
            System.out.printf("%-15s | %-15s | %s%n",
                    c.getIdCamion(),
                    c.getEtat(),
                    (int)c.getCapaciteMax());
        }
    }

    public String demanderIdCamion() {
        System.out.print("Entrez l'identifiant du camion à utiliser : ");
        return sc.nextLine().trim();
    }
}
