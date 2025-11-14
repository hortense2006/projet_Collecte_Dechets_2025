package map;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Plan
{
    // ATTRIBUTS
    private Map<String, Station> stations; //plan de la ville
    // CONSTRUCTEUR
    public Plan() { //constructeurs
        this.stations = new HashMap<>();
    }

    public enum ModeOrientation
    {
        HO1_NON_ORIENTE, // rue à double sens
        HO2_ORIENTE, // rue à sens unique
        HO3_MIXTE // rue mixte (double sens et sens unique)
    }

    // METHODE n°1
    private Station CreerStation (String nom)
    {
        if (nom.equals("D")) {
            return new Depot(nom);
        }
        else if (nom.equals("R")) {
            return new Intersection(nom);
        }
        else if (nom.equals("P")) {
            return new PointCollecte(nom);
        }
        return null;
    }

    // METHODE n°2
    public void chargerReseau(String nomFichier, ModeOrientation mode) throws IOException
    {
        // variable necessaire au chargement et à la création du graphe
        this.stations.clear();
        int nbArcsAjoutes = 0;
        String typeGraphe = "";
        String nomDepart;
        String nomArrivee;
        boolean estSensUnique = false;
        double distance = 0.0;
        boolean ajouterArcBA = false;

        try (BufferedReader br = new BufferedReader(new FileReader(nomFichier))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                if (ligne.trim().isEmpty() || ligne.trim().startsWith("#")) {
                    continue;
                }
                String[] parties = ligne.split(";");//sépare au niveau de chaque ;

                if (parties.length == 3) { // pour les 2 premier HO1 et HO2
                    nomDepart = parties[0].trim();
                    nomArrivee = parties[1].trim();
                    try {
                        distance = Double.parseDouble(parties[2].trim());//récupère la distance de la rue
                    } catch (ExceptionMap e) {
                        System.err.println("Ligne ignorée (distance invalide) : " + ligne); //lance une exception si elle n'est pas valide
                        continue;
                    }
                }
                else if (parties.length == 4) { // pour HO3
                    if (!parties[1].trim().equals(">")) {
                        System.err.println("Ligne ignorée (format invalide) : " + ligne);
                        continue;
                    }
                    nomDepart = parties[0].trim(); //récupère le départ de la rue
                    nomArrivee = parties[2].trim(); //récupère la fin de la rue
                    estSensUnique = true;
                    try {
                        distance = Double.parseDouble(parties[2].trim());
                    } catch (ExceptionMap e) {
                        System.err.println("Ligne ignorée (distance invalide) : " + ligne); //lance une exception si elle n'est pas valide
                        continue;
                    }
                }
                else {
                    System.err.println("Ligne ignorée (nombre inférieur à 3 ou supérieur à 4) : " + ligne);
                    continue;
                }

                Station depart = stations.computeIfAbsent(nomDepart, this::CreerStation); // création des sommets
                Station arrivee =stations.computeIfAbsent(nomArrivee, this::CreerStation);
                Arc arcAB = new Arc(nomDepart + "-" + nomArrivee, depart, arrivee, distance); // créer un arc type
                depart.ajouterArcSortant(arcAB); // créer la première connection
                nbArcsAjoutes++; //incrémente de nom d'arc

                if (mode == ModeOrientation.HO1_NON_ORIENTE) {//initialise le type des graphe
                    ajouterArcBA = true; //elles sont toutes à double sens
                    typeGraphe = "HO1 (Strictement Double Sens)";
                }
                else if (mode == ModeOrientation.HO2_ORIENTE){
                    typeGraphe = "HO2 (Strictement Sens Unique)";
                }
                else if  (mode == ModeOrientation.HO3_MIXTE) {
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
        System.out.println("Réseau chargé avec succès depuis " + nomFichier);
        System.out.println("Mode de chargement : " + typeGraphe);
    }

    // METHODE n°3
    public void afficherReseau() { //affiche le plan de la ville chargée
        System.out.println("Affichage du plan de la ville");
        for (Station station : stations.values()) {
            System.out.print("(" + station.getNom() + " - " + station.getClass().getSimpleName() + ") est relié à : "); //affiche un point
            if (station.getArcsSortants().isEmpty()) { // si la route ne mene à rien
                System.out.println("Aucune autre station.");
            } else {
                List<String> voisins = new ArrayList<>();
                for (Arc arc : station.getArcsSortants()) {
                    voisins.add(arc.getArrivee().getNom() + "(" + (int)arc.getDistance() + ")"); //affiche son voisin
                }
                System.out.println(String.join(", ", voisins)); //continue
            }
        }
    }
}
