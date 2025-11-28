package model.particulier;
import model.map.Arc;

import java.time.LocalDate;

/* CLASSE TERMINEE*/
public class DemandeCollecte
{
    // ATTRIBUTS
    private String idUtilisateur;
    private TypeEncombrant typeEncombrant;
    private int quantite;
    private LocalDate dateDemande;
    private String rue;
    private double numero;
    private Arc arcMaison;
    private double position;


    // CONSTRUCTEUR
    public DemandeCollecte(String idUtilisateur,TypeEncombrant typeEncombrant,int quantite,LocalDate dateDemande,String rue,double numero)
    {
        this.idUtilisateur = idUtilisateur;
        this.typeEncombrant = typeEncombrant;
        this.quantite = quantite;
        this.dateDemande = dateDemande;
        this.rue = rue;
        this.numero = numero;
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
    // GETTER n°6
    public String getId() { return idUtilisateur;}
}
