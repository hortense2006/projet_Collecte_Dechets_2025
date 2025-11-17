package model;

import java.util.*;
// Cette classe s'occupe uniquement des tâches propre à un particulier.
public class ParticulierModel
{
    // ATTRIBUTS
    private Map<String, model.Profil> compte;
    String nomFichier;
    private final model.FichiersProfil f;

    // CONSTRUCTEUR
    public ParticulierModel(String nomFichier)
    {
        this.nomFichier = nomFichier;
        this.f = new model.FichiersProfil(nomFichier);
        this.compte = new HashMap<>();
        f.chargerInfos(); // charge les profils existants
    }

    // GETTER n°1
    public Map<String, model.Profil> getCompte()
    {
        return compte;
    }


    // METHODE n°1 : Demander une collecte d'encombrants

    public void faireDemandeCollecte(model.DemandeCollecte demande)
    {
        // ATTRIBUTS
        List<model.DemandeCollecte> demandeEncombrants = new ArrayList<>();
        demandeEncombrants.add(demande);
    }
    // METHODE n°2 : Consulter le planning de collecte (ramassage devant les maisons)

    public void consulterPlanningRamassage(String commune) {

    }

    // METHODE n°3 : Générer aléatoirement un identifiant
    public String genererId()
    {
        String id = UUID.randomUUID().toString(); // Chaque id est différent
        return id;
    }

    // METHODE n°4: Remplir le fichier texte
    public model.Profil inscrire(model.ProfilInput input)
    {
        String id = genererId();

        model.Profil p = new model.Profil(
                input.prenom(),
                input.nom(),
                input.numero(),
                input.rue(),
                id,
                input.mdp()
        );

        f.sauvegarderProfil(p);
        compte.put(id, p);

        return p;
    }
}
