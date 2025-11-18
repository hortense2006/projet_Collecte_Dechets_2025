package model;
import java.time.LocalDate;

public class DemandeCollecte
{
    // ATTRIBUTS
    private String idUtilisateur;
    private TypeEncombrant typeEncombrant;
    private int quantite;
    private LocalDate dateDemande;

    // CONSTRUCTEUR
    public DemandeCollecte(String idUtilisateur,TypeEncombrant typeEncombrant,int quantite,LocalDate dateDemande)
    {
        this.idUtilisateur = idUtilisateur;
        this.typeEncombrant = typeEncombrant;
        this.quantite = quantite;
        this.dateDemande = dateDemande;
    }
    public enum TypeEncombrant
    {
        MEUBLE, ELECTROMENAGER,BOIS,CANAPE, AUTRE;

        // METHODE n°1 : Evite les erreurs de typo dans AffichagedemandeCollecte
        public static TypeEncombrant fromString(String input)
        {
            if (input == null) return null;
            input = input.trim().toUpperCase(); // "Meuble" → "MEUBLE"

            switch (input)
            {
                case "MEUBLES": return MEUBLE;
                case "ELECTROMENAGER":
                case "ÉLECTROMÉNAGER":
                    return ELECTROMENAGER;
                case "AUTRE": return AUTRE;
                default: return AUTRE;
            }
        }
    }

}
