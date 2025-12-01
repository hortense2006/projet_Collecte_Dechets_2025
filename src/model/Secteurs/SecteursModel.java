package model.Secteurs;

import exceptions.ExceptionPersonnalisable;

import java.util.Comparator;

public class SecteursModel implements Comparator<Secteurs>
{
    // METHODE n°1 : Comparer deux secteurs (incompatibilité selon critères géographiques + jours de passage)
    @Override
    public int compare(Secteurs o1, Secteurs o2)
    {
        int degre1; // 0 si c'est incompatible, 1 si c'est compatible
        try
        {
            degre1 = getIncompatibilitesSecteurs(o1,o2);
        }
        catch (Exception e)
        {
            throw new ExceptionPersonnalisable("Produit non trouve.");
        }
        int degre2 = getIncompatibilitesSecteurs(o1,o2);
        return Integer.compare(degre1,degre2);
    }
    // METHODE n°2 : Incompatilibités des secteurs
    public int getIncompatibilitesSecteurs(Secteurs o1,Secteurs o2)
    {return 0;}
}
