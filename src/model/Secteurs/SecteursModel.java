package model.Secteurs;

import java.io.IOException;
import java.util.*;

public class SecteursModel
{
    //ATTRIBUTS
    private FichierSecteurs fichiers;
    public HashMap<String, Secteurs> secteur;
    List<Secteurs> secteursIncompatibles;
    private List<Secteurs> voisins;
    // CONSTRUCTEUR
    public SecteursModel(FichierSecteurs fichiers)
    {
        this.fichiers = fichiers;
        secteur = fichiers.getSecteurs();
        secteursIncompatibles = new ArrayList<>();
    }
    // METHODE n°1 : Comparer deux secteurs (incompatibilité selon critères géographiques + jours de passage)

    public List<Secteurs> comparerSecteurs(Secteurs o1, Secteurs o2)
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
        Secteurs sfusionne = fusionnerSecteurs(o1, o2);
        secteursIncompatibles.add(sfusionne);// On met le couple de secteurs compatibles dans une hashmap.
    }
    // Sinon : compatibles (ne sont pas voisins)
        return secteursIncompatibles;
    }

    // METHODE 2: Fusion des secteurs
    public Secteurs fusionnerSecteurs(Secteurs s1, Secteurs s2)
    {

        // Nouveau nom pour le secteur fusionné
        String nouveauNom = s1.getNom() + "_" + s2.getNom();

        // Fusion des sommets (évite les doublons)
        Set<String> nouveauxSommets = new HashSet<>(Arrays.asList(s1.getSommets().split(",")));
        nouveauxSommets.addAll(Arrays.asList(s2.getSommets().split(",")));

        // Fusion des arcs
        Set<String> nouveauxArcs = new HashSet<>(Arrays.asList(s1.getArcAssocie().split(",")));
        nouveauxArcs.addAll(Arrays.asList(s2.getArcAssocie().split(",")));

        // Créer un nouveau secteur fusionné
        Secteurs s = new Secteurs(
                nouveauNom,"BLANC",  // couleur par défaut, car on ne l'as pas encore calculée
                String.join(",", nouveauxSommets),
                String.join(",", nouveauxArcs)
        );
        fichiers.sauvegarderSecteurs(s);
        return s;
    }

    //METHODE n°3 :  Renvoie le nombre de couleurs (le nombre de secteurs)
    // 1 couleur/secteur
    public void welshPowell() throws IOException
    {
        // On charge le graphe des secteurs
        fichiers.chargerDepuisFichier();
        // On récupère la liste des secteurs incompatibles
        // secteursIncompatibles = graphe d'adjacence
        // Chaque secteur a un nom, une couleur, un point de collecte,des sommets & des arcs
        for(Secteurs o1 : secteur.values())
        {
            for(Secteurs o2 : secteur.values())
            {
                if(!o1.equals(o2))
                {
                    comparerSecteurs(o1, o2);
                }
            }
        }
        // On trie les secteurs
        triSecteursPardegre(secteursIncompatibles);
        // Welsh-Powell
        int nbzones = 0;
        // Lire la liste produits
        while(!secteursIncompatibles.isEmpty()) // On lit la case de la liste
        {
            for (Secteurs s : secteursIncompatibles)
            {
                // Si le produit n'a pas encore de zone
                if (s.getCouleur() == null)
                {
                    nbzones++;
                    s.setCouleur(String.valueOf(nbzones)); // on lui attribue une nouvelle zone
                }
            }
        }
    }
    // METHODE n°4 : Tri de la HashMap
    public void triSecteursPardegre(List<Secteurs> secteursIncompatibles)
    {
        // On récupère la liste de secteurs et on la trie
        secteursIncompatibles.sort(Comparator.comparingInt(Secteurs::getDegre).reversed());
    }
}
