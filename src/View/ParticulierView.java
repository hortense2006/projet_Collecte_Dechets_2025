package View;
import Controller.ParticulierController;
import Exceptions.ExceptionPersonnalisable;
import Model.*;
import java.util.*;

// Cette classe s'occuppe uniquement de l'affichage
 /* CLASSE TERMINEE !! */
public class ParticulierView
{
    // APPEL DE CLASSES
    Scanner sc = new Scanner(System.in);

    // CONSTRUCTEUR
    public ParticulierView(){}

    // METHODE n°1 : AFFICHAGE DU LOGIN DE L'UTILISATEUR
    public String ActionLogin()
    {
        System.out.println("Etes-vous déjà inscrit ? (oui/non)");
        return sc.nextLine();
    }

    // METHODE n°2 : Afficher une message
    public void afficherMessage(String message)
    {
        System.out.println(message);
    }

    // METHODE n°3 : DEMANDER IDENTIFIANT
    public String afficherId()
    {
        System.out.println("Saisissez votre identifiant:");
        String idPropose = sc.nextLine(); // On récupère l'identifiant saisi
        return idPropose;
    }

    // METHODE n°4 : DEMANDER MOT DE PASSE
    public String  afficherMdp()
    {
        System.out.println("Saisissez votre mot de passe:");
        String mdpPropose = sc.nextLine(); // On récupère le mdp saisi
        return mdpPropose;
    }

    // METHODE n°5 : AFFICHAGE INSCRIPTION
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

    // METHODE n°6 : AFFICHAGE DEMANDE COLLECTE
    public String affichageDemandeCollecte()
    {
        System.out.println("Quel type d'encombrants voulez-vous déclarer ?");
        System.out.println("\nMeubles" +
                           "\nElectromenager");
        String typeEncombrants = sc.nextLine();
        return typeEncombrants;
    }
    // METHODE n°7 : QUELLE QUANTITE D'ENCOMBRANTS
    public int affichageQuantiteEncombrants()
    {
        System.out.println("Saisissez votre quantite :");
        int quantite = sc.nextInt();
        sc.nextLine();
        return quantite;
    }
    // METHODE n°8 : afficher les infos du particulier
    public void afficherInfos(){}
}
