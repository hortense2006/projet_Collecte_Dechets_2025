package controller;

import model.FichierDemandes;
import model.map.Itineraire;
import model.particulier.DemandeCollecte;
import model.particulier.ParticulierModel;
import view.CamionView;
import view.ItineraireView;

import java.util.Queue;

public class CamionController
{
    private EntrepriseController entreprise;
    private ParticulierModel particuliermodel;
    private CamionView camionView;

    public CamionController(EntrepriseController entreprise, ParticulierModel particuliermodel, CamionView camionView)
    {
        this.entreprise = entreprise;
        this.particuliermodel= particuliermodel;
        this.camionView = camionView;
    }
    // METHODE n°4 : Faire la tournée
    public void executerTournee(String nomFichier)
    {
        FichierDemandes fd = new FichierDemandes(nomFichier);
        fd.chargerDepuisFichier(); // lit le fichier et remplit fileDemandes
        particuliermodel.setDemande(fd.getFileDemandes());
        // Récupère la liste des demandes
        Queue<DemandeCollecte> liste = particuliermodel.getDemande();
        // On récupère la liste de demandes et on les execute à l'aide de CollecteDemande
        Itineraire itineraireFinal = entreprise.CollecteDemande(liste);
        camionView.afficherItineraireE(itineraireFinal);
    }
}
