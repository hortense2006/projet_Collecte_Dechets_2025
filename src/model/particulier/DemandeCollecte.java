package model.particulier;
import java.time.LocalDate;

public class DemandeCollecte
{
    // ATTRIBUTS
    private String idUtilisateur;
    private TypeEncombrant typeEncombrant;
    private int quantite;
    private LocalDate dateDemande;
    //private String

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
}
