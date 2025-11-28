package model.map;

public class Maison extends Station
{
    // ATTRIBUTS
    private Plan plan;
    private String nom;
    // CONSTRUCTEUR
    public Maison(Plan plan)
    {
        super(nom);
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

        // Définir la position sur l’arc selon numérotation américaine
        double numeroDebut = 0;               // début de la rue
        double numeroFin = arc.getDistance();  // longueur totale de la rue
        // Découper l’arc en deux arcs : début -> maison, maison -> fin
        Arc arc1 = new Arc(arc.getDepart(), maisonTemp, numero - numeroDebut);
        Arc arc2 = new Arc(maisonTemp, arc.getArrivee(), numeroFin - numero);

        // Mettre à jour arcs sortants
        arc.getDepart().getArcsSortants().remove(arc);
        arc.getDepart().ajouterArcSortant(arc1);
        maisonTemp.ajouterArcSortant(arc2);

        return maisonTemp;
    }
}
