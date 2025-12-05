package model.tournee;

import model.map.Arc;
import model.map.Plan;
import model.map.Station;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Dijkstra {

    Plan plan ;

    public Dijkstra(Plan plan) {
        this.plan = plan;
    }

    public model.tournee.ResultatDijkstra dijkstra (Station depart) {

        Map<Station, Double> distances = new HashMap<>();
        Map<Station, Arc> predecesseurs = new HashMap<>();
        PriorityQueue<Noeud> file = new PriorityQueue<>();

        for (Station s : plan.getStations().values()) { //initialisation des distance à l'infinie
            distances.put(s, Double.MAX_VALUE);
        }
        distances.put(depart, 0.0);
        file.add(new Noeud(depart, 0.0));

        while (!file.isEmpty()) {
            Noeud courant = file.poll(); //position où on se trouve
            Station u = courant.station;
            if (courant.cout > distances.get(u)) {// si on trouve mieux alors on ignore
                continue;
            }

            for (Arc a : u.getArcsSortants()) { // on explore les voisins par les arcs sortants
                Station v = a.getArrivee();
                double newDist = distances.get(u) + a.getDistance();
                if (newDist < distances.get(v)) {
                    distances.put(v, newDist);
                    predecesseurs.put(v, a); // on note l'arc utilisé pour arriver ici
                    file.add(new Noeud(v, newDist));
                }
            }
        }
        return new ResultatDijkstra(distances, predecesseurs);// retourne l'objet contenant les deux trajets
    }
}