package Controller;
import Model.ParticulierModel;
import Model.Profil;
import java.util.HashMap;
import View.ParticulierView;
// Cette classe s'occupe de la coordination
public class ParticulierController
{
    private final ParticulierModel model;
    private final ParticulierView view;
    // ATTRIBUTS
    public HashMap<String, Profil> compte;
    String idPropose;
    String mdpPropose;

    // APPEL DE CLASSES
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
            view.afficherMessage("id valide");
        }
        mdpPropose = view.afficherMdp();
        if(checkMdp(mdpPropose))
        {
            view.afficherMessage("Mdp valide");
        }
    }
}
