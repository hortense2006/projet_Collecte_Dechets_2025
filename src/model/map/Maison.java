package model.map;

public class Maison extends Station
{
    // ATTRIBUTS
    private Plan plan;
    // CONSTRUCTEUR
    public Maison(Plan plan)
    {
        this.plan = plan;
    }

    // METHODE n°1 : Création d'une maison (station temporaire)
    public Station creerMaison(String rue, double numero)
    {
        Arc arc = plan.getArcParNom(rue);
        if (arc == null)
        {
            return null; // rue inconnue
        }

        // Créer la station temporaire
        String nomTemp = "M" + rue + "_" + numero;

        // Ajouter la station au graphe
        Station maisonTemp = plan.creerStation(nomTemp);

        // Découper l’arc en deux arcs : début -> maison, maison -> fin
        Arc arc1 = new Arc(arc.getDepart(), maisonTemp, numero - arc.getNumeroDebut());
        Arc arc2 = new Arc(maisonTemp, arc.getArrivee(), arc.getNumeroFin() - numero);

        // Supprimer l’ancien arc et ajouter les deux nouveaux
        supprimerArc(arc);
        ajouterArc(arc1);
        ajouterArc(arc2);

        return maisonTemp;
    }


}
