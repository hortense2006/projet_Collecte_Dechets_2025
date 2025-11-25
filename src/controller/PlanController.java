package controller;

import model.map.Plan;
import view.PlanView;

import java.io.IOException;
import java.util.Scanner;

public class PlanController {
    private Plan plan;
    private PlanView planView;

    public PlanController(Plan plan, PlanView planView) {
        this.plan = plan;
        this.planView = planView;
    }

    //Menu qui permet de choisir le type de fichier qu'on veut et l'affiche
    public Plan choixFichier(Plan p) {
        String nomFichier = "";
        int choix = 0;
        while (choix >=3  || choix <= 1) {
        Scanner sc = new Scanner(System.in);
        choix = planView.afficherMenu();

            switch (choix) { //applique le type de plan que nous utilisons
                case 1:
                    nomFichier = "Ranville_HO1.txt";

                    try {
                        p.chargerPlan(nomFichier, Plan.modeOrientation.HO1_NON_ORIENTE);
                        planView.afficherMessagePlan("\n");
                        return p;
                    } catch (IOException e) { // lance l'erreur necessaire
                        planView.afficherErreurPlan("ERREUR : Impossible de lire le fichier du réseau.");
                        planView.afficherErreurPlan("Détail de l'erreur: " + e.getMessage());
                    }
                    break;
                case 2:
                    nomFichier = "Ranville_HO2.txt";
                    try {
                        p.chargerPlan(nomFichier, Plan.modeOrientation.HO2_ORIENTE);
                        planView.afficherMessagePlan("\n");
                        return p;
                    } catch (IOException e) {
                        planView.afficherErreurPlan("ERREUR : Impossible de lire le fichier du réseau.");
                        planView.afficherErreurPlan("Détail de l'erreur: " + e.getMessage());
                    }
                    break;
                case 3:
                    nomFichier = "Ranville_HO3.txt";
                    try {
                        plan.chargerPlan(nomFichier, Plan.modeOrientation.HO3_MIXTE);
                        planView.afficherMessagePlan("\n");
                        return p;
                    } catch (IOException e) {
                        planView.afficherErreurPlan("ERREUR : Impossible de lire le fichier du réseau.");
                        planView.afficherErreurPlan("Détail de l'erreur: " + e.getMessage());
                    }
                    break;
            }
        }
        return p = null;
    }
}

