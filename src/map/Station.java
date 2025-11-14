package map;
import java.util.ArrayList;
import java.util.List;

// Classe de fond pour tout les lieux du plan
// tel que le dépot, les points de collecte et les intersections

public class Station {

    private String nom;
    private ArrayList<Arc> arcsSortants;

    public Station(String nom) { //constructeur
        this.nom = nom;
        this.arcsSortants = new ArrayList<>();
    }

    public String getNom(){
        return nom;
    }
    public List<Arc> getArcsSortants() {
        return this.arcsSortants;
    }

    public String setNom(String nom) {
        this.nom = nom;
        return nom;
    }

    public void ajouterArcSortant(Arc arc) {
        this.arcsSortants.add(arc);
    }

    public String toString() { //afficher détail
        return "Station : " + getNom();
    }

}
