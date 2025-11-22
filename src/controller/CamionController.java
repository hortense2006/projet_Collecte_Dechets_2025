package controller;

import model.particulier.ParticulierModel;

public class CamionController
{
    //planifierTournee()
    private EntrepriseController entreprise;
    private ParticulierModel particulierm;

    public CamionController(EntrepriseController entreprise,ParticulierModel particulierm)
    {
        this.entreprise = entreprise;
        this.particulierm = particulierm;
    }
    // METHODE n°4 : Faire la tournée
    public void executerTournee()
    {
        entreprise.CollecteDemande(particulierm.getDemande());
    }
}
