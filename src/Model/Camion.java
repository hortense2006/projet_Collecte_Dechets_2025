package Model;

public class Camion
{
    // ATTRIBUTS
    private String idCamion;                       // Identifiant unique
    private double capaciteMax;                    // Charge maximale (kg ou L)
    private double capaciteActuelle;               // Charge actuelle
    private boolean disponible;                    // Statut du camion

    // CONSTRUCTEUR
    public Camion(String idCamion, double capaciteMax)
    {
        this.idCamion = idCamion;
        this.capaciteMax = capaciteMax;
    }

    // GETTER n°1
    public void getIdCamion(String idCamion)
    {
        this.idCamion = idCamion;
    }
    // GETTER n°2
    public void getCapaciteActuelle(double capaciteActuelle)
    {
        this.capaciteActuelle = capaciteActuelle;
    }

    // METHODE n°1 : Chargement du camion
    public void chargerDechets(double quantite){}
    // METHODE n°2 : Vider le camion
    public void viderCamion(){}
    // METHODE n°3 : Planification de la tournée
    public void planifierTournee(){}
    // METHODE n°4 : Faire la tournée
    public void executerTournee(){}
    // METHODE n°5 : Afficher l'itinéraire
    public void afficherItineraire(){}


}
