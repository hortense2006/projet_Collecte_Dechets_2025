package model.map;

import java.io.IOException;
import java.util.List;

// Création des secteurs dans la ville
public class Secteurs
{
    /// Comparer deux secteurs
    /*public int compare(Produit p1, Produit p2)
    {
        int degre1; // 0 si c'est incompatible, 1 si c'est compatible
        try {
            degre1 = n.getIncompatibilitesProduits(p1,p2);
        }
        catch (ProduitNonTrouveException p)
        {
            throw new ProduitNonTrouveException("Produit non trouve.");
        }
        int degre2 = n.getIncompatibilitesProduits(p1,p2);
        return Integer.compare(degre1,degre2);
    }*/

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
