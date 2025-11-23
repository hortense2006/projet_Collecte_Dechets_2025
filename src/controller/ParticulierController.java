package controller;
import model.particulier.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Queue;

import view.ParticulierView;
import static model.particulier.TypeEncombrant.*;

// Cette classe s'occupe de la coordination
public class ParticulierController
{
    // ATTRIBUTS
    final int MAX_DEMANDES= 10;
    private Profil utilisateurActuel;
    private final ParticulierModel model;
    private final ParticulierView view;
    private DemandeCollecte demande;
    private FichiersProfil fichiers;
    String idPropose;
    String mdpPropose;

    // CONSTRUCTEUR
    public ParticulierController(ParticulierModel model, ParticulierView view,FichiersProfil fichiers)
    {
        this.fichiers = fichiers;
        this.model = model;
        this.view = view;
        this.utilisateurActuel = null;
    }

    // METHODE n°1 : Vérifier l'identifiant
    public boolean checkId(String idPropose)
    {
        if(fichiers.getCompte().containsKey(idPropose))
        {
            Profil p = fichiers.getCompte().get(idPropose);
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
        while(!checkId(idPropose))
        {
            idPropose = view.afficherId();
            if(!checkId(idPropose))
            {
                view.afficherMessage("Identifiant introuvable !");
            }
            else
            {
                view.afficherMessage("id valide");
            }
        }
        while (!checkMdp(mdpPropose))
        {
            mdpPropose = view.afficherMdp();
            if(!checkMdp(mdpPropose))
            {
                view.afficherMessage("Mot de passe incorrect !");
            }
            else
            {
                view.afficherMessage("Mdp valide");
            }
        }
        view.afficherInfos();
    }


    // METHODE n°4 : SE CONNECTER
    public void login()
    {
        boolean Choix = false;
        while(!Choix)
        {
            String choix = view.ActionLogin();
            switch(choix.trim().toLowerCase())
            {
                case "oui":
                {
                    Choix = true;
                    signin();
                    break;
                }
                case "non":
                {
                    Choix = true;
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
    }
        // METHODE n°5 : DEMANDER COLLECTE D'ENCOMBRANTS
    public DemandeCollecte DemandeCollecteE()
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
    // METHODE n°6 : Remplir liste de demandes
    public Queue<DemandeCollecte> remplirListeDemandeCollecte()
    {
        Queue<DemandeCollecte> listeDemande = new LinkedList<>();
        int nbDemandes =0;
        while (nbDemandes !=MAX_DEMANDES)
        {
            listeDemande.add(DemandeCollecteE());
            nbDemandes++;
        }
        return listeDemande;
    }
}
