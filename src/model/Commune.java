package model;
import exceptions.ExceptionPersonnalisable;
import java.util.Scanner;

public class Commune
{
    // ATTRIBUTS
    String id;
    String mdp;
    private boolean estConnecte;

    //METHODE n°3 : Exécuter la demande
    // Deux cas possibles : exécution immédiate ou au bout de 5 requêtes
    public void executerDemande() {}
    //METHODE PEUT ETRE PAS NECESSAIRE

    private void setEstConnecte(boolean estConnecte)
    {
        this.estConnecte = estConnecte;
    }
}
