import Exceptions.ExceptionPersonnalisable;

import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        // ATTRIBUTS
        String typeUser;
        int choix;
        Scanner sc = new Scanner(System.in);
        Utilisateur user;

        System.out.println("Choisissez votre profil:");
        System.out.println("commune");
        System.out.println("particulier");
        System.out.println("entreprise");
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
                choix = sc.nextInt();
                switch (choix)
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
                    default: {throw new ExceptionPersonnalisable("Wrong choice");}
                }

            }
            case "entreprise":{}
            default:{throw new ExceptionPersonnalisable("Wrong choice");}
        }
    }
}