package controller;

import model.map.Plan;
import model.map.PointCollecte;
import model.map.Station;
import view.PointCollecteView;

public class PointCollecteController {

    private Plan plan;
    private PointCollecteView pdcV;

    public PointCollecteController(Plan plan, PointCollecteView pdcV) {
        this.plan = plan;
        this.pdcV = pdcV;
    }

    public void depotDechetAuPointCollecte() {
        String nomPoint = pdcV.demanderNomPoint(); //saisie du nom du point
        Station station = plan.getStation(nomPoint); //on le trouve sur le plan
        if (station == null) { // on verifie s'il existe
            pdcV.afficherMessage("Erreur : Ce point de collecte n'existe pas dans la ville.");
            return;
        }
        if (!(station instanceof PointCollecte)) {
            pdcV.afficherMessage("Erreur : " + nomPoint + " n'est pas un point de collecte (c'est une intersection ou un dépôt).");
            return;
        }
        int quantite = pdcV.demanderQuantite();// on demande la quantité

        if (quantite <= 0) {
            pdcV.afficherMessage("Erreur : La quantité doit être un nombre positif.");
            return;
        }

        PointCollecte pc = (PointCollecte) station;
        pc.remplir(quantite); // on le remplie
        PointCollecte.sauvegarderEtat(plan);

        pdcV.afficherMessage("Vous avez déposé " + quantite + " unités dans " + pc.getNom());
        pdcV.afficherMessage("Niveau actuel du conteneur : " + pc.getNiveauRemplissage());
    }
}