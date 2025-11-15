package View;
import Exceptions.ExceptionPersonnalisable;
import Model.*;
import java.util.*;

// Cette classe s'occuppe uniquement de l'affichage : actuellement pas de logique MVC => à refaire
public class ParticulierView
{
    // ATTRIBUTS
    public HashMap<String, Profil> compte;
    private String nomFichier;

    // APPEL DE CLASSES
    Scanner sc = new Scanner(System.in);
    ParticulierModel p = new ParticulierModel();
    Profil profil = new Profil();

    public record ProfilInput(String prenom, String nom, int numero, String rue, String mdp) {}


    // METHODE n°1 : AFFICHAGE DU LOGIN DE L'UTILISATEUR
    public void afficherLogin()
    {
        String choix;
        System.out.println("Etes-vous déjà inscrit ?");
        choix = sc.nextLine();
        switch(choix)
        {
            case "oui":
            { // Connexion
                afficherSignin();
                break;
            }
            case "non":
            { // Inscription
                afficherRegister();
                break;
            }
            default:{throw new ExceptionPersonnalisable("Choix invalide.");}
        }
    }

    // METHODE n°2 : AFFICHAGE CONNEXION
    public void afficherSignin()
    {
        System.out.println("Saisissez votre identifiant:");
        String idPropose = sc.nextLine(); // On récupère l'identifiant saisi
        p.verifierInfos(idPropose); // On verifie l'identifiant
        System.out.println("Saisissez votre mot de passe:");
        String mdpPropose = sc.nextLine(); // On récupère le mdp saisi
        p.verifierInfos(mdpPropose);
        System.out.println("Connexion réussie !");
    }

    // METHODE n°3 : AFFICHAGE INSCRIPTION
    public ProfilInput afficherRegister()
    {
        System.out.println("Saisissez votre prenom");
        String prenom = sc.nextLine();
        System.out.println("Saisissez votre nom de famille:");
        String nom = sc.nextLine();
        System.out.println("Saisissez votre numero d'habitation:");
        int numero = sc.nextInt();
        sc.nextLine(); // Vide le buffer
        System.out.println("Saisissez le nom de votre rue:");
        String rue = sc.nextLine();
        System.out.println("Saisissez un mot de passe:");
        String mdp = sc.nextLine();
        return new ProfilInput(prenom,nom,numero,rue,mdp); // On retourne les informations du profil.
    }
    // METHODE n°4 : AFFICHER LE PROFIL
    public void afficherInfos()
    {
        System.out.println("Prenom :" + profil.getPrenom());
        System.out.println("Nom :" + profil.getNom());
        System.out.println("Numero :" + profil.getNumero());
        System.out.println("Rue :" + profil.getRue());
        System.out.println("Mot de passe :"+ profil.getMdp());
    }
}
