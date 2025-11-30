package model.map;

import java.util.List;

// Création des secteurs dans la ville
public class Secteurs
{
    // ATTRIBUTS
    private String nomP;
    private int zone;
    // CONSTRUCTEUR
    public Secteurs(String nomP, int zone)
    {
        this.nomP = nomP;
        this.zone = zone;
    }
    // GETTER n°1 : nom
    public String getNom() {return nomP;}
    // GETTER n°2 : zone
    public int  getZone() {return zone;}
    // SETTER n°1 : zone
    public void setZone(int zone) {
        this.zone = zone;
    }
}
