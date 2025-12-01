package view;

import model.map.Arc;
import model.map.Itineraire;
import model.map.Station;

import java.util.List;

public class ItineraireView {

    Itineraire itineraire;

    public ItineraireView (Itineraire itineraire)
    {
        this.itineraire = itineraire;
    }

    // afficher l'itinéraire de la tournée
    public void afficherItineraire(Itineraire itineraire) {

        List<Arc> chemin = itineraire.getChemin(); // On récupère la liste une bonne fois pour toutes
        int sizeChemin = chemin.size();

        if (chemin.isEmpty()) {
            System.out.println("Itinéraire non trouvé");
            return;
        }
        System.out.println("Itinéraire : " + itineraire.getDepart().getNom() + " vers " + itineraire.getArrivee().getNom() + " - ");
        System.out.println("Total Stations : " + itineraire.getNombreStations() + " : Total de changements : " + itineraire.getNombreChangements());
        if (itineraire.getDistanceTotale() != -1.0) {
            System.out.println("Durée totale estimée : " +itineraire.getDistanceTotale()+ " minutes.");
        }
        Station current = itineraire.getDepart(); // on initialise la station à celle de départ
        String ligneCourante = "";
        for (int i = 0; i < sizeChemin; i++) {
            Arc arc = chemin.get(i);
            String ligneArc = arc.getIdLigne();
            if (!ligneArc.equals(ligneCourante)) { // vérifie si la ligne courante est différente de la suivant
                if (i > 0) {
                    System.out.println(" Correspondance à " + current.getNom());
                }
                System.out.println("Prendre la ligne " + ligneArc + " depuis " + current.getNom());
                ligneCourante = ligneArc;
            }
            current = arc.getArrivee();
            if (i == sizeChemin - 1) {
                System.out.println("Arrivée : " + current.getNom()); //arrivée atteinte
            } else {
                System.out.println("Continuer jusqu'à " + current.getNom()); //continue si elle n'est pas trouvée
            }
        }
    }
}
