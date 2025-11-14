import Exceptions.ExceptionPersonnalisable;
import Model.Particuliers;
import Model.Utilisateur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        // ATTRIBUTS
        String typeUser = "";
        int choix;
        Scanner sc = new Scanner(System.in);
        Utilisateur user;
        final String NOM_FICHIER = "Base_De_Donnees_Particuliers.txt";
        Particuliers p = new Particuliers(typeUser);

        try
        {
            //lecture du fichier
            System.out.println("Tentative de chargement du réseau en tant que ressource: " + NOM_FICHIER);
            InputStream is = Main.class.getClassLoader().getResourceAsStream(NOM_FICHIER);
            if (is == null)
            {
                System.out.println("Échec de la lecture ClassLoader. Tentative de lecture de fichier simple...");
                p.chargerInfos(NOM_FICHIER);
            }
            else
            {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8)))
                {
                    p.chargerReseauDepuisBuffer();
                }
            }

        }
        catch (IOException e)
        {
            System.err.println("ERREUR Impossible de lire le fichier du réseau (" + NOM_FICHIER + ").");
            System.err.println("Détail de l'erreur: " + e.getMessage());
            return;
        }
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
                }
            }
            case "entreprise":{}
        }
    }
}