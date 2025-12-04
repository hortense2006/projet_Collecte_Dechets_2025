package controller;
import model.EncombrantModel;
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
    private EncombrantModel encombrantModel;
    String idPropose;
    String mdpPropose;

    // CONSTRUCTEUR
    public ParticulierController(ParticulierModel model, ParticulierView view,FichiersProfil fichiers)
    {
        this.fichiers = fichiers;
        this.model = model;
        this.view = view;
        this.utilisateurActuel = null;
        this.encombrantModel = new EncombrantModel();
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
        // Blindage de l'identifiant
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
        // Blindage du mot de passe
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
    }


    // METHODE n°4 : SE CONNECTER
    public void login()
    {
        // Blindage de la connexion de l'utilisateur
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
                    view.afficherInfos(this.utilisateurActuel);
                    break;
                }
                case "non":
                {
                    Choix = true;
                    ProfilInput input = view.afficherRegister();
                    this.utilisateurActuel = model.inscrire(input);
                    view.afficherInfos(this.utilisateurActuel);
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

        // Blindage de l'utilisateur
        if (utilisateurActuel == null)
        {
            view.afficherMessage("Erreur : aucun utilisateur connecté.");
            return null;
        }
        // Blindage du choix du type d'encombrants fait dans la view
        TypeEncombrant choix = view.affichageDemandeCollecte(); // On demande le type d'encombrants
        boolean choixValide = false;
        // Blindage de la quantite d'encombrants
        while(!choixValide)
        {
            quantite = view.affichageQuantiteEncombrants(choix); // On demande la quantite d'encombrants.
            if(!encombrantModel.quantiteValide(choix,quantite))// Vérifie si la quantite entrée correspond au type d'encombrants.
            {
                view.afficherMessage("Quantite invalide.");
                view.messageErreur(choix);
            }
            else
            {
                choixValide = true;
            }
        }
        demande = model.faireDemandeCollecte(utilisateurActuel.getId(),choix,quantite,dateDemande,utilisateurActuel.getRue(),utilisateurActuel.getNumero());
        view.afficherDemande(demande); // On affiche la demande pour vérification.
        view.afficherMessage("Votre demande a été enregistrée.");
        return demande;
    }
    // METHODE n°6 : Remplir liste de demandes
    public Queue<DemandeCollecte> remplirListeDemandeCollecte(DemandeCollecte premiere,Queue<DemandeCollecte> listeDemande)
    {
        listeDemande.add(premiere);
        boolean continuer = true;

        while (continuer && listeDemande.size() < MAX_DEMANDES)
        {
            String rep = view.demander("Voulez-vous faire une autre demande ? (oui/non)");
            if (!rep.equalsIgnoreCase("oui"))
            {
                continuer = false;
            }
            else
            {
                DemandeCollecte demande = DemandeCollecteE();
                if (demande != null)
                {
                    listeDemande.add(demande);
                }
                if (listeDemande.size() >= MAX_DEMANDES)
                {
                    view.afficherMessage("Nombre maximum de demandes atteint.");
                    break;
                }
            }
        }
        return listeDemande;
    }

}
