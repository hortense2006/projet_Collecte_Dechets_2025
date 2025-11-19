package model.particulier;

// ENUM
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
