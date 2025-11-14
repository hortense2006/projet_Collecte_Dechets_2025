package Model;

import Exceptions.ExceptionPersonnalisable;
import java.io.*;
import java.util.*;

public class Particuliers
{
    // ATTRIBUTS
    private String typeUser; // "collectivité", "entreprise", "particulier"
    private boolean estConnecte;
    private Map<String, Profil> compte;

    // CONSTRUCTEUR
    public Particuliers()
    {
        this.typeUser = typeUser;
        this.estConnecte = false;
        this.compte = new HashMap<>();
    }


    // METHODE n°4 : Demander une collecte d'encombrants

    public void faireDemandeCollecte(String typeUser)
    {
        System.out.println("Voulez-vous demander une collecte d'encombrants");

    }
    // METHODE n°5 : Consulter le planning de collecte (ramassage devant les maisons)

    public void consulterPlanningRamassage(String commune) {

    }
}
