package map;

public class Arc
{
    // ATTRIBUTS
    private final String idLigne;
    private final Station depart;
    private final Station arrivee;
    private double distance;

    // CONSTRUCTEUR
    public Arc(String idLigne, Station depart, Station arrivee, double duree)
    {
        this.idLigne = idLigne;
        this.depart = depart;
        this.arrivee = arrivee;
        this.distance = duree;
    }

    // GETTER n°1
    public String getIdLigne() {
        return idLigne;
    }
    // GETTER n°2
    public Station getDepart() {
        return depart;
    }
    // GETTER n°3
    public Station getArrivee() {
        return arrivee;
    }
    // GETTER n°4
    public double getDistance() {
        return distance;
    }
}
