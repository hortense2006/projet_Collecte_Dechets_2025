package model.tournee;

import model.CamionModel;
import model.map.*;
import view.PlanView;
import view.TourneePointCollecteView;

import java.util.*;

public class TourneePointCollecte {

    public Plan plan;
    public PlanView planV = new PlanView();
    public Dijkstra dijkstra ;
    public Depot depot = null;
    public TourneePointCollecteView tpcV ;
    public List<Itineraire> listeSeg; // La suite des rues empruntées
    public double distanceTotale;


    public TourneePointCollecte(Plan plan) {
        this.plan = plan;
        this.listeSeg = new ArrayList<>();
        this.distanceTotale = 0.0;
        this.dijkstra = new Dijkstra(plan);
        this.tpcV = new TourneePointCollecteView(this);
    }

    public void tourneePlusProcheVoisinAvecCapacite(CamionModel camion) {
        Station here = null;
        List<Station> aVoir = new ArrayList<>();
        for (Station s : plan.getStations().values()) { // initialisation
            if (s instanceof Depot) {
                here = s;
                this.depot = (Depot) s;
            } else if (s instanceof PointCollecte) {
                if (((PointCollecte) s).getNiveauRemplissage() > 0) { // on ne prends que les poubelles état remplies
                    aVoir.add(s);
                }
            }
        }
        if (here == null) { // si le dépot n'est pas trouvé
            planV.afficherErreur("Erreur : Aucun dépôt trouvé !");
            return;
        }

        planV.afficherMessage("Début de la tournée");
        planV.afficherMessage("Camion : " + camion.getIdCamion() + " | Capacité : " + camion.getCapaciteMax());

        Itineraire chemin = new Itineraire(null, null, new ArrayList<>());

        while (!aVoir.isEmpty()) {
            ResultatDijkstra resultat = dijkstra.dijkstra(here); //calcul du trajet depuis où on se trouve
            Station meilleurCandidat = null;
            double meilleurDistance = Double.MAX_VALUE;
            for (Station candidat : aVoir) { //trouver le point le plus proche
                double distAller = resultat.distances.getOrDefault(candidat, Double.MAX_VALUE);
                if (distAller < Double.MAX_VALUE && distAller < meilleurDistance) {
                    ResultatDijkstra testRetour = dijkstra.dijkstra(candidat);
                    double distRetourDepot = testRetour.distances.getOrDefault(depot, Double.MAX_VALUE);

                    if (distRetourDepot < Double.MAX_VALUE) {
                        meilleurDistance = distAller;
                        meilleurCandidat = candidat;
                    } else {
                        System.out.println("Point " + ((PointCollecte)candidat).getNom() + " ignoré car cul-de-sac.");
                    }
                }
            }
            if (meilleurCandidat == null) break; // Plus de chemin possible

            PointCollecte pc = (PointCollecte) meilleurCandidat; // logique avec le remplissage du camion
            double dechetsDansPoubelle = pc.getNiveauRemplissage();
            double placeDansCamion = camion.getCapaciteMax() - camion.getCapaciteActuelle();

            // On se déplace quoiqu'il arrive vers ce point car c'est le plus proche et on a de la place
            Itineraire segment = chemin.reconstituerChemin(here, meilleurCandidat, meilleurCandidat, resultat.predecesseurs);
            listeSeg.add(segment);
            distanceTotale += meilleurDistance;
            here = meilleurCandidat;

            if (dechetsDansPoubelle <= placeDansCamion) { // si tout rentre dans la camion
                camion.chargerDechets(dechetsDansPoubelle);
                pc.setNiveauRemplissage(0); // point de collecte vidé

                System.out.println(" Collecte complète à " + pc.getNom() + " (+" + dechetsDansPoubelle + ")");
                aVoir.remove(pc); // On retire ce point de la liste car il est vide
            }

            else { // si le camion ne pourra pas contenir toute la poubelle
                double aPrendre = placeDansCamion;// On remplit le camion au maximum
                camion.chargerDechets(aPrendre);
                pc.setNiveauRemplissage((int)(dechetsDansPoubelle - aPrendre)); // on met à jour le point de collecte (ce qu'il y avait - ce qu'on a pris)

                System.out.println("Collecte partielle à " + pc.getNom() + ". Camion rempli.");
                System.out.println("    (Pris : " + aPrendre + " | Reste dans poubelle : " + pc.getNiveauRemplissage() + ")");
                planV.afficherMessage("Camion plein ! Retour au dépôt et fin de la tournée."); // fin de la tournée
                break; // On sort de la boucle while
            }
            if (camion.getCapaciteActuelle() >= camion.getCapaciteMax()) { // si le camion est tout plein on s'arrète
                planV.afficherMessage("Camion plein ! Retour au dépôt.");
                break;
            }
        }
        if (depot != null && here != null && !here.equals(depot)) { // retour au dépot
            ResultatDijkstra resRetour = dijkstra.dijkstra(here);
            if (resRetour.distances.getOrDefault(depot, Double.MAX_VALUE) < Double.MAX_VALUE) {
                Itineraire segmentRetour = chemin.reconstituerChemin(here, depot, depot, resRetour.predecesseurs);
                listeSeg.add(segmentRetour);
                distanceTotale += resRetour.distances.get(depot);
            } else {
                planV.afficherErreur("Erreur : Impossible de rentrer au dépôt ");
            }
        }

        tpcV.affichageBilanTournee(plan,camion);

    }

}
