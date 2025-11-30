package view;

import model.Tournee.TourneePointCollecte;
import model.map.Itineraire;

public class TourneePointCollecteView
{

    // ATTRIBUTS
    TourneePointCollecte tpc;

    // CONSTRUCTEUR
    public TourneePointCollecteView(TourneePointCollecte tpc) {
        this.tpc = tpc;
    }

    // METHODE N°1 : Afficher le trajet de la tournée + la distance parcourue
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


}
