package controller;
import model.Commune;
import model.EntrepriseModel;
import model.map.Itineraire;
import model.particulier.*;
import java.time.LocalDate;
import view.ParticulierView;
import static model.particulier.TypeEncombrant.*;

// Cette classe s'occupe de la coordination
public class ParticulierController
{
    // ATTRIBUTS
    private Profil utilisateurActuel;
    private final ParticulierModel model;
    private final ParticulierView view;
    private final EntrepriseModel em;
    private DemandeCollecte demande;
    Commune commune;
    String idPropose;
    String mdpPropose;

    // CONSTRUCTEUR
    public ParticulierController(ParticulierModel model, ParticulierView view, EntrepriseModel em)
    {
        this.model = model;
        this.view = view;
        this.em = em;
        this.utilisateurActuel = null;
    }

    // METHODE n°1 : Vérifier l'identifiant
    public boolean checkId(String idPropose)
    {
        if(model.getCompte().containsKey(idPropose))
        {
            Profil p = model.getCompte().get(idPropose);
            if (p != null)
            {
                this.utilisateurActuel = p;
                return true;
            }
        }
        else
        {
            return false;
        }
        return false;
    }

    // METHODE n°2 : Vérifier le mot de passe
    public boolean checkMdp(String mdpPropose)
    {
        if(utilisateurActuel.getMdp().equals(mdpPropose))
        {
            utilisateurActuel.setEstConnecte(true);
            return true;
        }
        else
        {
            return false;
        }
    }

    // METHODE n°3 : CONNEXION (METHODE LOGIQUE)
    public void signin()
    {
        idPropose = view.afficherId();
        if(checkId(idPropose))
        {
            view.afficherMessage("id valide");
        }
        mdpPropose = view.afficherMdp();
        if(checkMdp(mdpPropose))
        {
            view.afficherMessage("Mdp valide");
            view.afficherInfos();
        }
    }

    // METHODE n°4 : SE CONNECTER
    public void login()
    {
        String choix = view.ActionLogin();

        switch(choix.trim().toLowerCase())
        {
            case "oui":
            {
                signin();
                break;
            }
            case "non":
            {
                ProfilInput input = view.afficherRegister();
                this.utilisateurActuel = model.inscrire(input);
                view.afficherMessage("Inscription réussie !");
                break;
            }
            default:
            {
                view.afficherMessage("Choix invalide.");
            }
        }
    }
        // METHODE n°5 : DEMANDER COLLECTE D'ENCOMBRANTS
    public DemandeCollecte DemandeCollecte()
    {
        int quantite = 0;
        LocalDate dateDemande = LocalDate.now(); // Date de la demande

        TypeEncombrant choix = view.affichageDemandeCollecte(); // On demande le type d'encombrants
        switch (choix)
        {
            case MEUBLE:
            {
                quantite = view.affichageQuantiteEncombrants(MEUBLE);
                demande = model.faireDemandeCollecte(utilisateurActuel.getId(),MEUBLE,quantite,dateDemande,utilisateurActuel.getRue(),utilisateurActuel.getNumero());
                view.afficherDemande(demande); // On affiche la demande pour vérification.
                break;
            }
            case ELECTROMENAGER:
            {
                quantite = view.affichageQuantiteEncombrants(ELECTROMENAGER);
                demande = model.faireDemandeCollecte(utilisateurActuel.getId(),ELECTROMENAGER,quantite,dateDemande,utilisateurActuel.getRue(),utilisateurActuel.getNumero());
                view.afficherDemande(demande); // On affiche la demande pour vérification.
                break;
            }
            case BOIS:
            {
                quantite = view.affichageQuantiteEncombrants(BOIS);
                demande = model.faireDemandeCollecte(utilisateurActuel.getId(),BOIS,quantite,dateDemande,utilisateurActuel.getRue(),utilisateurActuel.getNumero());
                view.afficherDemande(demande); // On affiche la demande pour vérification.
                break;
            }
            case CANAPE:
            {
                quantite = view.affichageQuantiteEncombrants(CANAPE);
                demande = model.faireDemandeCollecte(utilisateurActuel.getId(),CANAPE,quantite,dateDemande,utilisateurActuel.getRue(),utilisateurActuel.getNumero());
                view.afficherDemande(demande); // On affiche la demande pour vérification.
                break;
            }
            case AUTRE:
            {
                quantite = view.affichageQuantiteEncombrants(AUTRE);
                demande = model.faireDemandeCollecte(utilisateurActuel.getId(),AUTRE,quantite,dateDemande,utilisateurActuel.getRue(),utilisateurActuel.getNumero());
                view.afficherDemande(demande); // On affiche la demande pour vérification.
                break;
            }
            default:
            {
                view.afficherMessage("Choix invalide, valeur par défaut : Autre");
                quantite = view.affichageQuantiteEncombrants(AUTRE);
                demande = model.faireDemandeCollecte(utilisateurActuel.getId(),AUTRE,quantite,dateDemande,utilisateurActuel.getRue(),utilisateurActuel.getNumero());
                break;
            }
        }
        return demande;
    }
}
