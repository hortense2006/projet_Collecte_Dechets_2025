package Model;

import Exceptions.ExceptionPersonnalisable;
import java.io.*;
import java.util.*;
// Cette classe s'occupe uniquement des tâches propre à un particulier.
public class Particuliers
{
    // ATTRIBUTS
    private String typeUser; // "collectivité", "entreprise", "particulier"
    private boolean estConnecte;
    private Map<String, Profil> compte;
    String nomFichier;

    // APPEL DE CLASSES
    FichiersProfil f = new FichiersProfil(nomFichier );

    // CONSTRUCTEUR
    public Particuliers()
    {
        this.typeUser = typeUser;
        this.estConnecte = false;
    }

    // METHODE n°1 : Demander une collecte d'encombrants

    public void faireDemandeCollecte(String typeUser)
    {}
    // METHODE n°2 : Consulter le planning de collecte (ramassage devant les maisons)

    public void consulterPlanningRamassage(String commune) {

    }

    // METHODE n°3 : Générer aléatoirement un identifiant
    public String genererId()
    {
        String id = UUID.randomUUID().toString(); // Chaque id est différent
        return id;
    }
    public Profil inscrire(Profil input)
    {
        Profil p = new Profil(
                input.getPrenom(),
                input.getNom(),
                input.getNumero(),
                input.getRue(),
                input.getId(),
                input.getMdp()
        );

        f.sauvegarderProfil(p);
        compte.put(input.getId(), p);
        return p;
    }

}
