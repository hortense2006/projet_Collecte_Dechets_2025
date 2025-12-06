package view;
import model.particulier.DemandeCollecte;
import model.particulier.Profil;
import model.particulier.TypeEncombrant;
import model.particulier.ProfilInput;
import java.util.*;

// Cette classe s'occupe uniquement de l'affichage de tout ce qui se rapporte au particulier
public class ParticulierView
{
    // APPEL DE CLASSES
    private Scanner sc;

    // ATTRIBUTS
    public String saisie;

    // CONSTRUCTEUR
    public ParticulierView(Scanner sc){this.sc=sc;}

    // METHODE n°1 : AFFICHAGE DU LOGIN DE L'UTILISATEUR
    public String ActionLogin()
    {
        System.out.println("Etes-vous déjà inscrit ? (oui/non)");
        sc.nextLine();
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
        System.out.println("Saisissez le nom de votre ville :");
        String nomVille = sc.nextLine();
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
        return new ProfilInput(nomVille,prenom,nom,numero,rue,mdp); // On retourne les informations du profil.
    }

    // METHODE n°6 : AFFICHAGE DEMANDE COLLECTE
    public TypeEncombrant affichageDemandeCollecte()
    {
        do
        {
            System.out.println("Quel type d'encombrant voulez-vous déclarer ?");
            System.out.println("\n Meubles" +
                    "\n Electromenager" +
                    "\n Bois" +
                    "\n Canape" +
                    "\n Autre");
            saisie = sc.nextLine();
            saisie = saisie.trim();// Enlève les espaces avant/après en trop
        }while(!saisie.equalsIgnoreCase("Meubles") && !saisie.equalsIgnoreCase("Electromenager") &&
                !saisie.equalsIgnoreCase("Bois") && !saisie.equalsIgnoreCase("Canape") &&
                !saisie.equalsIgnoreCase("Autre"));
        TypeEncombrant choix = TypeEncombrant.fromString(saisie);
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
    public void afficherInfos(Profil profil)
    {
        System.out.println("Ville :"+profil.getNomVille());
        System.out.println("Nom :"+ profil.getNom());
        System.out.println("Prenom :" + profil.getPrenom());
        System.out.println("Id:"+ profil.getId());
        System.out.println("Mot de passe :" + profil.getMdp());
        System.out.println("Numero de rue :" + profil.getNumero());
        System.out.println("Nom de la rue :"+ profil.getRue());
    }

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
    public void messageErreur(TypeEncombrant encombrant)
    {
        switch (encombrant)
        {
            case MEUBLE:
            {
                System.out.println("Vous ne pouvez déclarer que 3 meubles maximum.");
                break;
            }
            case ELECTROMENAGER:
            {
                System.out.println("Vous ne pouvez déclarer que 2 appareils maximum.");
                break;
            }
            case CANAPE:
            {
                System.out.println("Un seul canapé est autorisé.");
                break;
            }
            case BOIS:
            {
                System.out.println("Maximum 20 pièces de bois.");
                break;
            }
            case AUTRE:
            {
                System.out.println("Maximum 5 objets pour la catégorie 'Autre'.");
            }
            default:
            {
                System.out.println("Quantité invalide.");
            }
        }
    }

    // METHODE n°11 : Affichage d'un message + retour de la réponse
    public String demander(String message)
    {
        System.out.println(message);
        return sc.nextLine();
    }
    // METHODE n°12 : Consulter le planning de collecte (ramassage devant les maisons)
    public void consulterPlanningRamassageBordeaux()
    {
        System.out.println("-----------------PLANNING DE RAMASSAGE-----------------");
        System.out.println("Lundi : Caudéran\n" +
                           "Mardi : Chartrons - Grand Parc - Jardin Public\n" +
                           "Mercredi : Bordeaux Centre\n" +
                           "Jeudi : Saint-Augustin - Tauzin - Alphonse Dupeux\n" +
                           "Vendredi : Nansouty - Saint-Genès\n" +
                           "Samedi : Bordeaux Sud\n" +
                           "Dimanche : La Bastide, Bordeaux Maritime");
    }
    public void consulterPlanningRamassageRanville(){}

}
