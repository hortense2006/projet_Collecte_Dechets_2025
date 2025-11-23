package view;

import model.particulier.TypeEncombrant;

public class EncombrantView
{

    // Renvoie le message d'erreur correspondant
    public String messageErreur(TypeEncombrant encombrant)
    {
        switch (encombrant)
        {
            case MEUBLE: return "Vous ne pouvez déclarer que 3 meubles maximum.";
            case ELECTROMENAGER: return "Vous ne pouvez déclarer que 2 appareils maximum.";
            case CANAPE: return "Un seul canapé est autorisé.";
            case BOIS: return "Maximum 20 pièces de bois.";
            case AUTRE: return "Maximum 5 objets pour la catégorie 'Autre'.";
            default: return "Quantité invalide.";
        }
    }
}
