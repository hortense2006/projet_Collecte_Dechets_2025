package view;

import model.CamionModel;

import java.util.List;
import java.util.Scanner;
import model.map.Arc;
import model.map.Itineraire;
import model.map.Station;

import java.util.List;
public class CamionView
{

    // ATTRIBUTS
    private Scanner sc;

    // CONSTRUCTEUR
    public CamionView() {
        this.sc = new Scanner(System.in);
    }

    // METHODE n°1 : Afficher un message
    public void afficherMessage(String message) {
        System.out.println(message);
    }

    // METHODE n°2 :Afficher un message d'erreur
    public void afficherErreur(String message) {
        System.err.println(message); // Affiche en rouge dans la console souvent
    }
    // METHODE n°3 : Afficher la liste des camions disponibles
    public void afficherListeCamions(List<CamionModel> camions) {
        System.out.println("\n-Liste des camions");
        System.out.printf("%-15s | %-15s | %s%n", "ID", "État", "Capacité");
        System.out.println("\n");
        for (CamionModel c : camions) {
            System.out.printf("%-15s | %-15s | %s%n",
                    c.getIdCamion(),
                    c.getEtat(),
                    (int)c.getCapaciteMax());
        }
    }
    // METHODE n°4 : Demander l'id du camion
    public String demanderIdCamion() {
        System.out.print("Entrez l'identifiant du camion à utiliser : ");
        return sc.nextLine().trim();
    }
    // METHODE n°5 : Afficher l'itinéraire
    public void afficherItineraireE(Itineraire itin)
    {

        List<Arc> cheminE = itin.getChemin(); // On récupère la liste une bonne fois pour toutes
        int sizeChemin = cheminE.size();

        if (cheminE.isEmpty())
        {
            System.out.println("Itinéraire non trouvé");
            return;
        }
        System.out.println("Itinéraire : " + itin.getDepart().getNom() + " vers " + itin.getArrivee().getNom() + " - ");
        System.out.println("Total Stations : " + itin.getNombreStations() + " : Total de changements : " + itin.getNombreChangements());

        Station current = itin.getDepart(); // on initialise la station à celle de départ
        String ligneCourante = "";
        for (int i = 0; i < sizeChemin; i++)
        {
            Arc arc = cheminE.get(i);
            String ligneArc = simplifierNomLigne(arc.getIdLigne());
            if (!ligneArc.equals(ligneCourante))
            { // vérifie si la ligne courante est différente de la suivant
                if (i > 0)
                {
                    System.out.println(" Correspondance à " + current.getNom());
                }
                System.out.println("Prendre la ligne " + ligneArc + " depuis " + current.getNom());
                ligneCourante = ligneArc;
            }
            current = arc.getArrivee();
            if (i == sizeChemin - 1)
            {
                System.out.println("Arrivée : " + current.getNom()); //arrivée atteinte
            }
        }
    }
    // METHODE n°6 : Simplifier l'affichage des noms de lignes (évite la multiplication de M-M1-M2 à la suite)
    private String simplifierNomLigne(String nomLigne)
    {
        int pos = nomLigne.indexOf("-");
        if (pos == -1) return nomLigne; // pas de M1/M2
        return nomLigne.substring(0, pos); // on prend seulement la rue
    }

}
