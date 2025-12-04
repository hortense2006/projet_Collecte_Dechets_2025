package controller;

import model.CamionModel;
import model.FichierDemandes;
import model.map.Itineraire;
import model.particulier.DemandeCollecte;
import model.particulier.ParticulierModel;
import view.CamionView;
import view.ItineraireView;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class CamionController {

    private EntrepriseController entreprise;
    private ParticulierModel particuliermodel;
    private Queue<DemandeCollecte> liste;
    CamionView camionV;

    public CamionController(EntrepriseController entreprise,ParticulierModel particuliermodel, CamionView camionV) {
        this.entreprise = entreprise;
        this.particuliermodel= particuliermodel;
        this.camionV = camionV;
        this.liste = new LinkedList<>();
    }

    public void executerTournee(String nomFichier)
    {
        FichierDemandes fd = new FichierDemandes(nomFichier);
        fd.chargerDepuisFichier(); // lit le fichier et remplit fileDemandes
        particuliermodel.setDemande(fd.getFileDemandes());
        // Récupère la liste des demandes
        liste = particuliermodel.getDemande();
        // On récupère la liste de demandes et on les exécute à l'aide de CollecteDemande
        Itineraire itineraireFinal = entreprise.CollecteDemande(liste);
        camionV.afficherItineraireE(itineraireFinal);
    }

    public CamionModel selectionnerCamion() {
        CamionModel camionChoisi = null;
        while (camionChoisi == null) {
            List<CamionModel> camions = CamionModel.chargerCamions(); // afficher les données
            camionV.afficherListeCamions(camions);
            String idSaisi = camionV.demanderIdCamion(); // récupère ce qu'a choisi l'utilisateur
            boolean trouve = false;
            for (CamionModel c : camions) {
                if (c.getIdCamion().equals(idSaisi)) {
                    trouve = true;
                    if (c.getEtat().equalsIgnoreCase("disponible")) { // le camion est trouvé
                        camionV.afficherMessage("Succès : Camion " + idSaisi + " sélectionné.");
                        CamionModel.changerEtatCamion(idSaisi, "occupé"); // mise à jour du fichier texte
                        c.setEtat("occupé"); // Mise à jour locale pour le retour
                        camionChoisi = c; // recupère le camion que nous avons choisi
                    } else { // le camion demandé n'est pas disponible
                        camionV.afficherErreur("ERREUR : Ce camion est " + c.getEtat() + ". Choisissez-en un autre."); // le camion demandé n'existe pas
                    }
                    break;
                }
            }
            if (!trouve) { // le camion n'existe pas
                camionV.afficherErreur("ERREUR : Identifiant introuvable dans la liste.");
            }
        }
        return camionChoisi;
    }

    // remettre le bon état au camion s'il est libre
    public void libererCamion(String idCamion) {
        CamionModel.changerEtatCamion(idCamion, "disponible");
        camionV.afficherMessage("Info : Le camion " + idCamion + " est de nouveau disponible.");
    }

    public void sauvegarderEtatCamion(CamionModel c) {
        CamionModel.mettreAJourCamion(c);
    }

    public void viderUnCamion() {
        List<CamionModel> camions = CamionModel.chargerCamions();// affiche les camions
        camionV.afficherListeCamions(camions);
        String id = camionV.demanderIdCamion(); //demande qui doit être vider
        for (CamionModel c : camions) {
            if (c.getIdCamion().equals(id)) {
                c.viderCamion(); // le vide et donc remet à 0
                c.setEtat("disponible"); // remet son état à disponible

                CamionModel.mettreAJourCamion(c); // sauvegarde du fichier
                camionV.afficherMessage("Le camion " + id + " a été vidé et est disponible.");
                return;
            }
        }
        camionV.afficherErreur("Camion introuvable.");
    }

    public void viderTousCamions() {
        List<CamionModel> camions = CamionModel.chargerCamions();
        CamionModel.reinitialiserTousCamions(); // appel de la fonction créée ci-dessus
        camionV.afficherMessage("Succès : Tous les camions ont été vidés et sont disponibles.");
    }

}
