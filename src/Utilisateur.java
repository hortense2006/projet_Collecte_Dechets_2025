import java.util.Scanner;

public class Utilisateur
{
    // ATTRIBUTS
    private String typeUser; // "collectivité", "entreprise", "particulier"
    private String id;
    private String mdp;
    private boolean estConnecte;
    Scanner sc = new Scanner(System.in);

    // CONSTRUCTEUR
    public Utilisateur(String typeUser, String id, String mdp)
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
    public void login(String id, String mdp)
    {
        System.out.println("Etes vous une particulier ou une commune ?");
        this.typeUser = sc.nextLine();
        switch(typeUser)
        {
            case "particulier":
            {
            }
            case "commune":
            {
                System.out.println("Saisissez le nom de la commune :");
                String idPropose = sc.nextLine();
                if(idPropose.equals(id))
                {
                    System.out.println("Saisissez le mot de passe de la commune:");
                    String mdpPropose = sc.nextLine();
                    if(mdpPropose.equals(mdp))
                    {
                        setEstConnecte(true); // La commune est connectée
                    }
                    else
                    {
                        throw new ExceptionPersonnalisable("Le mot de passe est invalide");
                    }
                }
                else
                {
                    throw new ExceptionPersonnalisable("Le nom de la commune est invalide");
                }
                break;
            }
        }
    }
    // METHODE n°2 : Logout
    public void logout(){};
    // METHODE n°3 : Déclarer un dépôt
    public void declarerDepot(){}
    // METHODE n°4 : Demander une collecte
    public void faireDemandeCollecte(){}
    // METHODE n°5 : Consulter le planning de collecte
    public void consulterPlanning(Commune commune){}
}
