package model;

import model.particulier.TypeEncombrant;

public class EncombrantModel
{

    public static final int MAX_MEUBLE = 3;
    public static final int MAX_ELECTROMENAGER = 2;
    public static final int MAX_CANAPE = 1;
    public static final int MAX_BOIS = 20;
    public static final int MAX_AUTRE = 5;

    // Vérifie si la quantité est valide pour le type d'encombrant
    public boolean quantiteValide(TypeEncombrant encombrant, int quantite)
    {
        switch (encombrant)
        {
            case MEUBLE:
            {
                if(quantite <= MAX_MEUBLE) return  true;}
            case ELECTROMENAGER:
            {
                if(quantite <= MAX_ELECTROMENAGER) return  true;
            }
            case CANAPE:
            {
                if(quantite <= MAX_CANAPE) return  true;
            }
            case BOIS:
            {
                if(quantite <= MAX_BOIS) return  true;
            }
            case AUTRE:
            {
                if(quantite <= MAX_AUTRE) return  true;
            }
            default: return false;
        }
    }
}
