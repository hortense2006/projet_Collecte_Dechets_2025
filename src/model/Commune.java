package model;
import exceptions.ExceptionPersonnalisable;
import model.map.*;
import java.util.LinkedList;
import java.util.Scanner;

public class Commune
{
    // ATTRIBUTS
    private final PointCollecte pc;
    String id;
    String mdp;
    private boolean estConnecte;

    // CONSTRUCTEUR
    public Commune() {}

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
    }
    private void setEstConnecte(boolean estConnecte)
    {
        this.estConnecte = estConnecte;
    }
}
