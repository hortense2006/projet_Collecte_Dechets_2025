package model.map;

public class Depot extends Station {
    Plan plan;

    public Depot (String nom){
        super(nom);
    }

    public String toString() { //afficher détail
        return "Depot : " + getNom();
    }

    public Station getDepot() {
        for (Station s : plan.getStations().values()) {
            if (s instanceof Depot) {
                return s;
            }
        }
        return null;
    }
}
