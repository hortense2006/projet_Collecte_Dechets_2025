package map;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Plan {

    public Plan () {}

    public void chargerReseau(String nomFichier) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(nomFichier))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                String[] parties = ligne.split(";"); //sépare chaque ligne en 3 morceaux
                String idLigne = parties[0].trim();
                String nomDepart = parties[1].trim();
                String nomArrivee = parties[2].trim();
                Station depart = stations.getOrDefault(nomDepart, new Station(nomDepart)); // création des sommets
                Station arrivee = stations.getOrDefault(nomArrivee, new Station(nomArrivee));
                stations.putIfAbsent(nomDepart, depart);
                stations.putIfAbsent(nomArrivee, arrivee);
                double duree = dtmin + Math.random() * (dtmax - dtmin); // Simulation aléatoire de la durée
                Arc arc = new Arc(idLigne, depart, arrivee,duree); //oriente le graphe
                depart.ajouterArcSortant(arc);
            }
        }
        System.out.println("Réseau chargé avec succès depuis " + nomFichier + ". " + stations.size() + " stations uniques trouvées.");
    }
}
