package controller;
import model.Commune;
import model.EntrepriseModel;
import model.map.Itineraire;
import model.particulier.DemandeCollecte;

public class EntrepriseController extends Commune
{
    private EntrepriseModel em;
    private ParticulierController pc;
    public EntrepriseController(ParticulierController pc)
    {
        this.pc = pc;
    }
    // L'entreprise récupère la demande du particulier, exécute la demande, et renvoie l'itinéraire au camion.
    public Itineraire cheminParcouru()
    {
        // On récupère la demande du particulier
        DemandeCollecte demandeParticulier = pc.DemandeCollecte();
        // deux cas possibles : exécution immédiate ou au bout de 5 requêtes
        Itineraire itin = em.executerDemande(demandeParticulier); // L'execution et l'enlèvement de la demande sont fait par la commune
        // Celle-ci sert d'intermédiaire entre le particulier & l'entreprise
        em.defilerDemande(demandeParticulier); // Une fois la demande exécutée, on retire la demande de la file.
        return itin;
    }
    // Boucle while qui parcourt la liste des demandes
    // (somme d'itinéraires (programme executerDemande dans EntrepriseModel))
    // Renvoie au Camion le chemin à faire.
}
