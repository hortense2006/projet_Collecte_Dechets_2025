package Controller;
import Model.Profil;
import java.util.HashMap;
import View.ParticulierView;

public class ParticulierController
{
    // ATTRIBUTS
    public HashMap<String, Profil> compte;
    String idPropose;
    String mdpPropose;

    // APPEL DE CLASSES
    ParticulierView view = new ParticulierView();
    Profil p;

    // CONSTRUCTEUR
    public ParticulierController() {}

    // METHODE n°1 : Vérifier l'identifiant
    public boolean checkId(String idPropose)
    {
        if(compte.containsKey(idPropose))
        {
            p = compte.get(idPropose);
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
            view.toString("Id valide");
        }
        mdpPropose = view.afficherMdp();
        if(checkMdp(mdpPropose))
        {
            view.toString("Mdp valide");
        }
    }
}
