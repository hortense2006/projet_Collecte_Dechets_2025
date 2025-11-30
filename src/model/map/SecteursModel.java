package model.map;

import exceptions.ExceptionPersonnalisable;

import java.util.Comparator;

public class SecteursModel implements Comparator<Secteurs>
{
    /// Comparer deux secteurs

    @Override
    public int compare(Secteurs o1, Secteurs o2) {
        int degre1; // 0 si c'est incompatible, 1 si c'est compatible
        try {
            degre1 = n.getIncompatibilitesProduits(o1,o2);
        }
        catch (Exception e)
        {
            throw new ExceptionPersonnalisable("Produit non trouve.");
        }
        int degre2 = n.getIncompatibilitesProduits(p1,p2);
        return Integer.compare(degre1,degre2);
    }

    /// Répartition par zones : établissement des secteurs
    // Méthode n°3 : Répartir la cargaison
    /*public int repartirCargaison() throws IOException
    {
        ;
        List<Produit> couleur;
        Produit color;
        int nbzones = 0;
        // Lire la liste produits
        while(!produit.isEmpty()) // On lit la case de la liste
        {
            for (Produit p : produit)
            {
                // Si le produit n'a pas encore de zone
                if (p.getZone() == 0)
                {
                    nbzones++;
                    p.setZone(nbzones); // on lui attribue une nouvelle zone
                }
            }
        }
        return nbzones;
    }*/
}
