package map;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import Exceptions.ExceptionPersonnalisable;

public class Plan {

    private Map<String, Station> stations; //plan de la ville

    public Plan() { //constructeur
        this.stations = new HashMap<>();
    }

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

    // Menu quipermet de choisir quel type de plan on veut utiliser et afficher le dit plan associé
    public void chargerPlan(String nomFichier, modeOrientation mode) throws IOException {
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
                    } catch (ExceptionPersonnalisable e) {
                        System.err.println("Ligne ignorée (distance invalide) : " + ligne); //lance une exception si elle n'est pas valide
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
                        System.err.println("Symbole de direction inconnu : " + ligne);
                        continue;
                    }
                    try {
                        distance = Double.parseDouble(parties[3].trim());
                    } catch (ExceptionPersonnalisable e) {
                        System.err.println("Ligne ignorée (distance invalide) : " + ligne); //lance une exception si elle n'est pas valide
                        continue;
                    }
                } else {
                    System.err.println("Ligne ignorée (erreur de format) : " + ligne);
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
        System.out.println("Réseau chargé avec succès ");
        System.out.println("Mode de chargement : " + typeGraphe);
    }

    public void afficherReseau() { //affiche le plan de la ville chargée
        System.out.println("Affichage du plan de la ville");
        for (Station station : stations.values()) {
            System.out.print("(" + station.getNom() + " - " + station.getClass().getSimpleName() + ") est relié à : "); //affiche un point
            if (station.getArcsSortants().isEmpty()) { // si la route ne mene à rien
                System.out.println("Aucune autre station.");
            } else {
                List<String> voisins = new ArrayList<>();
                for (Arc arc : station.getArcsSortants()) {
                    voisins.add(arc.getArrivee().getNom()); //affiche son voisin
                }
                System.out.println(String.join(", ", voisins)); //continue
            }
        }
    }

    public void choixFichier() {
        String nomFichier = "";
        int choixPlan;
        Scanner sc = new Scanner(System.in);

        System.out.println("Sur quel type de plan voulez vous vous baser : " + //permet le choix du type de plan que nous utiliserons
                "\n - 1 : à bouble sens" +
                "\n - 2 : à sens unique" +
                "\n - 3 : réaliste");
        choixPlan = sc.nextInt();

        switch (choixPlan) { //applique le type de plan que nous utilisons
            case 1:
                nomFichier = "C:\\Users\\valsa\\IdeaProjects\\Théorie des graphes\\projet_Collecte_Dechets_2025\\src\\Ranville_HO1.txt";
                //c'est un absolute path tu peux le changer pour qu'il fonctionne sur ton PC si tu veux
                // suffit de clic droit sur le fichier HO, et faire get absolute path et encore get absolut path et coller
                //merci de n'utiliser aucune autre forme que tu absolute path

                try {
                    chargerPlan(nomFichier, Plan.modeOrientation.HO1_NON_ORIENTE);
                    System.out.println("Plan de la ville : ");
                    afficherReseau();
                    System.out.println("\n");
                    sc.nextLine();
                } catch (IOException e) { //lance l'erreur necessaire
                    System.err.println("ERREUR : Impossible de lire le fichier du réseau.");
                    System.err.println("Détail de l'erreur: " + e.getMessage());
                    return;
                }
                break;
            case 2:
                nomFichier = "C:\\Users\\valsa\\IdeaProjects\\Théorie des graphes\\projet_Collecte_Dechets_2025\\src\\Ranville_HO2.txt";
                try {
                    chargerPlan(nomFichier, Plan.modeOrientation.HO2_ORIENTE);
                    System.out.println("Plan de la ville : ");
                    afficherReseau();
                    System.out.println("\n");
                    sc.nextLine();
                } catch (IOException e) {
                    System.err.println("ERREUR : Impossible de lire le fichier du réseau.");
                    System.err.println("Détail de l'erreur: " + e.getMessage());
                    return;
                }
                break;
            case 3:
                nomFichier = "C:\\Users\\valsa\\IdeaProjects\\Théorie des graphes\\projet_Collecte_Dechets_2025\\src\\Ranville_HO3.txt";
                try {
                    chargerPlan(nomFichier, Plan.modeOrientation.HO3_MIXTE);
                    System.out.println("Plan de la ville : ");
                    afficherReseau();
                    System.out.println("\n");
                    sc.nextLine();
                } catch (IOException e) {
                    System.err.println("ERREUR : Impossible de lire le fichier du réseau.");
                    System.err.println("Détail de l'erreur: " + e.getMessage());
                    return;
                }
                break;
        }
    }
}
