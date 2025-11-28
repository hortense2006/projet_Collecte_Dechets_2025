package model.map;
import java.util.ArrayList;
import java.util.List;

// Classe de fond pour tout les lieux du plan
// tel que le dépot, les points de collecte et les intersections

public class Station {
    private String nom;
    private ArrayList<Arc> arcsSortants;
    private double numero;

    //constructeur n°1
    public Station(String nom) {
        this.nom = nom;
        this.arcsSortants = new ArrayList<>();
    }
    // CONSTRUCTEUR n°2
    public Station(String nom, double numero) {
        this.nom = nom;
        this.numero = numero;
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
    // Ajouter un arc au graphe
    public void ajouterArc(Arc a)
    {
        // Ajoute l'arc à la station de départ
        a.getDepart().ajouterArcSortant(a);
    }

    // Supprimer un arc du graphe
    public void supprimerArc(Arc a) {
        // Supprime l'arc de la liste des arcs sortants de la station de départ
        a.getDepart().getArcsSortants().remove(a);
    }



}
