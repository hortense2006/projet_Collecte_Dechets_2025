import Model.Fichiers;
import Model.Particuliers;
import map.Plan;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {

        // ATTRIBUTS
        String typeUser = "";
        int choix;
        boolean exit = false;
        final String NOM_FICHIER = "Base_De_Donnees_Particuliers.txt";

        // IMPORT DES CLASSES
        Scanner sc = new Scanner(System.in);
        Fichiers f = new Fichiers(NOM_FICHIER);
        Plan plan = new Plan();
        Particuliers p = new Particuliers();

        try
        {
            //lecture du fichier
            System.out.println("Tentative de chargement du réseau en tant que ressource: " + NOM_FICHIER);
            InputStream is = Main.class.getClassLoader().getResourceAsStream(NOM_FICHIER);
            if (is == null)
            {
                System.out.println("Échec de la lecture ClassLoader. Tentative de lecture de fichier simple...");
                f.chargerInfos();
            }
            else
            {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8)))
                {
                    f.chargerReseauDepuisBuffer();
                }
            }

        }
        catch (IOException e)
        {
            System.err.println("ERREUR Impossible de lire le fichier du réseau (" + NOM_FICHIER + ").");
            System.err.println("Détail de l'erreur: " + e.getMessage());
            return;
        }
        while (!exit)
        { //permet de faire tourner l'application sans fin tant que exitApp n'a pas été choisi

            plan.choixFichier(); //permet de choisir le fichier qu'on utilise et affiche le plan de la ville associé

            System.out.println("Choisissez votre profil : " +
                    "\n commune" +
                    "\n particulier" +
                    "\n entreprise");
            typeUser = sc.nextLine();
            switch (typeUser)
            {
                case "commune":
                {
                    System.out.println("Que souhaitez-vous faire :");
                    System.out.println("1. Consulter le plan de Ranville.");
                    System.out.println("2. Mettre à jour les informations de la commune");
                    System.out.println("3. Quitter"); //créer la case associée
                    break;
                }
                case "particulier":
                {
                    System.out.println("Que souhaitez-vous faire :");
                    System.out.println("1. Connexion");
                    System.out.println("2. Demande de collecte");
                    System.out.println("3. Consulter le planning de ramassage");
                    System.out.println("4. Quitter");
                    choix = sc.nextInt();
                    switch (choix)
                    {
                        case 1:
                        {
                            //user.login();
                            break;
                        }
                        case 2:
                        {
                            //user.faireDemandeCollecte("particulier");
                            break;
                        }
                        case 3:
                        {
                            //user.consulterPlanningRamassage("ranville");
                            break;
                        }
                        case 4: //Sortie
                        {
                            exit = true; // Sortir du programme
                            System.exit(0);
                            break;
                        }
                    }
                    break;
                }
            }
        }
    }
}