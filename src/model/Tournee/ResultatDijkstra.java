package model.tournee;

import model.map.Arc;
import model.map.Station;

import java.util.Map;

public class ResultatDijkstra {
    public Map<Station, Double> distances;
    public Map<Station, Arc> predecesseurs;

    public ResultatDijkstra(Map<Station, Double> distances, Map<Station, Arc> predecesseurs) {
        this.distances = distances;
        this.predecesseurs = predecesseurs;
    }
}
