package model;

import model.particulier.TypeEncombrant;

public class EncombrantModel
{

    // ATTRIBUTS
    public static final int MAX_MEUBLE = 3;
    public static final int MAX_ELECTROMENAGER = 2;
    public static final int MAX_CANAPE = 1;
    public static final int MAX_BOIS = 20;
    public static final int MAX_AUTRE = 5;

    // METHODE n°1 :  Vérifie si la quantité est valide pour le type d'encombrant
    public boolean quantiteValide(TypeEncombrant encombrant, int quantite)
    {
        switch (encombrant)
        {
            case MEUBLE:
            {
                if(quantite <= MAX_MEUBLE) return  true;
                break;
            }
            case ELECTROMENAGER:
            {
                if(quantite <= MAX_ELECTROMENAGER) return  true;
                break;
            }
            case CANAPE:
            {
                if(quantite <= MAX_CANAPE) return  true;
                break;
            }
            case BOIS:
            {
                if(quantite <= MAX_BOIS) return  true;
                break;
            }
            case AUTRE:
            {
                if(quantite <= MAX_AUTRE) return  true;
                break;
            }
            default:
            {
                return false;
            }
        }
        return false;
    }
}
