package Model;

import View.ParticulierView;

import java.util.*;
// Cette classe s'occupe uniquement des tâches propre à un particulier.
public class ParticulierModel
{
    // ATTRIBUTS
    private String typeUser; // "collectivité", "entreprise", "particulier"
    private boolean estConnecte;
    private Map<String, Profil> compte;
    String nomFichier;

    // APPEL DE CLASSES
    FichiersProfil f = new FichiersProfil(nomFichier );
    ParticulierView view =  new ParticulierView();

    // CONSTRUCTEUR
    public ParticulierModel()
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
    public Profil inscrire(view.ProfilInput input)
    {
        view.afficherRegister();
        Profil p = new Profil(
                input.prenom(),
                input.nom(),
                input.numero(),
                input.rue(),
                genererId(),
                input.mdp()
        );

        f.sauvegarderProfil(p);
        compte.put(id, p);

        return p;
    }


}
