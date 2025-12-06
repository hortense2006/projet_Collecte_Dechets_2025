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
    private FichierDemandes demandeC;
    private ParticulierModel particuliermodel;
    private Queue<DemandeCollecte> liste;
    public CamionView camionV;
    public String nomFichier;

    public CamionController(EntrepriseController entreprise,ParticulierModel particuliermodel, CamionView camionV,FichierDemandes demandeC) {
        this.entreprise = entreprise;
        this.particuliermodel= particuliermodel;
        this.camionV = camionV;
        this.demandeC = demandeC;
        this.liste = new LinkedList<>();
    }

    public void executerTournee(String nomVille,String nomFichier) {
        FichierDemandes fd = new FichierDemandes(nomFichier);
        fd.chargerDepuisFichier(); // lit le fichier et remplit fileDemandes
        particuliermodel.setDemande(nomVille,fd.getFileDemandes());
        // Récupère la liste des demandes
        liste = particuliermodel.getDemande(nomVille);
        // On récupère la liste de demandes et on les exécute à l'aide de CollecteDemande
        Itineraire itineraireFinal = entreprise.collecteDemande(liste);
        camionV.afficherItineraireE(itineraireFinal);
        demandeC.viderDemande(nomFichier);
    }

    public CamionModel selectionnerCamion(String nomFichier) {
        CamionModel camionChoisi = null;
        while (camionChoisi == null) {
            List<CamionModel> camions = CamionModel.chargerCamions(nomFichier); // afficher les données
            camionV.afficherListeCamions(camions);
            String idSaisi = camionV.demanderIdCamion(); // récupère ce qu'a choisi l'utilisateur
            boolean trouve = false;
            for (CamionModel c : camions) {
                if (c.getIdCamion().equals(idSaisi)) {
                    trouve = true;
                    if (c.getEtat().equalsIgnoreCase("disponible")) { // le camion est trouvé
                        camionV.afficherMessage("Succès : Camion " + idSaisi + " sélectionné.");
                        CamionModel.changerEtatCamion(idSaisi, "occupé", nomFichier); // mise à jour du fichier texte
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

    // sauvegarde l'état du camion à la fin de la tournée
    public void sauvegarderEtatCamion(CamionModel c, String nomFichier) {
        CamionModel.mettreAJourCamion(c, nomFichier);
    }

    // vide un camion
    public void viderUnCamion(String nomFichier) {
        List<CamionModel> camions = CamionModel.chargerCamions(nomFichier);// affiche les camions
        camionV.afficherListeCamions(camions);
        String id = camionV.demanderIdCamion(); //demande qui doit être vider
        for (CamionModel c : camions) {
            if (c.getIdCamion().equals(id)) {
                c.viderCamion(); // le vide et donc remet à 0
                c.setEtat("disponible"); // remet son état à disponible

                CamionModel.mettreAJourCamion(c, nomFichier); // sauvegarde du fichier
                camionV.afficherMessage("Le camion " + id + " a été vidé et est disponible.");
                return;
            }
        }
        camionV.afficherErreur("Camion introuvable.");
    }

    // permet de vider tout les camions à la fois
    public void viderTousCamions() {
        List<CamionModel> camions = CamionModel.chargerCamions(nomFichier);
        CamionModel.reinitialiserTousCamions(nomFichier); // appel de la fonction créée ci-dessus
        camionV.afficherMessage("Succès : Tous les camions ont été vidés et sont disponibles.");
    }

}
