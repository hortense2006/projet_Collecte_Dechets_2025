import model.*;
import controller.*;
import model.map.*;
import view.*;
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
        boolean exitAll = false;
        boolean exitParticulier = false;
        final String NOM_FICHIER_USERS = "Base_De_Donnees_Particuliers.txt";

        // IMPORT DES CLASSES :
        // pour les particuliers
        Scanner sc = new Scanner(System.in);
        FichiersProfil f = new FichiersProfil(NOM_FICHIER_USERS);
        ParticulierView pv = new ParticulierView();
        ParticulierModel pm = new ParticulierModel(NOM_FICHIER_USERS);
        ParticulierController pc = new ParticulierController(pm,pv);

        //pour le plan
        Plan plan = new Plan();
        PlanView planV = new PlanView();
        PlanController planC = new PlanController(plan,planV);
        try
        {
            //lecture du fichier
            System.out.println("Tentative de chargement du réseau en tant que ressource: " + NOM_FICHIER_USERS);
            InputStream is = Main.class.getClassLoader().getResourceAsStream(NOM_FICHIER_USERS);
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
            System.err.println("ERREUR Impossible de lire le fichier du réseau (" + NOM_FICHIER_USERS + ").");
            System.err.println("Détail de l'erreur: " + e.getMessage());
            return;
        }
        planC.choixFichier(); //permet de choisir le fichier qu'on utilise et affiche le plan de la ville associé

        while (!exitAll)  //permet de faire tourner l'application sans fin tant que exitAll n'a pas été choisi
        {
            System.out.println("Choisissez votre profil : " +
                    "\n commune" +
                    "\n particulier" +
                    "\n quitter");
            typeUser = sc.nextLine();
            switch (typeUser)
            {
                case "commune":
                {
                    System.out.println("Que souhaitez-vous faire :");
                    System.out.println("\n1. Consulter le plan de Ranville."+
                                       "\n2. Mettre à jour les informations de la commune"+
                                       "\n3. Changer de type d'utilisateur"); //créer la case associée
                    break;
                }
                case "particulier":
                {
                    exitParticulier = false;
                    //pc.login();// Connexion/Inscription
                    while (!exitParticulier)
                    { //permet de faire tourner l'application utilisateur tant qu'on a pas demandé de changer de type d'utilisateur
                        System.out.println("Que souhaitez-vous faire :");
                        System.out.println("\n1. Demande de collecte"+
                                "\n2. Consulter le planning de ramassage"+
                                "\n3. Changer de type d'utilisateur");
                        choix = sc.nextInt();
                        switch (choix)
                        {
                            case 1:
                            {
                                pc.DemandeCollecte(); // Demander une collecte d'encombrants
                                break;
                            }
                            case 2:
                            {
                                pm.consulterPlanningRamassage("ranville");
                                break;
                            }
                            case 3: //Sortie
                            {
                                exitParticulier = true; // Sortir du programme
                                System.out.println("\n Changement d'utilisateur");
                                sc.nextLine();
                                break;
                            }
                        }
                    }
                    break;
                }
                case "quitter":
                {
                    exitAll = true;
                    System.out.println("\n Au revoir. ");
                    break;
                }
            }
        }
    }
}