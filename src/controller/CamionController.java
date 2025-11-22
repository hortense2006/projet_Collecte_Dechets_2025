package controller;

import model.particulier.ParticulierModel;

public class CamionController
{
    //planifierTournee()
    private EntrepriseController entreprise;
    private ParticulierController particuliercontroller;

    public CamionController(EntrepriseController entreprise,ParticulierController particuliercontroller)
    {
        this.entreprise = entreprise;
        this.particuliercontroller= particuliercontroller;
    }
    // METHODE n°4 : Faire la tournée
    public void executerTournee()
    {
        // On récupère la liste de demandes et on les execute à l'aide de CollecteDemande
        entreprise.CollecteDemande(particuliercontroller.remplirListeDemandeCollecte());
    }
}
