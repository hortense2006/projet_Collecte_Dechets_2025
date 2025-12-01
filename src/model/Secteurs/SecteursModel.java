package model.Secteurs;

import exceptions.ExceptionPersonnalisable;

import java.io.IOException;
import java.util.*;

public class SecteursModel implements Comparator<Secteurs>
{
    //ATTRIBUTS
    private FichierSecteurs fichiers;
    HashMap<String, Secteurs> secteur;
    HashMap<Integer, Secteurs> couleurParSecteurs;
    // CONSTRUCTEUR
    public SecteursModel(FichierSecteurs fichiers)
    {
        this.fichiers = fichiers;
        secteur = fichiers.getSecteurs();
        couleurParSecteurs = new HashMap<>();
    }
    // METHODE n°1 : Comparer deux secteurs (incompatibilité selon critères géographiques + jours de passage)
    @Override
    public int compare(Secteurs o1, Secteurs o2)
    {
        // On récupère les sommets du secteur o1 sous forme de liste
        List<String> s1 = Arrays.asList(o1.getSommets().split(","));
        // On récupère les sommets du secteur o2 sous forme de liste
        List<String> s2 = Arrays.asList(o2.getSommets().split(","));

        // Deux secteurs sont incompatibles s'ils ont au moins 1 sommet en commun
        boolean incompatibles = s1.stream().anyMatch(s2::contains);

        if (incompatibles)
        {
            // Les secteurs sont incompatibles (voisins)
            return 0;
        }
        else
        {
            // compatibles (ne sont pas voisins)
            return Integer.compare(s1.size(), s2.size());
        }
    }
    // METHODE n°2 : Incompatilibités des secteurs
    public int getIncompatibilitesSecteurs(Secteurs o1,Secteurs o2)
    {
        //Deux secteurs sont incompatibles s'ils ont  au moins 1 sommet en commun
        // On compare deux somme
        return 0;
    }
    // METHODE 3 : Remplissage des couleurs
    // Renvoie le nombre de couleur
    // 1 couleur/secteur
    public int welshPowell() throws IOException
    {
        // On charge le graphe des secteurs
        fichiers.chargerDepuisFichier();
        // On récupère les secteurs
        // Chaque secteur a un nom, une couleur, un point de collecte,des sommets & des arcs
        HashMap<Secteurs, Set<Secteurs>> graphe = new HashMap<>();
        // On construit le graphe d'adjacence
        for(Secteurs s : secteur.values())
        {
            graphe.put(s, new HashSet<>());
            for (Secteurs s2 : secteur.values())
            {
                if (compare(s, s2) > 0) // Si les secteurs ne sont pas voisins
                {
                    graphe.get(s).add(s2); // On ajoute au graphe
                }
            }
        }
        // On trie les secteurs
        triSecteursPardegre();
        // Welsh-Powell
        Secteurs color;
        int nbzones = 0;
        // Lire la liste produits
        while(!couleurParSecteurs.isEmpty()) // On lit la case de la HashMap
        {
            for (Secteurs s : color)
            {
                // Si le produit n'a pas encore de zone
                if (s.getCouleur() == null)
                {
                    nbzones++;
                    s.setCouleur(nbzones); // on lui attribue une nouvelle zone
                }
            }
        }
        return nbzones;
    }
    // METHODE n°4 : Tri de la HashMap
    public void triSecteursPardegre(){}
}
