package model;

import java.util.*;
// Cette classe s'occupe uniquement des tâches propre à un particulier.
public class ParticulierModel
{
    // ATTRIBUTS
    private Map<String, Profil> compte;
    String nomFichier;
    private final FichiersProfil f;

    // CONSTRUCTEUR
    public ParticulierModel(String nomFichier)
    {
        this.nomFichier = nomFichier;
        this.f = new FichiersProfil(nomFichier);
        this.compte = new HashMap<>();
        f.chargerInfos(); // charge les profils existants
    }

    // GETTER n°1
    public Map<String, Profil> getCompte()
    {
        return compte;
    }


    // METHODE n°1 : Demander une collecte d'encombrants

    public void faireDemandeCollecte(DemandeCollecte demande)
    {
        // ATTRIBUTS
        List<DemandeCollecte> demandeEncombrants = new ArrayList<>();
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
    public Profil inscrire(ProfilInput input)
    {
        String id = genererId();

        Profil p = new Profil(
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
