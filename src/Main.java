import Exceptions.ExceptionPersonnalisable;
import map.Plan;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        // ATTRIBUTS
        String typeUser;
        int choixUser;
        Scanner sc = new Scanner(System.in);
        Utilisateur user;
        Plan plan = new Plan();
        boolean exit = false;

        String nomFichier = "";
        String choixPlan;

        while (exit != true) {

            System.out.println ("Sur quel type de plan voulez vous vous baser : " +
                    "\n - HO1 : à bouble sens" +
                    "\n - HO2 : à sens unique" +
                    "\n - HO3 : réaliste");
            choixPlan = sc.nextLine();

            switch (choixPlan) {
                case "HO1":
                    nomFichier = "C:\\Users\\valsa\\IdeaProjects\\Théorie des graphes\\projet_Collecte_Dechets_2025\\src\\Ranville_HO1.txt";
                    try {
                        plan.chargerPlan(nomFichier, Plan.modeOrientation.HO1_NON_ORIENTE);
                        System.out.println("Plan de la ville : ");
                        plan.afficherReseau();
                        System.out.println("\n");
                    }
                    catch (IOException e) {
                        System.err.println("ERREUR : Impossible de lire le fichier du réseau.");
                        System.err.println("Détail de l'erreur: " + e.getMessage());
                        return;
                    }
                    break;
                case "HO2":
                    nomFichier = "C:\\Users\\valsa\\IdeaProjects\\Théorie des graphes\\projet_Collecte_Dechets_2025\\src\\Ranville_HO2.txt";
                    try {
                        plan.chargerPlan(nomFichier, Plan.modeOrientation.HO2_ORIENTE);
                        System.out.println("Plan de la ville : ");
                        plan.afficherReseau();
                        System.out.println("\n");
                    }
                    catch (IOException e) {
                        System.err.println("ERREUR : Impossible de lire le fichier du réseau.");
                        System.err.println("Détail de l'erreur: " + e.getMessage());
                        return;
                    }
                    break;
                case "HO3":
                    nomFichier = "C:\\Users\\valsa\\IdeaProjects\\Théorie des graphes\\projet_Collecte_Dechets_2025\\src\\Ranville_HO3.txt";
                    try {
                        plan.chargerPlan(nomFichier, Plan.modeOrientation.HO3_MIXTE);
                        System.out.println("Plan de la ville : ");
                        plan.afficherReseau();
                        System.out.println("\n");
                    }
                    catch (IOException e) {
                        System.err.println("ERREUR : Impossible de lire le fichier du réseau.");
                        System.err.println("Détail de l'erreur: " + e.getMessage());
                        return;
                    }
                    break;
            }

            System.out.println("Choisissez votre profil : " +
                    "\n - commune" +
                    "\n - particulier" +
                    "\n - entreprise");
            typeUser = sc.nextLine();
            switch (typeUser)
            {
                case "commune":
                {
                    System.out.println("Que souhaitez-vous faire :");
                    System.out.println("1. Consulter le plan de Ranville.");
                    System.out.println("2. Mettre à jour les informations de la commune");
                }
                case "particulier":
                {
                    user = new Particuliers(typeUser);
                    System.out.println("Que souhaitez-vous faire :");
                    System.out.println("1. Connexion");
                    System.out.println("2. Demande de collecte");
                    System.out.println("3. Consulter le planning de ramassage");
                    System.out.println("4. Quitter");
                    choixUser = sc.nextInt();
                    switch (choixUser)
                    {
                        case 1:
                        {
                            user.login();
                            break;
                        }
                        case 2:
                        {
                            user.faireDemandeCollecte("particulier");
                            break;
                        }
                        case 3:
                        {
                            user.consulterPlanningRamassage("ranville");
                            break;
                        }
                        case 4:
                            exit = true;
                            System.exit(0);// Sortir du programme
                            break;
                        default: {throw new ExceptionPersonnalisable("Wrong choice");}
                    }

                } case "entreprise":{} //je ne pense pas qu'une case entreprise soit necessaire c'est uniquement la commune et un utilisateur qui gére le passage et trajet du camion

                default:{throw new ExceptionPersonnalisable("Wrong choice");}
            }
        }
    }
}