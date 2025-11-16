package model.map;

public class Depot extends Station {

    public Depot (String nom){
        super(nom);
    }

    public String toString() { //afficher détail
        return "Depot : " + getNom();
    }
}
