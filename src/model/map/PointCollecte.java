package model.map;

import java.io.*;

public class PointCollecte extends Station {

    private int capaciteMax;
    private int niveauRemplissage;

    private static final String FICHIER_SAUVEGARDE_RANVILLE = "Etat_point_collecte_Ranville.txt";
    private static final String FICHIER_SAUVEGARDE_BORDEAUX = "Etat_point_collecte_Bordeaux.txt";

    public PointCollecte(String nom, int capaciteMax) {
        super(nom);
        this.capaciteMax = capaciteMax;
    }

    public String toString() {
        return "PointDeCollecte : " + getNom();
    }
    public int getNiveauRemplissage () {return this.niveauRemplissage;}
    public int getCapaciteMax() {return this.capaciteMax;}


    public void setNiveauRemplissage (int niveauRemplissage) {this.niveauRemplissage = niveauRemplissage;}

    public void remplir(int quantite) { //pour qu'un particulier puisse jeter ces dechets en points de collecte
        if (quantite < 0) {
            System.out.println("Erreur : On ne peut pas ajouter une quantité négative.");
            return;
        }
        if (this.niveauRemplissage + quantite > capaciteMax) { // on vérifie si ça déborde
            this.niveauRemplissage = capaciteMax;
            System.out.println("Attention : Le conteneur " + getNom() + " est plein !");
        } else {
            this.niveauRemplissage += quantite;
        }
    }

    public static void sauvegarderEtatRanville(Plan plan) { // sauvegarde à la fin du programme
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FICHIER_SAUVEGARDE_RANVILLE))) {
            for (Station s : plan.getStations().values()) { // parcours de l'intégralité du fichier
                if (s instanceof PointCollecte) {
                    PointCollecte pc = (PointCollecte) s;
                    writer.write(pc.getNom() + ";" + pc.getNiveauRemplissage()); // on écrit le point et la quantité
                    writer.newLine();
                }
            }

        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde des déchets : " + e.getMessage());
        }
    }

    public static void sauvegarderEtatBordeaux(Plan plan) { // sauvegarde à la fin du programme
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FICHIER_SAUVEGARDE_BORDEAUX))) {
            for (Station s : plan.getStations().values()) { // parcours de l'intégralité du fichier
                if (s instanceof PointCollecte) {
                    PointCollecte pc = (PointCollecte) s;
                    writer.write(pc.getNom() + ";" + pc.getNiveauRemplissage()); // on écrit le point et la quantité
                    writer.newLine();
                }
            }

        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde des déchets : " + e.getMessage());
        }
    }

    public static void chargerEtatRanville(Plan plan) { //permet de récupere des fois d'avant ce qu'il y avait dans les PDC

        File file = new File(FICHIER_SAUVEGARDE_RANVILLE);
        if (!file.exists()) return; // Si pas de fichier, on commence à 0

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                String[] parts = ligne.split(";");
                if (parts.length == 2) {
                    String nom = parts[0];
                    int quantite = Integer.parseInt(parts[1]);
                    Station s = plan.getStation(nom);// cherche le point dans le plan
                    if (s instanceof PointCollecte) {
                        ((PointCollecte) s).setNiveauRemplissage(quantite);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement des déchets : " + e.getMessage());
        }
    }

    public static void chargerEtatBordeaux(Plan plan) { //permet de récupere des fois d'avant ce qu'il y avait dans les PDC

        File file = new File(FICHIER_SAUVEGARDE_BORDEAUX);
        if (!file.exists()) return; // Si pas de fichier, on commence à 0

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                String[] parts = ligne.split(";");
                if (parts.length == 2) {
                    String nom = parts[0];
                    int quantite = Integer.parseInt(parts[1]);
                    Station s = plan.getStation(nom);// cherche le point dans le plan
                    if (s instanceof PointCollecte) {
                        ((PointCollecte) s).setNiveauRemplissage(quantite);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement des déchets : " + e.getMessage());
        }
    }
}
