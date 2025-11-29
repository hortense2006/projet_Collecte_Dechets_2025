package model.map;

import java.util.Map;

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
        String rueNettoyee = nettoyerNomRue(rue);
        Arc arc = plan.getArcParNom(rueNettoyee); // Récupère le meilleur arc correspondant

        if (arc == null)
        {
            System.err.println("Rue inconnue : " + rue);
            return null;
        }

        String idArcOriginal = arc.getIdLigne(); // le nom enregistré dans le fichier texte

        // Limitation des numéros
        if (numero < 0) numero = 0;
        if (numero > arc.getDistance()) numero = arc.getDistance();

        // Créer la station temporaire
        String nomTemp = "M_" + rueNettoyee.replace(" ", "_") + "_" + numero;
        Station maisonTemp = plan.creerStation(nomTemp);
        if (maisonTemp == null)
        {
            System.err.println("Impossible de créer la station " + nomTemp);
            return null;
        }
        plan.getStations().put(nomTemp, maisonTemp);

        // Création des nouveaux arcs (id uniques pour éviter doublons)
        Arc arc1 = new Arc(idArcOriginal + "-M1", arc.getDepart(), maisonTemp, numero);
        Arc arc2 = new Arc(idArcOriginal + "-M2", maisonTemp, arc.getArrivee(), arc.getDistance() - numero);

        //On retire l'arc original
        arc.getDepart().retirerArcSortant(arc);
        String cleARetirer = null;
        for (Map.Entry<String, Arc> entry : plan.getArcs().entrySet())
        {
            if (entry.getValue().equals(arc)) // On compare les arcs
            {
                cleARetirer = entry.getKey();
                break;
            }
        }
        if (cleARetirer != null)
        {
            plan.getArcs().remove(cleARetirer);
        }

        // Ajout des arcs
        arc.getDepart().ajouterArcSortant(arc1);
        maisonTemp.ajouterArcSortant(arc2);

        // On utilise des clés uniques basées sur le nom nettoyé + segments
        String cle1 = rueNettoyee + "_M1";
        String cle2 = rueNettoyee + "_M2";

        plan.getArcs().put(cle1, arc1);
        plan.getArcs().put(cle2, arc2);

        return maisonTemp;
    }
    private String nettoyerNomRue(String rue)
    {
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
        .replace("ô","o");  // Remplacer ô
    }
}
