package map;

public class PointCollecte extends Station
{
    // CONSTRUCTEUR
    public PointCollecte(String nom) {
        super(nom);
    }
    // METHODE n°1
    public String toString() {
        return "PointDeCollecte : " + getNom();
    }
}
