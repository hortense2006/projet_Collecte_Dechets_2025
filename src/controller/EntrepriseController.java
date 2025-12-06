package controller;
import exceptions.ExceptionPersonnalisable;
import model.EntrepriseModel;
import model.FichierDemandes;
import model.map.*;
import model.particulier.DemandeCollecte;
import view.ParticulierView;

import java.util.*;

public class EntrepriseController
{
    // ATTRIBUTS
    private Plan p;
    private Station courant;  // où se trouve le camion
    private EntrepriseModel em;
    private ParticulierView pv;
    private ParticulierController pc;
    private Maison maison;
    private Queue<DemandeCollecte> liste;

    public EntrepriseController(EntrepriseModel em,Plan p,Maison maison,ParticulierView pv,ParticulierController pc)
    {
        this.p = p;
        this.em = em;
        this.maison = maison;
        this.pv = pv;
        this.pc = pc;
        this.liste = new LinkedList<>();
        this.courant = p.getStation("D"); // dépôt
    }

    //METHODE n°1 : Orchestration complète de la collecte répondant à la liste de demandes
    // Renvoie l'itinéraire optimisé que le camion doit suivre.
    // L'entreprise récupère la demande du particulier, exécute la demande, et renvoie l'itinéraire au camion.
    public Itineraire collecteDemande(Queue<DemandeCollecte> demandes) throws ExceptionPersonnalisable
    {

        if (demandes == null || demandes.isEmpty())
        {
            return null; // aucune demande
        }

        Map<String, List<DemandeCollecte>> demandesParAdresse = new HashMap<>();

        for (DemandeCollecte d : demandes)
        {
            String cle = getCleAdresse(d);
            demandesParAdresse.computeIfAbsent(cle, k -> new ArrayList<>()).add(d);
        }
        List<Arc> arcsTotaux = new ArrayList<>();
        Station depart = courant; // départ du camion (dépôt)
        List<String> adressesRestantes = new ArrayList<>(demandesParAdresse.keySet());

        if (courant == null)
        {
            throw new ExceptionPersonnalisable("Le dépôt du camion n'est pas défini.");
        }

        // Boucle while qui parcourt la liste des demandes
        while (!adressesRestantes.isEmpty())
        {
            // Trouver la demande la plus proche du point de départ actuel
            DemandeCollecte plusProche = null;
            double minDistance = Double.MAX_VALUE;

            for (String cle : adressesRestantes)
            {
                DemandeCollecte d = demandesParAdresse.get(cle).get(0);
                Station stationpossible = maison.creerMaison(d.getRue(), d.getNumero());
                // vérification
                if (stationpossible == null || stationpossible.getNom().equals(depart.getNom()))
                {
                    continue;
                }
                double distanceApprox = depart.distanceVers(d); // calculer distance depuis depart
                if (distanceApprox < minDistance)
                {
                    minDistance = distanceApprox;
                    plusProche = d;
                }
            }

            // Calculer le chemin jusqu'à cette demande
            if (plusProche == null)
            {
                pv.afficherMessage("Fin de la collecte : aucune demande restante ne correspond à une rue du plan.");
                break; // Sort de la boucle while
            }
            Station stationArrivee = maison.creerMaison(plusProche.getRue(), plusProche.getNumero());
            if (stationArrivee == null)
            {
                String clePlusProche = getCleAdresse(plusProche);
                adressesRestantes.remove(clePlusProche);
                continue;
            }
            if (depart.equals(stationArrivee))
            {
                // Le camion est déjà à cette adresse (demande dupliquée).
                // On retire cette demande et on passe à la suivante sans chercher de chemin.
               pv.afficherMessage("Demande ignorée : adresse déjà visitée pour " + plusProche.getRue() + " " + plusProche.getNumero());
                String clePlusProche = getCleAdresse(plusProche);
                adressesRestantes.remove(clePlusProche);
                continue; // Passe à l'itération suivante de la boucle while
            }
            Itineraire chemin = em.bfsPlusCourtChemin(depart.getNom(), stationArrivee.getNom());
            arcsTotaux.addAll(chemin.getChemin()); // ajouter les arcs de ce chemin à l'itinéraire total
            // Marquer la demande comme traitée
            String clePlusProche = getCleAdresse(plusProche);
            adressesRestantes.remove(clePlusProche); // On la supprime de la liste des demandes

            // Mettre à jour le point de départ pour la prochaine boucle
            depart = stationArrivee;
        }
        // A la fin de l'itinéraire, le camion doit retourner au dépôt
        Station stationDepot = p.getStation("D"); // On récupère la station de dépôt
        // Calculer le chemin de la dernière maison visitée ('depart') au dépôt
        Itineraire cheminRetour = em.bfsPlusCourtChemin(depart.getNom(), stationDepot.getNom());
        if (cheminRetour != null)
        {
            arcsTotaux.addAll(cheminRetour.getChemin()); // Ajout des arcs du retour
            depart = stationDepot; // Met à jour 'depart' pour être le dépôt final
        }
        this.courant = depart;
        // Renvoie au Camion le chemin à faire.
        return new Itineraire(courant, depart, arcsTotaux);
    }

    // METHODE n°2 : Regroupe toutes les demandes ayant la même adresse sur une même "clé"
    private String getCleAdresse(DemandeCollecte d)
    {
        return d.getRue().trim().toLowerCase() + "_" + d.getNumero();
    }

    // METHODE n°3
    public Queue<DemandeCollecte> recupListeDemandes(int choixDeVille, FichierDemandes fichierDemandes,DemandeCollecte d)
    {
        if(choixDeVille == 1)
        {
            Queue<DemandeCollecte>  listeDemandes = fichierDemandes.getFileDemandes();
            liste = pc.remplirListeDemandeCollecte(d,listeDemandes);// On remplit la liste de demandes.
        }
        else if(choixDeVille == 2)
        {
            Queue<DemandeCollecte>  listeDemandes = fichierDemandes.getFileDemandes();
            liste = pc.remplirListeDemandeCollecte(d,listeDemandes);// On remplit la liste de demandes.
        }
        return liste;
    }
}
