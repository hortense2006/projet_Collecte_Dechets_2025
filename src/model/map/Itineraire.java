package model.map;

import java.util.*;

public class Itineraire {
    private final Station depart;
    private final Station arrivee;
    private final List<Arc> chemin;
    private double distanceTotal;

    public Itineraire(Station depart, Station arrivee, List<Arc> chemin) {
        this.depart = depart;
        this.arrivee = arrivee;
        this.chemin = chemin;
        this.distanceTotal = -1;
    }

    public int getNombreStations() {
        return chemin.size() + 1;
    }
    public List<Arc> getChemin () {
        return chemin;
    }
    public double getDistanceTotale() {
        return distanceTotal;
    }
    public Station  getDepart() {
        return depart;
    }
    public Station getArrivee() {
        return arrivee;
    }

    public int getNombreChangements() {
        int changements = 0;
        if (chemin.isEmpty()) {
            return 0;
        }
        String ligneCourante = chemin.get(0).getIdLigne(); //recupère le numéro de la ligne
        for (int i = 1; i < chemin.size(); i++) {
            String ligneSuivante = chemin.get(i).getIdLigne();
            if (!ligneSuivante.equals(ligneCourante)) { // verifie si la lignes courante est différentes de celle d'après
                changements++; // si oui on augmente les changements
                ligneCourante = ligneSuivante; //et on passe la suivante à la courante
            }
        }
        return changements;
    }

    public static Itineraire reconstituerChemin (Station depart, Station arrivee, Station stationArriveeTrouvee, Map<Station, Arc> predecesseurs) { //méthode qui servira à l'affichage d'un chemin
        if (stationArriveeTrouvee == null) {
            return new Itineraire(depart, arrivee, Collections.emptyList());
        }
        LinkedList<Arc> cheminInverse = new LinkedList<>(); //retrace le chemin à l'envers
        Station courant = arrivee;
        while (predecesseurs.get(courant) != null) { //si on a une information avant
            Arc arcEntrant = predecesseurs.get(courant);
            cheminInverse.addFirst(arcEntrant);
            courant = arcEntrant.getDepart();
        }
        return new Itineraire(depart, arrivee, new ArrayList<>(cheminInverse)); //retourne pour l'affichage
    }
}
