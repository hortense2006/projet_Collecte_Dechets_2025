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
                    nomFichier = "C:\\Users\\valsa\\IdeaProjects\\Théorie des graphes\\projet_Collecte_Dechets_2025\\src\\Ranville_HO1.txt";
                    //c'est un absolute path tu peux le changer pour qu'il fonctionne sur ton PC si tu veux
                    // suffit de clic droit sur le fichier HO, et faire get absolute path et encore get absolut path et coller
                    //merci de n'utiliser aucune autre forme que tu absolute path

                    try {
                        p.chargerPlan(nomFichier, Plan.modeOrientation.HO1_NON_ORIENTE);
                        System.out.println("Plan de la ville : ");
                        planView.afficherMessagePlan("\n");
                        return p;
                    } catch (IOException e) { // lance l'erreur necessaire
                        planView.afficherErreurPlan("ERREUR : Impossible de lire le fichier du réseau.");
                        planView.afficherErreurPlan("Détail de l'erreur: " + e.getMessage());
                    }
                    break;
                case 2:
                    nomFichier = "C:\\Users\\valsa\\IdeaProjects\\Théorie des graphes\\projet_Collecte_Dechets_2025\\src\\Ranville_HO2.txt";
                    try {
                        p.chargerPlan(nomFichier, Plan.modeOrientation.HO2_ORIENTE);
                        System.out.println("Plan de la ville : ");
                        planView.afficherMessagePlan("\n");
                        return p;
                    } catch (IOException e) {
                        planView.afficherErreurPlan("ERREUR : Impossible de lire le fichier du réseau.");
                        planView.afficherErreurPlan("Détail de l'erreur: " + e.getMessage());
                    }
                    break;
                case 3:
                    nomFichier = "C:\\Users\\valsa\\IdeaProjects\\Théorie des graphes\\projet_Collecte_Dechets_2025\\src\\Ranville_HO3.txt";
                    try {
                        plan.chargerPlan(nomFichier, Plan.modeOrientation.HO3_MIXTE);
                        System.out.println("Plan de la ville : ");
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

