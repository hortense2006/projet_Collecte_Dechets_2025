package view;

import model.map.Arc;
import model.map.Itineraire;
import model.map.Station;

import java.util.List;

public class CamionView
{
    // CONSTRUCTEUR
    public void afficherItineraire(){}
    // METHODE n°1 : Afficher l'itinéraire
    public void afficherItineraireE(Itineraire itineraire)
    {

        List<Arc> chemin = itineraire.getChemin(); // On récupère la liste une bonne fois pour toutes
        int sizeChemin = chemin.size();

        if (chemin.isEmpty())
        {
            System.out.println("Itinéraire non trouvé");
            return;
        }
        System.out.println("Itinéraire : " + itineraire.getDepart().getNom() + " vers " + itineraire.getArrivee().getNom() + " - ");
        System.out.println("Total Stations : " + itineraire.getNombreStations() + " : Total de changements : " + itineraire.getNombreChangements());

        Station current = itineraire.getDepart(); // on initialise la station à celle de départ
        String ligneCourante = "";
        for (int i = 0; i < sizeChemin; i++)
        {
            Arc arc = chemin.get(i);
            String ligneArc = simplifierNomLigne(arc.getIdLigne());
            if (!ligneArc.equals(ligneCourante))
            { // vérifie si la ligne courante est différente de la suivant
                if (i > 0)
                {
                    System.out.println(" Correspondance à " + current.getNom());
                }
                System.out.println("Prendre la ligne " + ligneArc + " depuis " + current.getNom());
                ligneCourante = ligneArc;
            }
            current = arc.getArrivee();
            if (i == sizeChemin - 1)
            {
                System.out.println("Arrivée : " + current.getNom()); //arrivée atteinte
            }
            else
            {
                System.out.println("Continuer jusqu'à " + current.getNom()); //continue si elle n'est pas trouvée
            }
        }
    }
    // METHODE n°2 : Simplifier l'affichage des noms de lignes (évite la multiplication de M-M1-M2 à la suite)
    private String simplifierNomLigne(String nomLigne)
    {
        int pos = nomLigne.indexOf("-");
        if (pos == -1) return nomLigne; // pas de M1/M2
        return nomLigne.substring(0, pos); // on prend seulement la rue
    }

}
