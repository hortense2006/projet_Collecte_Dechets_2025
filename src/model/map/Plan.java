package model.map;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.Random;

import exceptions.ExceptionPersonnalisable;
import model.FichierDemandes;
import model.particulier.DemandeCollecte;
import view.PlanView;

public class Plan {

    private Map<String, Station> stations; //plan de la ville
    private HashMap<String, Arc> arcs; // Arcs (rues)
    public PlanView planView = new PlanView();

    //constructeur
    public Plan() {
        this.stations = new HashMap<>();
        this.arcs = new HashMap<>();
    }

    public Map<String, Station> getStations() {
        return stations;
    }

    public Station getStation(String nomStation) {
        return stations.get(nomStation);
    }

    public HashMap<String, Arc> getArcs() {
        return arcs;
    } // Méthode pour récupérer tous les arcs

    // Récupérer un Arc à partir du nom de rue
    public Arc getArcParNom(String nomRue) {
        String nomRechercheNettoye = nettoyerNomRue(nomRue);

        for (Arc arc : arcs.values()) {
            String idArcComplet = arc.getIdLigne();
            if (idArcComplet == null) continue; // Sécurité

            // On nettoie l'id complet de l'arc
            String idArcNettoye = nettoyerNomRue(idArcComplet);

            // Vérification
            if (idArcNettoye.startsWith(nomRechercheNettoye)) {
                return arc;
            }
        }
        return null;
    }// renvoie null si le nom n'existe pas
    private String nettoyerNomRue(String rue) {
        if (rue == null) return "";
        return rue
                .trim()
                .toLowerCase()
                .replace("'", "") // Retirer l'apostrophe droite
                .replace("’", "") // Retirer l'apostrophe typographique
                .replace("é", "e") // Remplacer é
                .replace("è", "e") // Remplacer è
                .replace("à", "a") // Remplacer à
                .replace("â", "a")// Remplacer â
                .replace("ô","o"); // Remplacer ô
    }

    public enum modeOrientation { //Permet de déterminer l'orientation du graphe
        HO1_NON_ORIENTE, // rue à double sens
        HO2_ORIENTE, // rue à sens unique
        HO3_MIXTE // rue mixte (double sens et sens unique)
    }

    public Station creerStation(String nom) {
        if (nom.startsWith("D")) {
            return new Depot(nom);
        }
        else if (nom.startsWith("R")) {
            return new Intersection(nom);
        }
        else if (nom.startsWith("P")) {
            return new PointCollecte(nom, 10000);
        }
        else if (nom.startsWith("M")) {
            return new Station(nom);
        }
        return null;
    }

    // Permet de lire un plan et de la charger dans les elements necessaires
    public void chargerPlan(String nomFichier, modeOrientation mode) throws IOException {

        // variable necessaire au chargement et à la création du graphe
        this.stations.clear();
        int nbArcsAjoutes = 0;
        String typeGraphe = "";

        try (BufferedReader br = new BufferedReader(new FileReader(nomFichier))) {
            String ligne;

            while ((ligne = br.readLine()) != null) {
                if (ligne.trim().isEmpty() || ligne.trim().startsWith("#")) {
                    continue;
                }
                String[] parties = ligne.split(";");//sépare au niveau de chaque ;

                String nomRue = ""; //permet d'avoir un vrai nom de rue
                String nomRueNormalise = null;
                String nomDepart;
                String nomArrivee;
                boolean estSensUnique = false;
                double distance = 0.0;
                boolean ajouterArcBA = false;

                if (parties.length == 4) {// pour les 2 premier HO1 et HO2
                    nomRue = parties[0].trim(); //nom de la rue donc l'arc
                    nomRueNormalise = nomRue.toLowerCase();
                    nomDepart = parties[1].trim(); //intersection d'arrivée
                    nomArrivee = parties[2].trim(); //intersection de fin

                    try {
                        distance = Double.parseDouble(parties[3].trim());//récupère la distance de la rue
                    } catch (ExceptionPersonnalisable e) {
                        planView.afficherErreurPlan("Ligne ignorée (distance invalide) : " + ligne); //lance une exception si elle n'est pas valide
                        continue;
                    }
                } else if (parties.length == 5) { // pour HO3
                    nomRue = parties[0].trim(); //le nom de la rue
                    nomRueNormalise = nomRue.toLowerCase();
                    nomDepart = parties[1].trim(); // le point de départ
                    String sens = parties[2].trim(); //le sens
                    nomArrivee = parties[3].trim(); //fin de la rue

                    if (sens.equals(">")) {
                        estSensUnique = true;
                    } else if (sens.equals("-")) {
                        estSensUnique = false;
                    } else {
                        planView.afficherErreurPlan("Symbole de direction inconnu : " + ligne);
                        continue;
                    }
                    try {
                        distance = Double.parseDouble(parties[4].trim());
                    } catch (ExceptionPersonnalisable e) {
                        planView.afficherErreurPlan("Ligne ignorée (distance invalide) : " + ligne); //lance une exception si elle n'est pas valide
                        continue;
                    }
                } else {
                    planView.afficherErreurPlan("Ligne ignorée (erreur de format) : " + ligne);
                    continue;
                }

                Station depart = stations.computeIfAbsent(nomDepart, this::creerStation); // création des sommets
                Station arrivee = stations.computeIfAbsent(nomArrivee, this::creerStation);
                Arc arcAB = new Arc(nomRue, depart, arrivee, distance); // créer un arc type
                String cleAB = nomRueNormalise + "_" + nomDepart + "_" + nomArrivee;
                this.arcs.put(cleAB, arcAB);
                depart.ajouterArcSortant(arcAB); // créer la première connection
                nbArcsAjoutes++; //incrémente de nom d'arc

                if (mode == modeOrientation.HO1_NON_ORIENTE) {//initialise le type des graphe
                    ajouterArcBA = true; //elles sont toutes à double sens
                    typeGraphe = "HO1 (Strictement Double Sens)";
                } else if (mode == modeOrientation.HO2_ORIENTE) {
                    typeGraphe = "HO2 (Strictement Sens Unique)";
                } else if (mode == modeOrientation.HO3_MIXTE) {
                    if (!estSensUnique) {
                        ajouterArcBA = true; // C'est une rue double sens
                    }
                    typeGraphe = "HO3 (Graphe Mixte)";
                }

                if (ajouterArcBA) { //si c'est à double sens alors elle créer l'arc associé dans le sens inverse
                    Arc arcBA = new Arc(nomRue, arrivee, depart, distance);
                    String cleBA = nomRueNormalise + "_" + nomArrivee + "_" + nomDepart;
                    this.arcs.put(cleBA, arcBA);
                    arrivee.ajouterArcSortant(arcBA);
                    nbArcsAjoutes++;
                }
            }
        }
        planView.afficherMessagePlan("Le plan a été chargé avec succés. L'orientation est : " + typeGraphe);
    }
}
