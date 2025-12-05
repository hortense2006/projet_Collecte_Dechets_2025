package model.tournee;

import model.map.Station;

public class Noeud implements Comparable<Noeud> {
    Station station;
    double cout;

    public Noeud(Station station, double cout) {
        this.station = station;
        this.cout = cout;
    }

    @Override
    public int compareTo(Noeud other) {
        return Double.compare(this.cout, other.cout);
    }
}
