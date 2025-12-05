package view;

import model.CamionModel;
import model.tournee.TourneePointCollecte;
import model.map.Itineraire;
import model.map.Plan;
import model.map.PointCollecte;
import model.map.Station;

public class TourneePointCollecteView {

    TourneePointCollecte tpc;

    public TourneePointCollecteView(TourneePointCollecte tpc) {
        this.tpc = tpc;
    }

    // affiche le trajet de la tournée + la distance parcourue
    public void afficherResultats() {
        System.out.println("Affichage du trajet de la tournée");
        int etape = 1;
        for (Itineraire segment : tpc.listeSeg) {
            ItineraireView itineraireV = new ItineraireView(segment);
            System.out.println("\n Étape n°" + etape + " : ");
            itineraireV.afficherItineraire(segment); // affiche le trajet
            etape++;
        }
        System.out.println("\n");
        System.out.println("Distance parcouru lors de la tournée : " + tpc.distanceTotale + " mètres");
    }


    // affiche le bilan des points de collecte
    public void affichageBilanTournee (Plan plan, CamionModel camion) {
        System.out.println("\n      Bilan de la tournée :       "); // affichage de l'état des point de collectes
        System.out.println("Camion : " + camion.getCapaciteActuelle() + "/" + camion.getCapaciteMax());
        System.out.println("État des poubelles restantes :");
        for (
                Station s : plan.getStations().values()) {
            if (s instanceof PointCollecte) {
                PointCollecte p = (PointCollecte) s;
                if (p.getNiveauRemplissage() > 0) {
                    System.out.println(" - " + p.getNom() + " : Reste " + p.getNiveauRemplissage());
                } else {
                    System.out.println(" - " + p.getNom() + " : vidé");
                }
            }
        }
    }


}
