package model.map;

import model.particulier.DemandeCollecte;

import java.util.ArrayList;
import java.util.List;

public class Arc {

    private String idLigne;
    private final Station depart;
    private final Station arrivee;
    private double distance;

    private List<DemandeCollecte> dechetsSurLaVoie;

    public Arc(String idLigne, Station depart, Station arrivee, double duree) {
        this.idLigne = idLigne;
        this.depart = depart;
        this.arrivee = arrivee;
        this.distance = duree;
        this.dechetsSurLaVoie = new ArrayList<>();
    }
    public Arc(Station depart, Station arrivee, double duree)
    {
        this.depart = depart;
        this.arrivee = arrivee;
        this.distance = duree;
    }

    public String getIdLigne() {
        return idLigne;
    }
    public Station getDepart() {
        return depart;
    }
    public Station getArrivee() {
        return arrivee;
    }
    public double getDistance() {
        return distance;
    }

}
