package model.map;

public class Maison extends Station
{
    // ATTRIBUTS
    private Plan plan;
    // CONSTRUCTEUR
    public Maison(Plan plan,String nom)
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
            System.err.println("Rue inconnue : " + rue);
            return null;
        }

        // Limiter le numéro à l'intérieur de l'arc
        if (numero < 0) numero = 0;
        if (numero > arc.getDistance()) numero = arc.getDistance();

        // Créer la station temporaire
        String nomTemp = "M_" + rue + "_" + numero;
        Station maisonTemp = plan.creerStation(nomTemp);
        if (maisonTemp == null) {
            System.err.println("Impossible de créer la station " + nomTemp);
            return null;
        }

        // Créer deux arcs pour la maison sur l'arc existant
        Arc arc1 = new Arc(arc.getDepart(), maisonTemp, numero);
        Arc arc2 = new Arc(maisonTemp, arc.getArrivee(), arc.getDistance() - numero);

        // Ajouter les arcs sortants
        arc.getDepart().ajouterArcSortant(arc1);
        maisonTemp.ajouterArcSortant(arc2);

        return maisonTemp;
    }
}
