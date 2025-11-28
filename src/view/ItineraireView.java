package view;

import model.map.Arc;
import model.map.Itineraire;
import model.map.Station;

public class ItineraireView {

    Itineraire i;

    public void afficherItineraire() {
        if (i.getChemin().isEmpty()) {
            System.out.println("Itinéraire non trouvé");
            return;
        }
        System.out.println("Itinéraire : " + depart.getNom() + " vers " + arrivee.getNom() + " - ");
        System.out.println("Total Stations : " + getNombreStations() + " : Total de changements : " + getNombreChangements());
        if (i.getDistanceTotale() != -1.0) {
            System.out.println("Durée totale estimée : " +dureeTotale+ " minutes.");
        }
        Station current = depart; // on initialise la station à celle de départ
        String ligneCourante = "";
        for (int i = 0; i < chemin.size(); i++) {
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
            if (i == chemin.size() - 1) {
                System.out.println("Arrivée : " + current.getNom()); //arrivée atteinte
            } else {
                System.out.println("Continuer jusqu'à " + current.getNom()); //continue si elle n'est pas trouvée
            }
        }
    }

}
