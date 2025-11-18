package model;
import exceptions.ExceptionPersonnalisable;
import java.util.Scanner;

public class Commune
{
    // ATTRIBUTS
    String id;
    String mdp;
    private boolean estConnecte;


    private void setEstConnecte(boolean estConnecte)
    {
        this.estConnecte = estConnecte;
    }
}
