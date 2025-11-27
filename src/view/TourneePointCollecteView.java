package view;

import model.Tournee.TourneePointCollecte;
import model.map.Itineraire;

public class TourneePointCollecteView {

    TourneePointCollecte tpc;

    public TourneePointCollecteView(TourneePointCollecte tpc) {
        this.tpc = tpc;
    }

    public void afficherResultats() {
        System.out.println("Affichage du trajet de la tournée");
        int etape = 1;
        for (Itineraire segment : tpc.listeSeg) {
            System.out.println("\n Étape n°" + etape + " : ");
            segment.afficherItineraire(); // affiche le trajet
            etape++;
        }
        System.out.println("\n");
        System.out.println("Distance parcouru lors de la tournée : " + tpc.distanceTotale + " mètres");
    }
}
