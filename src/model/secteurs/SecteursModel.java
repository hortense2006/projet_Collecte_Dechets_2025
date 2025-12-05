package model.secteurs;

import model.map.Arc;
import model.map.Plan;

import java.io.IOException;
import java.util.*;

public class SecteursModel {

    // ATTRIBUTS
    private final FichierSecteurs fichierSecteurs;
    private final Plan plan;
    private HashMap<String,Secteurs> secteurs;
    private Map<String, Set<String>> voisins;

    // CONSTRUCTEUR
    public SecteursModel(FichierSecteurs fichierSecteurs, Plan plan)
    {
        this.fichierSecteurs = fichierSecteurs;
        this.plan = plan;
        this.secteurs = fichierSecteurs.getSecteurs();
        this.voisins = new HashMap<>();
    }

    // GETTER n°1
    public Map<String, Secteurs> getSecteurs()
    {
        return secteurs;
    }
    // GETTER n°2
    public Map<String, Set<String>> getAdjacence()
    {
        return voisins;
    }

    // METHODE n°1 : Chargement des secteurs
    public void chargerSecteurs() throws IOException
    {
        fichierSecteurs.chargerDepuisFichier();
        secteurs = fichierSecteurs.getSecteurs();
    }

    // Construire le graphe d'appartenance des sommets
    private Map<String, String> construireAppartenanceSommet()
    {
        Map<String, String> appartenance = new HashMap<>();

        for (Secteurs s : secteurs.values())
        {
            String[] sommets = s.getSommets().split(",");
            for (String sommet : sommets)
            {
                appartenance.put(sommet.trim(), s.getNom());
            }
        }
        return appartenance;
    }

    // METHODE n°3 : Construire l'adjacence entre secteurs
    public void construireAdjacence()
    {
        voisins.clear();
        Map<String, String> appartenance = construireAppartenanceSommet();

        for (Arc arc : plan.getArcs().values())
        {

            String s1 = arc.getDepart().getNom();
            String s2 = arc.getArrivee().getNom();

            String q1 = appartenance.get(s1);
            String q2 = appartenance.get(s2);

            // Pas d’adjacence si 1 sommet n'appartient à aucun secteur
            if (q1 == null || q2 == null) continue;

            // Pas d’adjacence si même secteur
            if (q1.equals(q2)) continue;

            // On enregistre l’adjacence bilatérale
            voisins.computeIfAbsent(q1, k -> new HashSet<>()).add(q2);
            voisins.computeIfAbsent(q2, k -> new HashSet<>()).add(q1);
        }
    }

    // METHODE n°4 : welsh Powell
    public void welshPowell() throws IOException
    {
        chargerSecteurs();//Charger les secteurs depuis fichier
        construireAdjacence();//Construire le graphe d'adjacence

        //Trier par degré (secteurs les plus contraints d'abord)
        List<Secteurs> liste = new ArrayList<>(secteurs.values());
        liste.sort((a, b) ->
        {
            int da = voisins.getOrDefault(a.getNom(), Set.of()).size();
            int db = voisins.getOrDefault(b.getNom(), Set.of()).size();
            return Integer.compare(db, da);
        });

        //Welsh–Powell : coloration
        int couleurActuelle = 1;

        for (Secteurs s : liste)
        {
            if (s.getCouleur() != null && !s.getCouleur().isEmpty()) continue;

            s.setCouleur("C" + couleurActuelle);

            for (Secteurs s2 : liste)
            {

                if (s2.getCouleur() != null && !s2.getCouleur().isEmpty()) continue;

                boolean compatible = true;

                Set<String> voisinV = voisins.getOrDefault(s.getNom(), Set.of());

                if (voisinV.contains(s2.getNom()))
                {
                    compatible = false;
                }
                else
                {
                    // Vérifier aussi les voisins déjà colorés
                    for (String voisin : voisins.getOrDefault(s2.getNom(), Set.of()))
                    {
                        Secteurs vSecteur = secteurs.get(voisin);
                        if (vSecteur != null && ("C" + couleurActuelle).equals(vSecteur.getCouleur()))
                        {
                            compatible = false;
                            break;
                        }
                    }
                }
                if (compatible)
                {
                    s2.setCouleur("C" + couleurActuelle);
                }
                couleurActuelle++;
            }
            couleurActuelle++;
        }

        // Sauvegarde
        fichierSecteurs.sauvegarderSecteurs();

    }
}
