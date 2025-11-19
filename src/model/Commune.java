package model;
import exceptions.ExceptionPersonnalisable;
import model.map.*;
import model.particulier.DemandeCollecte;

import java.io.IOException;
import java.util.List;

public class Commune
{
    // ATTRIBUTS
    String id;
    String mdp;
    private boolean estConnecte;

    // CONSTRUCTEUR
    public Commune() {}

    // SETTER n°1
    private void setEstConnecte(boolean estConnecte)
    {
        this.estConnecte = estConnecte;
    }
    //METHODE n°1 : Retirer une demande après exécution
    public void retirerDemande(DemandeCollecte demande) throws ExceptionPersonnalisable
    {
        //DemandeCollecte prochaineDemande = demande.poll(); // retire en FIFO
    }
    //METHODE n°2 : Exécuter la demande
    // Deux cas possibles : exécution immédiate ou au bout de 5 requêtes
    public void executerDemande(DemandeCollecte demande)
    {
        //La demande est reçue en paramètre
        //Analyse de la demande
        // Méthode Welsh-Powell
        repartirCargaison();
    }

    // Méthode n°3 : Répartir la cargaison
    public int repartirCargaison()
    {
        ;
        List<Produit> couleur;
        Produit color;
        int nbzones = 0;
        // Lire la liste produits
        while(!produit.isEmpty()) // On lit la case de la liste
        {
            for (Produit p : produit)
            {
                // Si le produit n'a pas encore de zone
                if (p.getZone() == 0)
                {
                    nbzones++;
                    p.setZone(nbzones); // on lui attribue une nouvelle zone
                }
            }
        }
        return nbzones;
    }
}
