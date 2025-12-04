package model.particulier;
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
    private Queue<DemandeCollecte> demandesRanville;
    private Queue<DemandeCollecte> demandesBordeaux;



    // CONSTRUCTEUR
    public ParticulierModel(String nomFichier)
    {
        this.nomFichier = nomFichier;
        this.f = new FichiersProfil(nomFichier);
        this.compte = f.getCompte();
        this.demandesRanville = new LinkedList<>();
        this.demandesBordeaux = new LinkedList<>();

        f.chargerDepuisFichier(); // charge les profils existants
    }

    // GETTER n°2
    public Queue<DemandeCollecte> getDemande(String nomVille)
    {
        if(nomVille.equals("Ranville")) return demandesRanville;
        else if(nomVille.equals("Bordeaux")) return demandesBordeaux;
        return null;
    }


    // METHODE n°1 : Demander une collecte d'encombrants
    public DemandeCollecte faireDemandeCollecte(String nomVille,String idUtilisateur,TypeEncombrant encombrant,int quantite,LocalDate date,String rue,Double numero)
    {
        // On remplit une nouvelle demande, et on l'ajoutes à une liste de demandes
        DemandeCollecte nouvelleDemande = new DemandeCollecte(nomVille,idUtilisateur,encombrant, quantite,date,rue,numero);
        if(nomVille.equals("Ranville")) demandesRanville.add(nouvelleDemande);
        else if(nomVille.equals("Bordeaux")) demandesBordeaux.add(nouvelleDemande);
        return nouvelleDemande;
    }

    // METHODE n°2: Remplir le fichier texte
    public Profil inscrire(ProfilInput input)
    {
        String id = UUID.randomUUID().toString(); // Chaque id est différent

        Profil p = new Profil(
                input.nomVille(),
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

    // SETTER n°1
    public Queue<DemandeCollecte> setDemande(String nomVille,Queue<DemandeCollecte> fileDemandes)
    {
        if(nomVille.equals("Ranville")) this.demandesRanville = fileDemandes;
        else if(nomVille.equals("Bordeaux")) this.demandesBordeaux = fileDemandes;
        return fileDemandes;
    }
}
