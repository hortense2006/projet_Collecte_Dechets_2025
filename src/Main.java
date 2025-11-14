import Exceptions.ExceptionPersonnalisable;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        // ATTRIBUTS
        String typeUser;
        int choix;
        Scanner sc = new Scanner(System.in);
        Utilisateur user;
        Plan plan = new Plan();
        boolean exit = false;

        while (!exit) { //permet de faire tourner l'application sans fin tant que exitApp n'a pas été choisi

            plan.choixFichier(); //permet de choisir le fichier qu'on utilise et affiche le plan de la ville associer

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
                    System.out.println("3. Quitter"); //créer la case associer
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
                        case 4: //rejout de la case de sortie
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