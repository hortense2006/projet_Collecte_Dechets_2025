import java.util.Scanner;
import java.util.UUID;


public class Particuliers implements Utilisateur
{
    // ATTRIBUTS
    private String typeUser; // "collectivité", "entreprise", "particulier"
    private String id;
    private String mdp;
    private boolean estConnecte;
    Scanner sc = new Scanner(System.in);

    // CONSTRUCTEUR
    public Particuliers(String typeUser)
    {
        this.typeUser = typeUser;
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

    // METHODE n°1 : Login de l'utilisateur
    @Override
    public void login(String typeUser)
    {
        System.out.println("Etes déjà inscrit ?");
        this.typeUser = sc.nextLine();
        switch(typeUser)
        {
            case "oui":
            { // Connexion (lecture du fichier texte)
                // Menu
                System.out.println("Saisissez votre identifiant:");
                this.id = sc.nextLine();
                if(lireInfos()) // Lecture du fichier
                {
                    System.out.println("Saisissez votre mot de passe:");
                    this.mdp = sc.nextLine();
                }
                else
                {
                    throw new ExceptionPersonnalisable("Le mot de passe est invalide");
                }
            }
            case "non":
            { // Se créer un compte (écriture d'un fichier)
                // nom, prénom, adresse (numéro + nom de rue)
                this.id = UUID.randomUUID().toString(); // Chaque id est différent
                this.mdp = String.valueOf(Math.random()); // Génération aléatoire d'un mdp
                // identifiant random (ne peut pas se répéter)

                break;
            }
        }
    }

    // METHODE n°1
    public void chargerInfos() {

    }
    // METHODE n°2
    public boolean lireInfos() {
        return false;
    }
    // METHODE n°4 : Demander une collecte d'encombrants
    @Override
    public void faireDemandeCollecte(String typeUser) {

    }
    // METHODE n°5 : Consulter le planning de collecte (ramassage devant les maisons
    @Override
    public void consulterPlanningRamassage(String commune) {

    }
}
