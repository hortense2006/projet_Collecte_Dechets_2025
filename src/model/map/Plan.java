package model.map;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import exceptions.ExceptionPersonnalisable;
import view.PlanView;

public class Plan {

    private Map<String, Station> stations; //plan de la ville
    private HashMap<String, Arc> arcs; // Arcs (rues)
    public PlanView planView = new PlanView();

    public Plan() { //constructeur

        this.stations = new HashMap<>();
        this.arcs = new HashMap<>();
    }

    public Map<String, Station> getStations() {
        return stations;
    }
    public Station getStation(String nomStation) {
        return stations.get(nomStation);
    }
    public Station getStationP(Arc rue, double numero) {
        String cle = rue + "-" + numero;   // clé composite
        return stations.get(cle);
    }
    // Méthode pour récupérer tous les arcs
    public HashMap<String, Arc> getArcs() {
        return arcs;
    }

    // Récupérer un Arc à partir du nom de rue
    public Arc getArcParNom(String nomRue) {
        return arcs.get(nomRue); // renvoie null si le nom n'existe pas
    }
    //Permet de déterminer l'orientation du graphe
    public enum modeOrientation {
        HO1_NON_ORIENTE, // rue à double sens
        HO2_ORIENTE, // rue à sens unique
        HO3_MIXTE // rue mixte (double sens et sens unique)
    }

    private Station creerStation(String nom) {
        if (nom.startsWith("D")) {
            return new Depot(nom);
        } else if (nom.startsWith("R")) {
            return new Intersection(nom);
        } else if (nom.startsWith("P")) {
            return new PointCollecte(nom);
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

                String nomDepart;
                String nomArrivee;
                boolean estSensUnique = false;
                double distance = 0.0;
                boolean ajouterArcBA = false;

                if (parties.length == 3) { // pour les 2 premier HO1 et HO2
                    nomDepart = parties[0].trim();
                    nomArrivee = parties[1].trim();
                    try {
                        distance = Double.parseDouble(parties[2].trim());//récupère la distance de la rue
                    } catch (ExceptionPersonnalisable e) {
                        planView.afficherErreurPlan("Ligne ignorée (distance invalide) : " + ligne); //lance une exception si elle n'est pas valide
                        continue;
                    }
                } else if (parties.length == 4) { // pour HO3
                    nomDepart = parties[0].trim(); //récupère le départ de la rue
                    String sens = parties[1].trim();
                    nomArrivee = parties[2].trim(); //récupère la fin de la rue
                    if (sens.equals(">")) {
                        estSensUnique = true;
                    } else if (sens.equals("-")) {
                        estSensUnique = false;
                    } else {
                        planView.afficherErreurPlan("Symbole de direction inconnu : " + ligne);
                        continue;
                    }
                    try {
                        distance = Double.parseDouble(parties[3].trim());
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
                Arc arcAB = new Arc(nomDepart + "-" + nomArrivee, depart, arrivee, distance); // créer un arc type
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
                    Arc arcBA = new Arc(nomArrivee + "-" + nomDepart, arrivee, depart, distance);
                    arrivee.ajouterArcSortant(arcBA);
                    nbArcsAjoutes++;
                }
            }
        }
        planView.afficherMessagePlan("Le plan a été chargé avec succés. L'orientation est : " + typeGraphe);
    }


}
