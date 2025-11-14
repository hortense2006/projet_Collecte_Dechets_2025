package map;
import java.util.ArrayList;
import java.util.List;

// Classe de fond pour tout les lieux du plan
// tel que le dépot, les points de collecte et les intersections

public class Station
{
    // ATTRIBUTS
    private String nom;
    private ArrayList<Arc> arcsSortants;

    //constructeur
    public Station(String nom)
    {
        this.nom = nom;
        this.arcsSortants = new ArrayList<>();
    }

    // GETTER n°1
    public String getNom(){
        return nom;
    }
    // GETTER n°2
    public List<Arc> getArcsSortants() {
        return this.arcsSortants;
    }
    // SETTER n°1
    public String setNom(String nom)
    {
        this.nom = nom;
        return nom;
    }

    // METHODE n°1
    public void ajouterArcSortant(Arc arc) {
        this.arcsSortants.add(arc);
    }
    // METHODE n°2
    public String toString() { //afficher détail
        return "Station : " + getNom();
    }

}
