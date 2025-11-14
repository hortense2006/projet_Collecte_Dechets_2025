package View;

import Exceptions.ExceptionPersonnalisable;
import Model.Profil;

import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

public class ParticulierView
{
    // ATTRIBUTS
    public Map<String, Profil> compte;

    // APPEL DE CLASSES
    Scanner sc = new Scanner(System.in);

    // METHODE n°1 : Login de l'utilisateur

    public void login()
    {
        String choix;
        System.out.println("Etes-vous déjà inscrit ?");
        choix = sc.nextLine();
        switch(choix)
        {
            case "oui":
            { // Connexion (lecture du fichier texte)
                signin();
                break;
            }
            case "non":
            { // Se créer un compte (écriture d'un fichier)
                register();
                break;
            }
            default:{throw new ExceptionPersonnalisable("Choix invalide.");}
        }
    }

    // METHODE n°2 : CONNEXION
    public void signin()
    {
        System.out.println("Saisissez votre identifiant:");
        String idPropose = sc.nextLine(); // On récupère l'identifiant saisi
        if(compte.containsKey(idPropose)) // Lecture du fichier : l'identifiant existes
        {
            Profil p = compte.get(idPropose);
            System.out.println("Saisissez votre mot de passe:");
            String mdpPropose = sc.nextLine(); // On récupère le mdp saisi
            if(p.getMdp().equals(mdpPropose))// On vérifie si c'est le bon mot de passe
            {
                p.setEstConnecte(true);
                System.out.println("Connexion réussie !");
            }
            else
            {
                System.out.println("Ce mot de passe est invalide.");
                signin();
            }
        }
        else
        {
            System.out.println("Cet identifiant n'existes pas.");
            signin();
        }
    }

    // METHODE n°3 : INSCRIPTION
    public void register()
    {
        try{}
        catch(ExceptionPersonnalisable e){}
        System.out.println("Saisissez votre prenom");
        String prenom = sc.nextLine();
        System.out.println("Saisissez votre nom de famille:");
        String nom = sc.nextLine();
        System.out.println("Saisissez votre numero d'habitation:");
        int numero = sc.nextInt();
        sc.nextLine(); // Vide le buffer
        System.out.println("Saisissez le nom de votre rue:");
        String rue = sc.nextLine();
        String id = UUID.randomUUID().toString(); // Chaque id est différent
        System.out.println("Voici votre identifiant:" + id);
        System.out.println("Saisissez un mot de passe:");
        String mdp = sc.nextLine();
        // On crée un profil
        Profil p = new Profil(prenom,nom,numero,rue,id,mdp);
        compte.put(id,p); // On l'ajoutes à la HashMap compte
        // On enregistre les infos dans le fichier texte
        //sauvegarderProfil(nomFichier,p);
    }
}
