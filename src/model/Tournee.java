package model;

import model.map.Arc;
import model.map.Depot;
import model.map.Plan;
import model.map.PointCollecte;
import model.map.Station;

import java.util.*;

/*public class Tournee {
    public Plan plan;
    public List<Arc> itineraire; // La suite des rues empruntées
    public double distanceTotale;

    public Tournee(Plan plan) {
        this.plan = plan;
        this.itineraire = new ArrayList<>();
        this.distanceTotale = 0.0;
    }
    public void calculer() {
        // 1. Identifier le point de départ (Dépôt) et les points à visiter
        Station positionActuelle = null;
        List<Station> aVisiter = new ArrayList<>();

        for (Station s : plan.getStations().values()) {
            if (s instanceof Depot) {
                positionActuelle = s;
            } else if (s instanceof PointCollecte) {
                aVisiter.add(s);
            }
        }

        if (positionActuelle == null) {
            System.err.println("Erreur : Pas de dépôt dans le plan !");
            return;
        }

        System.out.println("--- Début de la tournée (Départ : " + positionActuelle.getNom() + ") ---");

        // 2. Boucle Principale : Tant qu'il reste des poubelles à ramasser
        while (!aVisiter.isEmpty()) {

            // Etape A : Calculer les distances depuis ma position vers TOUS les autres points
            ResultatChemin res = calculerDistancesReelles(positionActuelle);

            // Etape B : Trouver le point le plus proche dans la liste 'aVisiter'
            Station meilleurCandidat = null;
            double minDistance = Double.MAX_VALUE;

            for (Station candidat : aVisiter) {
                double dist = res.distances.getOrDefault(candidat, Double.MAX_VALUE);
                if (dist < minDistance) {
                    minDistance = dist;
                    meilleurCandidat = candidat;
                }
            }

            if (meilleurCandidat == null) {
                System.out.println("Impossible d'atteindre les points restants (cul-de-sac ou sens unique bloquant).");
                break;
            }

            // Etape C : Se déplacer vers ce point
            List<Arc> chemin = res.reconstruireCheminVers(meilleurCandidat);
            itineraire.addAll(chemin);
            ajouterDistance(chemin);

            System.out.println(" -> Aller à " + meilleurCandidat.getNom() + " (Distance: " + minDistance + "m)");

            // Etape D : Mettre à jour la position et la liste
            positionActuelle = meilleurCandidat;
            aVisiter.remove(meilleurCandidat);
        }

        // 3. Retour au Dépôt à la fin
        Station depot = null;
        for(Station s : plan.getStations().values()) { if(s instanceof Depot) depot = s; }

        if (depot != null && !positionActuelle.equals(depot)) {
            ResultatChemin resRetour = calculerDistancesReelles(positionActuelle);
            double distRetour = resRetour.distances.getOrDefault(depot, Double.MAX_VALUE);

            if (distRetour < Double.MAX_VALUE) {
                List<Arc> cheminRetour = resRetour.reconstruireCheminVers(depot);
                itineraire.addAll(cheminRetour);
                ajouterDistance(cheminRetour);
                System.out.println(" -> Retour au Dépôt (Distance: " + distRetour + "m)");
            } else {
                System.out.println("Impossible de retourner au dépôt !");
            }
        }
    }

}*/
