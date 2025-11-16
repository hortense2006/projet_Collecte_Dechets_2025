package model.map;

public class PointCollecte extends Station {

    public PointCollecte(String nom) {
        super(nom);
    }

    public String toString() {
        return "PointDeCollecte : " + getNom();
    }
}
