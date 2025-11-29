package model;

import exceptions.ExceptionPersonnalisable;
import model.map.Arc;
import model.map.Itineraire;
import model.map.Station;

import java.util.*;

public class CamionModel {

    private String idCamion; // Identifiant unique
    private double capaciteMax; // en kg
    private double capaciteActuelle; // en kg
    private boolean disponible; // Statut du camion
    private Station posCamion;

    public CamionModel(String idCamion, double capaciteMax)
    {
        this.idCamion = idCamion;
        this.capaciteMax = capaciteMax;
        this.capaciteActuelle = 0; //vide au départ
    }

    public double getCapaciteActuelle() {
        return capaciteActuelle;
    }
    public String getIdCamion() {
        return idCamion;
    }

    public void setCapaciteActuelle(double capaciteActuelle)
    {
        this.capaciteActuelle = capaciteActuelle;
    }
    public void setIdCamion(String idCamion)
    {
        this.idCamion = idCamion;
    }

    public boolean aDeLaPlace(double quantite) {
        return (this.capaciteActuelle + quantite) <= this.capaciteMax;
    }

    // permet de remplir le camion
    public void chargerDechets(double quantite) {
        if (aDeLaPlace(quantite)) {
            this.capaciteActuelle += quantite;
        } else {
            System.err.println("Erreur : Surcharge du camion !");
        }
    }

    public void viderCamion() {
        this.capaciteActuelle = 0;
        System.out.println("-> Le camion a été vidé au dépôt.");
    }

}
