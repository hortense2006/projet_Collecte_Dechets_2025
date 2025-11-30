package view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class PointCollecteView
{

    // ATTRIBUTS
    private Scanner sc;

    // CONSTRUCTEUR
    public PointCollecteView() {
        this.sc = new Scanner(System.in);
    }

    // METHODE n°1 : Affiche un message simple
    public void afficherMessage(String message) {
        System.out.println(message);
    }

    // METHODE n°2 : Demande à l'habitant le nom du point
    public String demanderNomPoint() {
        System.out.println("Entrez le nom du point de collecte oùu vous allez jeter vos déchets : ");
        return sc.nextLine().trim();
    }

    // METHODE n°3 : Demander la quantite de déchets à déposer
    public int demanderQuantite() { // Demande à l'habitant la quantité
        System.out.println ("Entrez la quantité de déchet que vous allez jeter : ");
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1; // Code d'erreur si ce n'est pas un nombre
        }
    }

    // METHODE n°4 : Le point de collecte est-il plein ou pas ?
    public static void afficherEtatPointCollecte() {
        File file = new File("Etat_point_de_collecte.txt");

        if (!file.exists()) {
            System.err.println("Le fichier n'existe pas");
            return;
        }

        System.out.println("\n Etat des points de collecte");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String ligne;
            boolean estVide = true;
            while ((ligne = reader.readLine()) != null) {
                String[] parts = ligne.split(";");
                if (parts.length == 2) {
                    String nom = parts[0];
                    String quantite = parts[1];
                    System.out.println(" - Point " + nom + " : " + quantite + " déchets stockés");
                    estVide = false;
                }
            }
            if (estVide) {
                System.out.println("Le fichier est vide.");
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier : " + e.getMessage());
        }
    }

}
