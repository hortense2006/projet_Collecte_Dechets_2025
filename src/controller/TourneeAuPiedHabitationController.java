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
        // 1. Récupération des secteurs
        if (secteursModel.secteur.isEmpty()) {
            view.afficherErreur("Erreur : Aucun secteur chargé.");
            return;
        }

        List<Secteurs> listeSecteurs = new ArrayList<>(secteursModel.secteur.values());
        listeSecteurs.sort(Comparator.comparing(Secteurs::getNom));

        Secteurs secteurChoisi = null;

        // 2. Boucle de choix (Tant que l'utilisateur ne choisit pas un secteur valide)
        while (secteurChoisi == null) {
            // On affiche la liste (je modifierai la vue juste après pour enlever les voisins)
            view.afficherListeSecteurs(listeSecteurs, listeSecteurs);

            int choix = view.demanderChoixSecteur();

            if (choix < 1 || choix > listeSecteurs.size()) {
                view.afficherErreur("Choix invalide. Veuillez entrer un numéro valide.");
                continue; // On recommence la boucle
            }

            Secteurs candidat = listeSecteurs.get(choix - 1);

            // Règle unique : Est-ce qu'il est déjà fait ?
            if (candidat.getEtat()) {
                view.afficherErreur("ERREUR : Le secteur " + candidat.getNom() + " est déjà fait/bloqué. Choisissez-en un autre.");
                continue; // On recommence la boucle
            }

            // Si on arrive ici, c'est bon
            secteurChoisi = candidat;
        }

        view.afficherMessage(" -> Secteur validé : " + secteurChoisi.getNom());

        // 3. Camion et Tournée
        CamionModel camion = camionController.selectionnerCamion();

        TourneeAuPiedHabitation taph = new TourneeAuPiedHabitation(plan);
        TourneePointCollecte resultat = taph.calculerTournee(camion, secteurChoisi);

        // 4. Affichage
        if (resultat != null) {
            TourneePointCollecteView vueResultat = new TourneePointCollecteView(resultat);
            vueResultat.afficherResultats();
        }

        // 5. Sauvegarde Camion
        if (camion.getCapaciteActuelle() < camion.getCapaciteMax()) {
            camion.setEtat("disponible");
        } else {
            camion.setEtat("occupé");
        }
        camionController.sauvegarderEtatCamion(camion);
    }
}
