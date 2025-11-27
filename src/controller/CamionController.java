package controller;

import model.FichierDemandes;
import model.particulier.DemandeCollecte;
import model.particulier.ParticulierModel;

import java.util.Queue;

public class CamionController
{
    //planifierTournee()
    private EntrepriseController entreprise;
    private ParticulierModel particuliermodel;

    public CamionController(EntrepriseController entreprise,ParticulierModel particuliermodel)
    {
        this.entreprise = entreprise;
        this.particuliermodel= particuliermodel;
    }
    // METHODE n°4 : Faire la tournée
    public void executerTournee(String nomFichier)
    {
        FichierDemandes fd = new FichierDemandes(nomFichier);
        fd.chargerDepuisFichier(); // lit le fichier et remplit fileDemandes
        particuliermodel.setDemande(fd.getFileDemandes());
        // Récupère la liste des demandes
        Queue<DemandeCollecte> liste = particuliermodel.getDemande();
        System.out.println(liste);
        // On récupère la liste de demandes et on les execute à l'aide de CollecteDemande
        entreprise.CollecteDemande(liste);
    }
}
