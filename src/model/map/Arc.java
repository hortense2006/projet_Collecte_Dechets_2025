package model.map;

import model.particulier.DemandeCollecte;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Arc {

    private final String idLigne;
    private final Station depart;
    private final Station arrivee;
    private double distance;

    private List<DemandeCollecte> dechetsSurLaVoie;

    public Arc(String idLigne, Station depart, Station arrivee, double duree) {
        this.idLigne = idLigne;
        this.depart = depart;
        this.arrivee = arrivee;
        this.distance = duree;
        this.dechetsSurLaVoie = new ArrayList<>();
    }

    /*public void ajouterDechet(DemandeCollecte dechet) {
        this.dechetsSurLaVoie.add(dechet);//On ajoute le déchet à la liste de la rue
        this.dechetsSurLaVoie.sort(Comparator.comparingDouble(DemandeCollecte::getPosition));
    }*/

    public String getIdLigne() {
        return idLigne;
    }
    public Station getDepart() {
        return depart;
    }
    public Station getArrivee() {
        return arrivee;
    }
    public double getDistance() {
        return distance;
    }

    public List<Arc> recupererTousLesArcs(Plan plan) {
        List<Arc> tous = new ArrayList<>();
        for (Station s : plan.getStations().values()) {
            tous.addAll(s.getArcsSortants());
        }
        return tous;
    }

    public boolean contientArc(List<Arc> liste, Arc arcCherche) {
        for (Arc a : liste) {
            // Vérifie A->B
            if (a == arcCherche) return true;
            // Vérifie B->A (le retour équivalent)
            if (a.getDepart() == arcCherche.getArrivee() && a.getArrivee() == arcCherche.getDepart()) return true;
        }
        return false;
    }

    public void retirerArcDeLaListe(List<Arc> liste, Arc arcASupprimer) {
        // On retire l'arc et son inverse
        liste.removeIf(a ->
                (a.getDepart() == arcASupprimer.getDepart() && a.getArrivee() == arcASupprimer.getArrivee()) ||
                        (a.getDepart() == arcASupprimer.getArrivee() && a.getArrivee() == arcASupprimer.getDepart())
        );
    }
}
