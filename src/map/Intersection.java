package map;

public class Intersection extends Station
{

    // CONSTRUCTEUR
    public Intersection(String nom){
        super(nom);
    }

    // METHODE n°1
    public String toString() {
        return "Intersection : " + getNom() ;
    }
}
