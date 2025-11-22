package model.particulier;
import exceptions.ExceptionPersonnalisable;
import model.map.Plan;
import java.time.LocalDate;
import java.util.*;

// Cette classe s'occupe uniquement des tâches propre à un particulier.
public class ParticulierModel
{
    // ATTRIBUTS
    private Map<String, Profil> compte;
    Plan plan = new Plan();
    String nomFichier;
    private final FichiersProfil f;
    private Queue<DemandeCollecte> demandes;


    // CONSTRUCTEUR
    public ParticulierModel(String nomFichier)
    {
        this.nomFichier = nomFichier;
        this.f = new FichiersProfil(nomFichier);
        this.compte = f.getCompte();
        this.demandes = new LinkedList<>();
        f.chargerInfos(); // charge les profils existants
    }

    // GETTER n°2
    public Queue<DemandeCollecte> getDemande() {return demandes;}


    // METHODE n°1 : Demander une collecte d'encombrants
    public DemandeCollecte faireDemandeCollecte(String idUtilisateur,TypeEncombrant encombrant,int quantite,LocalDate date,String rue,Double numero)
    {
        // On remplit une nouvelle demande, et on l'ajoutes à une liste de demandes
        DemandeCollecte nouvelleDemande = new DemandeCollecte(idUtilisateur,encombrant, quantite,date,rue,numero);
        demandes.add(nouvelleDemande);
        return nouvelleDemande;
    }


    // METHODE n°2 : Consulter le planning de collecte (ramassage devant les maisons)
    public void consulterPlanningRamassage(String commune) {}

    // METHODE n°5: Remplir le fichier texte
    public Profil inscrire(ProfilInput input)
    {
        String id = UUID.randomUUID().toString(); // Chaque id est différent

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
