package Model;

public class Profil
{
    private String prenom;
    private String nom;
    private int numero;
    private String rue;
    private String id;
    private String mdp;

    // CONSTRUCTEUR
    public Profil(String prenom, String nom, int numero, String rue, String id, String mdp)
    {
        this.prenom = prenom;
        this.nom = nom;
        this.numero = numero;
        this.rue = rue;
        this.id = id;
        this.mdp = mdp;
    }

    // -------- GETTERS --------
    public String getPrenom() { return prenom; }
    public String getNom() { return nom; }
    public int getNumero() { return numero; }
    public String getRue() { return rue; }
    public String getId() { return id; }
    public String getMdp() { return mdp; }
}
