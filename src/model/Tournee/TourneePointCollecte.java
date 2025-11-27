package model.Tournee;

import model.CamionModel;
import model.map.*;
import view.PlanView;

import java.util.*;

public class TourneePointCollecte {
    Plan plan;
    PlanView planV = new PlanView();
    Dijkstra dijkstra ;
    Depot depot = null;
    public List<Itineraire> listeSeg; // La suite des rues empruntées
    public double distanceTotale;


    public TourneePointCollecte(Plan plan) {
        this.plan = plan;
        this.listeSeg = new ArrayList<>();
        this.distanceTotale = 0.0;
        this.dijkstra = new Dijkstra(plan);
    }

    public void tourneePlusProcheVoisinSansCapacite () { //permet d'avoir le plan de la tournée pour que le camion la fasse
        Station here = null;
        List<Station> aVoir = new ArrayList<>();
        for (Station s : plan.getStations().values()) { //trouver le depot
            if (s instanceof Depot) {
                here = s;
                this.depot = (Depot) s;
            } else if (s instanceof PointCollecte) { //créer une liste des points de collect à voir
                aVoir.add(s);
            }
        }
        if (here == null) {
            return;
        }

        planV.afficherMessagePlan("Début de la tournée");
        planV.afficherMessagePlan("Point de départ : " + here.getNom());

        Itineraire chemin = new Itineraire(null, null, new ArrayList<>());
        while (!aVoir.isEmpty()) {
            ResultatDijkstra resultat = dijkstra.dijkstra(here);
            Station meilleurCandidat = null; //permettra de stocker le meilleur choix
            double meilleurDistance = Double.MAX_VALUE;
            for  (Station candidat : aVoir) {
                double dist = resultat.distances.getOrDefault(candidat, Double.MAX_VALUE);
                if (dist < meilleurDistance) {
                    meilleurDistance = dist;
                    meilleurCandidat = candidat;
                }
            }
            if (meilleurCandidat == null) {
                planV.afficherErreurPlan("Impossible de finir la tournée !");
                break;
            }
            Itineraire segement = chemin.reconstituerChemin(here, meilleurCandidat, meilleurCandidat, resultat.predecesseurs);// stationArriveeTrouvee
            listeSeg.add(segement); //on stock l'arc
            distanceTotale += meilleurDistance;
            here =  meilleurCandidat; //on se place sur le point de collecte
            aVoir.remove(meilleurCandidat);
        }

        if (depot != null && !here.equals(depot)) {
            ResultatDijkstra resRetour = dijkstra.dijkstra(here);
            if (resRetour.distances.get(depot) < Double.MAX_VALUE) {
                Itineraire segmentRetour = chemin.reconstituerChemin(
                        here,
                        depot,
                        depot,
                        resRetour.predecesseurs
                );
                listeSeg.add(segmentRetour);
                distanceTotale += resRetour.distances.get(depot);
            }
        }
    }

    public void tourneePlusProcheVoisinAvecCapacite () { //permet d'avoir le plan de la tournée pour que le camion la fasse
        Station here = null;
        int capaciteMax = 1000;
        int capaciteActuelle = 0;
        CamionModel camion = new CamionModel("abcdefg",capaciteMax);
        List<Station> aVoir = new ArrayList<>();
        for (Station s : plan.getStations().values()) { //trouver le depot
            if (s instanceof Depot) {
                here = s;
                this.depot = (Depot) s;
            } else if (s instanceof PointCollecte) { //créer une liste des points de collect à voir
                aVoir.add(s);
            }
        }
        if (here == null) {
            return;
        }

        planV.afficherMessagePlan("Début de la tournée");
        planV.afficherMessagePlan("Point de départ : " + here.getNom());

        Itineraire chemin = new Itineraire(null, null, new ArrayList<>());

        while (!aVoir.isEmpty() && capaciteActuelle < capaciteMax) {
            ResultatDijkstra resultat = dijkstra.dijkstra(here);
            Station meilleurCandidat = null; //permettra de stocker le meilleur choix
            double meilleurDistance = Double.MAX_VALUE;
            for  (Station candidat : aVoir) {
                double dist = resultat.distances.getOrDefault(candidat, Double.MAX_VALUE);
                if (dist < meilleurDistance) {
                    meilleurDistance = dist;
                    meilleurCandidat = candidat;
                }
            }
            if (meilleurCandidat == null) {
                planV.afficherErreurPlan("Impossible de finir la tournée !");
                break;
            }
            Itineraire segement = chemin.reconstituerChemin(here, meilleurCandidat, meilleurCandidat, resultat.predecesseurs);// stationArriveeTrouvee
            listeSeg.add(segement); //on stock l'arc
            distanceTotale += meilleurDistance;
            here =  meilleurCandidat; //on se place sur le point de collecte
            aVoir.remove(meilleurCandidat);
        }

        if (depot != null && !here.equals(depot)) {
            ResultatDijkstra resRetour = dijkstra.dijkstra(here);
            if (resRetour.distances.get(depot) < Double.MAX_VALUE) {
                Itineraire segmentRetour = chemin.reconstituerChemin(
                        here,
                        depot,
                        depot,
                        resRetour.predecesseurs
                );
                listeSeg.add(segmentRetour);
                distanceTotale += resRetour.distances.get(depot);
            }
        }
    }

}
