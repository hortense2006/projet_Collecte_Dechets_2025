import Model.FichiersProfil;
import Model.ParticulierModel;
import Controller.ParticulierController;
import View.ParticulierView;
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
        FichiersProfil f = new FichiersProfil(NOM_FICHIER);
        ParticulierView v = new ParticulierView();
        Plan plan = new Plan();
        ParticulierModel model = new ParticulierModel(NOM_FICHIER);
        ParticulierController c = new ParticulierController(model,v);
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
                    f.chargerInfosDepuisBuffer();
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
                    System.out.println("\n1. Consulter le plan de Ranville."+
                                       "\n2. Mettre à jour les informations de la commune"+
                                       "\n3. Quitter"); //créer la case associée
                    break;
                }
                case "particulier":
                {
                    System.out.println("Que souhaitez-vous faire :");
                    System.out.println("\n1. Connexion"+
                                       "\n2. Demande de collecte"+
                                       "\n3. Consulter le planning de ramassage"+
                                       "\n4. Quitter");
                    choix = sc.nextInt();
                    switch (choix)
                    {
                        case 1:
                        {
                            c.login();// Connexion/Inscription
                            break;
                        }
                        case 2:
                        {
                            c.DemandeCollecte(); // Demander une collecte d'encombrants
                            break;
                        }
                        case 3:
                        {
                            model.consulterPlanningRamassage("ranville");
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