import java.util.Scanner;

public class Particuliers
{
    // ATTRIBUTS
    private String typeUser; // "collectivité", "entreprise", "particulier"
    private String id;
    private String mdp;
    private boolean estConnecte;
    Scanner sc = new Scanner(System.in);

    // CONSTRUCTEUR
    public Particuliers(String typeUser, String id, String mdp)
    {
        this.typeUser = typeUser;
        this.id = id;
        this.mdp = mdp;
        this.estConnecte = false;
    }

    // GETTER n°1
    public void getid(String id)
    {
        this.id = id;
    }

    // GETTER n°2
    public void getmdp(String mdp)
    {
        this.mdp = mdp;
    }

    // SETTER n°1

    public void setEstConnecte(boolean estConnecte)
    {
        this.estConnecte = estConnecte;
    }
    // METHODE n°1 : Login
    public void loginUser(String id, String mdp)
    {
        System.out.println("Etes déjà inscrit ?");
        this.typeUser = sc.nextLine();
        switch(typeUser)
        {
            case "oui":
            { // Connexion (lecture du fichier texte)
                // Menu
                System.out.println("Saisissez votre identifiant:");
            }
            case "non":
            { // Se créer un compte (écriture d'un fichier)
                // nom, prénom, adresse (numéro + nom de rue)
                // identifiant random (ne peut pas se répéter)

                break;
            }
        }
    }
    // METHODE n°4 : Demander une collecte d'encombrants
    public void faireDemandeCollecte(){}
    // METHODE n°5 : Consulter le planning de collecte (ramassage devant les maisons)
    public void consulterPlanningRamassage(Commune commune){}
}
