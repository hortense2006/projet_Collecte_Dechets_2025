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

    public void tourneePlusProcheVoisinAvecCapacite(CamionModel camion) {
        Station here = null;
        List<Station> aVoir = new ArrayList<>();

        // 1. Initialisation
        for (Station s : plan.getStations().values()) {
            if (s instanceof Depot) {
                here = s;
                this.depot = (Depot) s;
            } else if (s instanceof PointCollecte) {
                // On ne considère que les poubelles qui ont des déchets
                if (((PointCollecte) s).getNiveauRemplissage() > 0) {
                    aVoir.add(s);
                }
            }
        }

        if (here == null) {
            planV.afficherErreurPlan("Erreur : Aucun dépôt trouvé !");
            return;
        }

        planV.afficherMessagePlan("--- DÉBUT DE LA TOURNÉE UNIQUE ---");
        planV.afficherMessagePlan("Camion : " + camion.getIdCamion() + " | Capacité : " + camion.getCapaciteMax());

        Itineraire cheminUtils = new Itineraire(null, null, new ArrayList<>());

        // 2. Boucle Principale
        while (!aVoir.isEmpty()) {

            // Calculer les trajets depuis ma position actuelle
            ResultatDijkstra resultat = dijkstra.dijkstra(here);

            Station meilleurCandidat = null;
            double meilleurDistance = Double.MAX_VALUE;

            // Trouver le point le plus proche
            for (Station candidat : aVoir) {
                double dist = resultat.distances.getOrDefault(candidat, Double.MAX_VALUE);
                if (dist < meilleurDistance) {
                    meilleurDistance = dist;
                    meilleurCandidat = candidat;
                }
            }

            if (meilleurCandidat == null) break; // Plus de chemin possible

            // 3. LOGIQUE DE REMPLISSAGE "TOUR UNIQUE"
            PointCollecte pc = (PointCollecte) meilleurCandidat;
            double dechetsDansPoubelle = pc.getNiveauRemplissage();
            double placeDansCamion = camion.getCapaciteMax() - camion.getCapaciteActuelle();

            // On se déplace quoiqu'il arrive vers ce point (car c'est le plus proche et on a de la place)
            Itineraire segment = cheminUtils.reconstituerChemin(here, meilleurCandidat, meilleurCandidat, resultat.predecesseurs);
            listeSeg.add(segment);
            distanceTotale += meilleurDistance;
            here = meilleurCandidat;

            // CAS A : Tout rentre dans le camion
            if (dechetsDansPoubelle <= placeDansCamion) {
                camion.chargerDechets(dechetsDansPoubelle);
                pc.setNiveauRemplissage(0); // Poubelle vidée

                System.out.println(" -> Collecte COMPLÈTE à " + pc.getNom() + " (+" + dechetsDansPoubelle + ")");
                aVoir.remove(pc); // On retire ce point de la liste car il est vide
            }

            // CAS B : Le camion va être plein (Poubelle trop grosse ou pas assez de place)
            else {
                // On remplit le camion au maximum
                double aPrendre = placeDansCamion;
                camion.chargerDechets(aPrendre);

                // On met à jour la poubelle (il en reste dedans)
                pc.setNiveauRemplissage((int)(dechetsDansPoubelle - aPrendre));

                System.out.println(" -> Collecte PARTIELLE à " + pc.getNom() + ". Camion REMPLI à 100%.");
                System.out.println("    (Pris : " + aPrendre + " | Reste dans poubelle : " + pc.getNiveauRemplissage() + ")");

                // ARRÊT DE LA TOURNÉE : On rentre au dépôt
                planV.afficherMessagePlan("Camion plein ! Retour au dépôt et fin de la tournée.");
                break; // On sort de la boucle while
            }

            // Sécurité : Si le camion est plein pile poil (0 place), on arrête aussi
            if (camion.getCapaciteActuelle() >= camion.getCapaciteMax()) {
                planV.afficherMessagePlan("Camion plein ! Retour au dépôt.");
                break;
            }
        }

        // 4. Retour Final au Dépôt
        if (depot != null && here != null && !here.equals(depot)) {
            ResultatDijkstra resRetour = dijkstra.dijkstra(here);
            if (resRetour.distances.getOrDefault(depot, Double.MAX_VALUE) < Double.MAX_VALUE) {
                Itineraire segmentRetour = cheminUtils.reconstituerChemin(here, depot, depot, resRetour.predecesseurs);
                listeSeg.add(segmentRetour);
                distanceTotale += resRetour.distances.get(depot);
            } else {
                planV.afficherErreurPlan("Erreur : Impossible de rentrer au dépôt !");
            }
        }

        // 5. Affichage final de l'état des poubelles (Ce que tu as demandé)
        System.out.println("\n--- BILAN FIN DE TOURNÉE ---");
        System.out.println("Camion : " + camion.getCapaciteActuelle() + "/" + camion.getCapaciteMax());
        System.out.println("État des poubelles restantes :");
        for (Station s : plan.getStations().values()) {
            if (s instanceof PointCollecte) {
                PointCollecte p = (PointCollecte) s;
                if (p.getNiveauRemplissage() > 0) {
                    System.out.println(" - " + p.getNom() + " : Reste " + p.getNiveauRemplissage());
                } else {
                    System.out.println(" - " + p.getNom() + " : VIDÉ");
                }
            }
        }
        System.out.println("----------------------------");
    }
}
