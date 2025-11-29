package model;

import exceptions.ExceptionPersonnalisable;
import model.map.Arc;
import model.map.Itineraire;
import model.map.Station;

import java.io.*;
import java.util.*;

public class CamionModel {

    private String idCamion; // Identifiant unique
    private double capaciteMax; // en kg
    private double capaciteActuelle; // en kg
    private String etat; // Statut du camion

    private static final String FICHIER = "Camion_ville.txt";

    public CamionModel(String idCamion, String etat,double capaciteMax, double capaciteActuelle )
    {
        this.idCamion = idCamion;
        this.capaciteMax = capaciteMax;
        this.capaciteActuelle = capaciteActuelle;
        this.etat = etat;
    }

    public String getIdCamion() {
        return idCamion;
    }
    public double getCapaciteActuelle() {
        return capaciteActuelle;
    }
    public double getCapaciteMax() {
        return capaciteMax;
    }
    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }
    public void setCapaciteActuelle(double capaciteActuelle)
    {
        this.capaciteActuelle = capaciteActuelle;
    }
    public void setIdCamion(String idCamion)
    {
        this.idCamion = idCamion;
    }

    // verifie si le camion n'est pas plein
    public boolean aDeLaPlace(double quantite) {
        return (this.capaciteActuelle + quantite) <= this.capaciteMax;
    }

    // permet de remplir le camion
    public void chargerDechets(double quantite) {
        if (aDeLaPlace(quantite)) {
            this.capaciteActuelle += quantite;
        } else {
            System.err.println("Erreur : Surcharge du camion !");
        }
    }

    public void viderCamion() {
        this.capaciteActuelle = 0;
        System.out.println("-> Le camion a été vidé au dépôt.");
    }

    @Override
    public String toString() {
        return "ID: " + idCamion + " | Capacité: " + capaciteMax + " | État: " + etat;
    }

    // permet de lire le fichier de camion et de créer les camions
    public static List<CamionModel> chargerCamions() {
        List<CamionModel> liste = new ArrayList<>();
        File file = new File(FICHIER);
        if (!file.exists()) {
            System.err.println("ERREUR : Le fichier " + FICHIER + " est introuvable !");
            return liste;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                ligne = ligne.trim();
                String[] parts = ligne.split(";");
                if (parts.length == 4) {
                    try {
                        String id = parts[0].trim();
                        String etat = parts[1].trim();
                        double max = Double.parseDouble(parts[2].trim());
                        double actuel = Double.parseDouble(parts[3].trim());
                        liste.add(new CamionModel(id, etat, max, actuel)); // création d'un camion
                    } catch (NumberFormatException e) {
                        System.err.println("Erreur de format numérique sur la ligne : " + ligne);
                    }
                } else {
                    System.err.println("Ligne ignorée (Format incorrect, attendu 4 colonnes) : " + ligne); // si ce n'est pas conforme on rejette
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur lecture camions : " + e.getMessage());
        }
        return liste;
    }

    //réécrit le fichier si on décide de choisir un camion et donc changer l'état
    public static void changerEtatCamion(String idCamion, String nouvelEtat) {
        List<CamionModel> tousLesCamions = chargerCamions();
        for (CamionModel c : tousLesCamions) { // modification de la liste
            if (c.getIdCamion().equals(idCamion)) {
                c.setEtat(nouvelEtat);
                break;
            }
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FICHIER))) { // réécriture du fichier
            for (CamionModel c : tousLesCamions) {
                bw.write(c.getIdCamion() + ";" +
                        c.getEtat() + ";" +
                        (int)c.getCapaciteMax() + ";" +
                        (int)c.getCapaciteActuelle());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erreur sauvegarde camions : " + e.getMessage());
        }
    }

    public static void mettreAJourCamion(CamionModel camionModifie) {
        List<CamionModel> tous = chargerCamions();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FICHIER))) {
            for (CamionModel c : tous) {
                // Si c'est le camion qu'on vient de modifier, on prend ses nouvelles valeurs
                if (c.getIdCamion().equals(camionModifie.getIdCamion())) {
                    bw.write(camionModifie.getIdCamion() + ";" +
                            camionModifie.getEtat() + ";" +
                            (int)camionModifie.getCapaciteMax() + ";" +
                            (int)camionModifie.getCapaciteActuelle());
                } else {
                    // Sinon on réécrit l'ancien
                    bw.write(c.getIdCamion() + ";" +
                            c.getEtat() + ";" +
                            (int)c.getCapaciteMax() + ";" +
                            (int)c.getCapaciteActuelle());
                }
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erreur sauvegarde : " + e.getMessage());
        }
    }



}
