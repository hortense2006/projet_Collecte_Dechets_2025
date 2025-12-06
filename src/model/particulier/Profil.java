package model.particulier;

public class Profil
{
    private String prenom;
    private String nom;
    private double numero; // Notation américaine : correspond à la distance entre la maison et le bout de la rue
    private String rue;
    private String id;
    private String mdp;
    private String nomVille;
    private boolean estConnecte;

    // CONSTRUCTEUR
    public Profil(String nomVille,String prenom, String nom, double numero, String rue, String id, String mdp)
    {
        this.prenom = prenom;
        this.nom = nom;
        this.numero = numero;
        this.rue = rue;
        this.id = id;
        this.mdp = mdp;
        this.nomVille = nomVille;
    }

    // GETTERS
    public String getPrenom() { return prenom; }
    public String getNom() { return nom; }
    public double getNumero() { return numero; }
    public String getRue() { return rue; }
    public String getId() { return id; }
    public String getMdp() { return mdp; }
    public String getNomVille() { return nomVille;}

    // SETTER n°1
    public boolean setEstConnecte(boolean estConnecte)
    {
        this.estConnecte = estConnecte;
        return estConnecte;
    }
}
