package controller;

import model.CamionModel;
import model.secteurs.Secteurs;
import model.secteurs.SecteursModel;
import model.tournee.TourneeAuPiedHabitation;
import model.tournee.TourneePointCollecte;
import model.map.Plan;
import view.TourneePointCollecteView;
import view.TourAuPiedHabitationView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TourneeAuPiedHabitationController {

    private Plan plan;
    private CamionController camionController;
    private SecteursModel secteursModel;
    private TourAuPiedHabitationView view;

    public TourneeAuPiedHabitationController(Plan plan, CamionController cc, SecteursModel sm) {
        this.plan = plan;
        this.camionController = cc;
        this.secteursModel = sm;
        this.view = new TourAuPiedHabitationView();
    }

    public void lancerProcessusComplet() {
        if (secteursModel.secteur.isEmpty()) { // recupere les secteurs et les tri
            view.afficherErreur("Erreur : Aucun secteur chargé.");
            return;
        }
        List<Secteurs> listeSecteurs = new ArrayList<>(secteursModel.secteur.values());
        listeSecteurs.sort(Comparator.comparing(Secteurs::getNom));
        view.afficherListeSecteurs(listeSecteurs); // affiche les secteurs
        int choix = view.demanderChoixSecteur();

        if (choix < 1 || choix > listeSecteurs.size()) {
            view.afficherErreur("Choix invalide.");
            return;
        }

        Secteurs secteurChoisi = listeSecteurs.get(choix - 1);
        if (secteurChoisi.getEtat()) { // verifie si le secteur n'a pas deja ete fait
            boolean continuer = view.demanderConfirmation(secteurChoisi.getNom());
            if (!continuer) return;
        }

        view.afficherMessage(" -> Secteur sélectionné : " + secteurChoisi.getNom());
        CamionModel camion = camionController.selectionnerCamion(); // selection d'un camion parmis la liste
        TourneeAuPiedHabitation algo = new TourneeAuPiedHabitation(plan); // calcul de l'itineraire de la tournée
        TourneePointCollecte resultat = algo.calculerTournee(camion, secteurChoisi);
        if (resultat != null) { // affiche le resultat de la tournée
            TourneePointCollecteView vueResultat = new TourneePointCollecteView(resultat);
            vueResultat.afficherResultats();
        }
        if (camion.getCapaciteActuelle() < camion.getCapaciteMax()) { // sauvegarde l'état du camion
            camion.setEtat("disponible");
        } else {
            camion.setEtat("occupé");
        }
        camionController.sauvegarderEtatCamion(camion);
    }
}
