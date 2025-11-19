package model;
import java.time.LocalDate;
import java.util.*;

// Cette classe s'occupe uniquement des tâches propre à un particulier.
public class ParticulierModel
{
    // ATTRIBUTS
    private Map<String, Profil> compte;
    String nomFichier;
    private final FichiersProfil f;
    private Queue<DemandeCollecte> demande;


    // CONSTRUCTEUR
    public ParticulierModel(String nomFichier)
    {
        this.nomFichier = nomFichier;
        this.f = new FichiersProfil(nomFichier);
        this.compte = new HashMap<>();
        this.demande = new LinkedList<>();
        f.chargerInfos(); // charge les profils existants
    }

    // GETTER n°1
    public Map<String, Profil> getCompte() {return compte;}


    // METHODE n°1 : Demander une collecte d'encombrants

    public DemandeCollecte faireDemandeCollecte(String idUtilisateur, DemandeCollecte.TypeEncombrant typeEncombrant, int quantite, LocalDate dateDemande)
    {
        // On remplit une nouvelle demande, et on l'ajoutes à une liste de demandes
        DemandeCollecte nouvelleDemande = new DemandeCollecte(idUtilisateur, typeEncombrant, quantite, dateDemande);
        demande.add(nouvelleDemande);
        return nouvelleDemande;
    }

    // METHODE n°4 : Consulter le planning de collecte (ramassage devant les maisons)

    public void consulterPlanningRamassage(String commune) {

    }

    // METHODE n°5: Remplir le fichier texte
    public model.Profil inscrire(ProfilInput input)
    {
        String id = UUID.randomUUID().toString(); // Chaque id est différent

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
