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

    // -------- SETTERS --------
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public void setNom(String nom) { this.nom = nom; }
    public void setNumero(int numero) { this.numero = numero; }
    public void setRue(String rue) { this.rue = rue; }
    public void setId(String id) { this.id = id; }
    public void setMdp(String mdp) { this.mdp = mdp; }
}
