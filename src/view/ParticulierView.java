package view;
import model.map.Arc;
import model.map.Plan;
import model.map.Station;
import model.particulier.DemandeCollecte;
import model.particulier.TypeEncombrant;
import model.particulier.ProfilInput;
import java.util.*;

// Cette classe s'occupe uniquement de l'affichage de tout ce qui se rapporte au particulier
public class ParticulierView
{
    // APPEL DE CLASSES
    private Scanner sc;

    // CONSTRUCTEUR
    public ParticulierView(Scanner sc){this.sc=sc;}

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
    public TypeEncombrant affichageDemandeCollecte()
    {
        System.out.println("Quel type d'encombrant voulez-vous déclarer ?");
        System.out.println("\n1. Meubles" +
                           "\n2. Electroménager"+
                           "\n3. Bois"+
                           "\n4.Canape"+
                           "\n5. Autre");
        int saisie = sc.nextInt();
        sc.nextLine();
        if(saisie <1 || saisie>5)
        {
            System.out.println("Choix invalide");
        }
        TypeEncombrant choix = TypeEncombrant.fromString(String.valueOf(saisie));
        return choix;
    }
    // METHODE n°7 : QUELLE QUANTITE D'ENCOMBRANTS
    public int affichageQuantiteEncombrants(TypeEncombrant encombrant)
    {
        System.out.println("Combien d'objets voulez-vous déclarer ?");
        int quantite = sc.nextInt();
        sc.nextLine();
        return quantite;
    }
    // METHODE n°8 : afficher les infos du particulier
    public void afficherInfos(){}
    // METHODE n°9: Afficher la demande de l'utilisateur
    public void afficherDemande(DemandeCollecte demande)
    {
        System.out.println("Type d'encombrants :");
        System.out.println(demande.getTypeEncombrant());
        System.out.println("Quantite :");
        System.out.println(demande.getQuantite());
        System.out.println("Date de demande :");
        System.out.println(demande.getDateDemande());
    }
    // METHODE n°10 : Renvoie le message d'erreur correspondant pour les encombrants
    public String messageErreur(TypeEncombrant encombrant)
    {
        switch (encombrant)
        {
            case MEUBLE: return "Vous ne pouvez déclarer que 3 meubles maximum.";
            case ELECTROMENAGER: return "Vous ne pouvez déclarer que 2 appareils maximum.";
            case CANAPE: return "Un seul canapé est autorisé.";
            case BOIS: return "Maximum 20 pièces de bois.";
            case AUTRE: return "Maximum 5 objets pour la catégorie 'Autre'.";
            default: return "Quantité invalide.";
        }
    }
    public String demander(String message)
    {
        System.out.println(message);
        return sc.nextLine();
    }

    public boolean ajouterPoubelleSpecifique(Plan plan, String nomRue, double position, TypeEncombrant type)
    {
        Arc rue = trouverRueParNom(plan, nomRue);

        if (rue == null)
        {
            System.err.println("Erreur : La rue " + nomRue + " n'existe pas.");
            return false;
        }

        if (position < 0 || position > rue.getDistance())
        {
            System.err.println("Erreur : La position " + position + " est hors de la rue (Longueur max: " + rue.getDistance() + ")");
            return false;
        }

        //DemandeCollecte demande = new DemandeCollecte("Utilisateur_Manuel", rue, position, type, 50.0);
        //rue.ajouterDechet(demande);
        System.out.println("Succès : Déchet ajouté sur " + nomRue + " à " + position + "m.");
        return true;
    }

    private Arc trouverRueParNom(Plan plan, String idLigne)
    {
        for (Station s : plan.getStations().values())
        {
            for (Arc a : s.getArcsSortants())
            {
                if (a.getIdLigne().equals(idLigne))
                {
                    return a;
                }
            }
        }
        return null;
    }
}
