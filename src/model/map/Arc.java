package model.map;

public class Arc
{

    private final String idLigne;
    private final Station depart;
    private final Station arrivee;
    private double distance;


    public Arc(String idLigne, Station depart, Station arrivee, double duree) {
        this.idLigne = idLigne;
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
