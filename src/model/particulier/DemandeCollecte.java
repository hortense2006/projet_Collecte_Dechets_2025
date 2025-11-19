package model.particulier;
import java.time.LocalDate;

public class DemandeCollecte
{
    // ATTRIBUTS
    private String idUtilisateur;
    private TypeEncombrant typeEncombrant;
    private int quantite;
    private LocalDate dateDemande;
    private String rue;
    private double numero;


    // CONSTRUCTEUR
    public DemandeCollecte(String idUtilisateur,TypeEncombrant typeEncombrant,int quantite,LocalDate dateDemande)
    {
        this.idUtilisateur = idUtilisateur;
        this.typeEncombrant = typeEncombrant;
        this.quantite = quantite;
        this.dateDemande = dateDemande;
    }
    // GETTER n°1
    public TypeEncombrant getTypeEncombrant() {return typeEncombrant;}
    // GETTER n°2
    public int getQuantite() {return quantite;}
    // GETTER n°3
    public LocalDate getDateDemande() {return dateDemande;}
    // GETTER n°4
    public String getRue() {return rue;}
    // GETTER n°5
    public double getNumero() {return numero;}
}
