package controller;
import model.*;
import model.DemandeCollecte.*;

import java.time.LocalDate;
import java.util.HashMap;
import model.ProfilInput;
import model.Profil;
import view.ParticulierView;

import static model.DemandeCollecte.TypeEncombrant.*;

// Cette classe s'occupe de la coordination
public class ParticulierController
{
    // ATTRIBUTS
    private final ParticulierModel model;
    private final ParticulierView view;
    public HashMap<String, Profil> compte;
    String idPropose;
    String mdpPropose;
    Profil p;

    // CONSTRUCTEUR
    public ParticulierController(ParticulierModel model, ParticulierView view)
    {
        this.model = model;
        this.view = view;
    }

    // METHODE n°1 : Vérifier l'identifiant
    public boolean checkId(String idPropose)
    {
        if(model.getCompte().containsKey(idPropose))
        {
            p = model.getCompte().get(idPropose);
            return true;
        }
        else
        {
            return false;
        }
    }

    // METHODE n°2 : Vérifier le mot de passe
    public boolean checkMdp(String mdpPropose)
    {
        if(p.getMdp().equals(mdpPropose))
        {
            p.setEstConnecte(true);
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
                model.inscrire(input);
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
    public void DemandeCollecte()
    {
        int quantite = 0;
        LocalDate dateDemande = LocalDate.now();

        TypeEncombrant choix = view.affichageDemandeCollecte(); // On demande le type d'encombrants
        switch (choix)
        {
            case MEUBLE:
            {
                quantite = view.affichageQuantiteEncombrants(MEUBLE);
                model.faireDemandeCollecte(idPropose,MEUBLE,quantite,dateDemande);
                break;
            }
            case ELECTROMENAGER:
            {
                quantite = view.affichageQuantiteEncombrants(ELECTROMENAGER);
                model.faireDemandeCollecte(p.getId(),ELECTROMENAGER,quantite,dateDemande);
                break;
            }
            case BOIS:
            {
                quantite = view.affichageQuantiteEncombrants(BOIS);
                model.faireDemandeCollecte(p.getId(),BOIS,quantite,dateDemande);
                break;
            }
            case CANAPE:
            {
                quantite = view.affichageQuantiteEncombrants(CANAPE);
                model.faireDemandeCollecte(p.getId(),CANAPE,quantite,dateDemande);
                break;
            }
            case AUTRE:
            {
                quantite = view.affichageQuantiteEncombrants(AUTRE);
                model.faireDemandeCollecte(p.getId(),AUTRE,quantite,dateDemande);
                break;
            }
            default:
            {
                view.afficherMessage("Choix invalide, valeur par défaut : Autre");
                quantite = view.affichageQuantiteEncombrants(AUTRE);
                model.faireDemandeCollecte(p.getId(),AUTRE,quantite,dateDemande);
                break;
            }
        }
    }
}
