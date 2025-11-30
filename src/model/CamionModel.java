package model;

import model.map.Station;


public class CamionModel
{

    // ATTRIBUTS
    private String idCamion; // Identifiant unique
    private double capaciteMax; // en kg
    private double capaciteActuelle; // en kg
    private boolean disponible; // Statut du camion
    private Station posCamion;

    // CONSTRUCTEUR
    public CamionModel(String idCamion, double capaciteMax)
    {
        this.idCamion = idCamion;
        this.capaciteMax = capaciteMax;
        this.capaciteActuelle = 0; //vide au départ
    }

    // GETTER n°1
    public double getCapaciteActuelle() {
        return capaciteActuelle;
    }
    // GETTER n°2
    public String getIdCamion() {
        return idCamion;
    }
    // GETTER n°3
    public boolean getDisponible() {
        return disponible;
    }

    // SETTER n°1
    public void setCapaciteActuelle(double capaciteActuelle)
    {
        this.capaciteActuelle = capaciteActuelle;
    }
    // SETTER n°2
    public void setIdCamion(String idCamion)
    {
        this.idCamion = idCamion;
    }

    // METHODE n°1 : Identifie la capacité du camion
    public boolean aDeLaPlace(double quantite) {
        return (this.capaciteActuelle + quantite) <= this.capaciteMax;
    }

    // METHODE n°2 : permet de remplir le camion
    public void chargerDechets(double quantite)
    {
        if (aDeLaPlace(quantite))
        {
            this.capaciteActuelle += quantite;
        }
        else
        {
            System.err.println("Erreur : Surcharge du camion !");
        }
    }

    // METHODE n°3 : On réinitialise la capacité du camion
    public void viderCamion()
    {
        this.capaciteActuelle = 0;
        System.out.println("-> Le camion a été vidé au dépôt.");
    }

}
