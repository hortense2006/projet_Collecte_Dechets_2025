package map;

public class Intersection extends Station{

    public Intersection(String nom){
        super(nom);
    }

    public String toString() {
        return "Intersection : " + getNom() ;
    }
}
