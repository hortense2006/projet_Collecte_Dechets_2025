package controller;
import model.Commune;
import model.EntrepriseModel;

public class EntrepriseController extends Commune
{
    private EntrepriseModel entrepriseModel;
    public EntrepriseController(EntrepriseModel em)
    {
        this.entrepriseModel = em;
    }
    public void cheminParcouru()
    {
        do{}while(true);
    }
    // Boucle while qui parcourt la liste des demandes
    // (somme d'itinéraires (programme executerDemande dans EntrepriseModel))
    // Renvoie au Camion le chemin à faire.
}
