public class Utilisateur
{
    // ATTRIBUTS
    private String typeUser; // "collectivité", "entreprise", "particulier"
    private String id;
    private String mdp;
    private boolean estConnecte;

    // CONSTRUCTEUR
    public Utilisateur(String typeUser, String id, String mdp)
    {
        this.typeUser = typeUser;
        this.id = id;
        this.mdp = mdp;
    }

    // GETTER n°1
    public void getid(String id)
    {
        this.id = id;
    }

    // GETTER n°2
    public void getmdp(String mdp   )
    {
        this.mdp = mdp;
    }

    // METHODE n°1 : Login
    public boolean login(String id, String mdp){return false;}
    // METHODE n°2 : Logout
    public void logout(){};
    // METHODE n°3 : Déclarer un dépôt
    public void declarerDepot(){}
    // METHODE n°4 : Demander une collecte
    public void faireDemandeCollecte(){}
    // METHODE n°5 : Consulter le planning de collecte
    public void consulterPlanning(Commune commune){}

}
