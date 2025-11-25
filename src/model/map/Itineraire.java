package model.map;

import java.util.*;

public class Itineraire {
    private final Station depart;
    private final Station arrivee;
    private final List<Arc> chemin;
    private double dureeTotale;

    public Itineraire(Station depart, Station arrivee, List<Arc> chemin) {
        this.depart = depart;
        this.arrivee = arrivee;
        this.chemin = chemin;
        this.dureeTotale = -1;
    }

    public int getNombreStations() {
        return chemin.size() + 1;
    }
    public List<Arc> getChemin () {
        return chemin;
    }
    public double getDureeTotale() {
        return dureeTotale;
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

    public void afficherItineraire() {
        if (chemin.isEmpty()) {
            System.out.println("Itinéraire non trouvé");
            return;
        }
        System.out.println("Itinéraire : " + depart.getNom() + " vers " + arrivee.getNom() + " - ");
        System.out.println("Total Stations : " + getNombreStations() + " : Total de changements : " + getNombreChangements());
        if (dureeTotale != -1.0) {
            System.out.println("Durée totale estimée : " +dureeTotale+ " minutes.");
        }
        Station current = depart; // on initialise la station à celle de départ
        String ligneCourante = "";
        for (int i = 0; i < chemin.size(); i++) {
            Arc arc = chemin.get(i);
            String ligneArc = arc.getIdLigne();
            if (!ligneArc.equals(ligneCourante)) { // vérifie si la ligne courante est différente de la suivant
                if (i > 0) {
                    System.out.println(" Correspondance à " + current.getNom());
                }
                System.out.println("Prendre la ligne " + ligneArc + " depuis " + current.getNom());
                ligneCourante = ligneArc;
            }
            current = arc.getArrivee();
            if (i == chemin.size() - 1) {
                System.out.println("Arrivée : " + current.getNom()); //arrivée atteinte
            } else {
                System.out.println("Continuer jusqu'à " + current.getNom()); //continue si elle n'est pas trouvée
            }
        }
    }

    public Itineraire reconstituerChemin (Station depart, Station arrivee, Station stationArriveeTrouvee, Map<Station, Arc> predecesseurs) { //méthode qui servira à l'affichage d'un chemin
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
