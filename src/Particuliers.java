import java.util.*;

public class Particuliers implements Utilisateur
{
    // ATTRIBUTS
    private String typeUser; // "collectivité", "entreprise", "particulier"
    private String id;
    private String mdp;
    private boolean estConnecte;
    private Map<String,String> personne;
    private Map<Integer,String> adresse;
    private Map<String,String> compte;

    Scanner sc = new Scanner(System.in);

    // CONSTRUCTEUR
    public Particuliers(String typeUser)
    {
        this.typeUser = typeUser;
        this.estConnecte = false;
        this.personne = new HashMap<>();
        this.adresse = new HashMap<>();
        this.compte = new HashMap<>();
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
    public boolean setEstConnecte(boolean estConnecte)
    {
        this.estConnecte = estConnecte;
        return estConnecte;
    }
    // SETTER n°2
    public String setmdp(String mdp)
    {
        this.mdp = mdp;
        return mdp;
    }

    // METHODE n°1 : Login de l'utilisateur
    @Override
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
                signup();
                break;
            }
            default:{throw new ExceptionPersonnalisable("Choix invalide.");}
        }
    }

    // METHODE n°1 : CONNEXION
    public void signin()
    {
        System.out.println("Saisissez votre identifiant:");
        getid(sc.nextLine()); // On récupère l'identifiant saisi
        if(lireInfos(this.id)) // Lecture du fichier : l'identifiant existes
        {
            System.out.println("Saisissez votre mot de passe:");
            getmdp(sc.nextLine()); // On récupère le mdp saisi
            if(existenceMdp(this.id,this.mdp))// On vérifie son existence
            {
                setEstConnecte(true);
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

    // METHODE n°2 : INSCRIPTION
    public void signup()
    {
        System.out.println("Saisissez votre prenom");
        String prenom = sc.nextLine();
        System.out.println("Saisissez votre nom de famille:");
        String nom = sc.nextLine();
        personne.put(prenom,nom); // On remplit la Hashmap
        System.out.println("Saisissez votre numero d'habitation:");
        int numero = sc.nextInt();
        System.out.println("Saisissez votre numero de rue:");
        String rue = sc.nextLine();
        adresse.put(numero,rue); // On remplit la Hashmap
        this.id = UUID.randomUUID().toString(); // Chaque id est différent
        System.out.println("Voici votre identifiant:" + this.id);
        System.out.println("Saisissez un mot de passe:");
        setmdp(sc.nextLine());
        compte.put(this.id,this.mdp);
        // On enregistre les infos dans le fichier texte
        chargerInfos();
    }
    // METHODE n°3
    @Override
    public void chargerInfos() {

    }
    // METHODE n°4
    @Override
    public boolean lireInfos(String info) {
        return false;
    }
    // METHODE n°5 : Demander une collecte d'encombrants
    @Override
    public void faireDemandeCollecte(String typeUser) {

    }
    // METHODE n°6 : Consulter le planning de collecte (ramassage devant les maisons
    @Override
    public void consulterPlanningRamassage(String commune) {

    }
    // METHODE n°7
    public boolean existenceMdp(String id,String mdp){return false;}
}
