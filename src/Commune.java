import Exceptions.ExceptionPersonnalisable;

import java.io.IOException;
import java.util.Scanner;

public class Commune implements Utilisateur
{
    String id;
    String mdp;
    Scanner sc = new Scanner(System.in);
    private boolean estConnecte;

    public Commune(boolean estConnecte)
    {
        this.estConnecte = estConnecte;
    }

    @Override
    public void login()
    {
        System.out.println("Saisissez le nom de la commune :");
        String idPropose = sc.nextLine();
        if(idPropose.equals(id))
        {
            System.out.println("Saisissez le mot de passe de la commune:");
            String mdpPropose = sc.nextLine();
            if(mdpPropose.equals(mdp))
            {
                setEstConnecte(true); // La commune est connectée
            }
            else
            {
                throw new ExceptionPersonnalisable("Le mot de passe est invalide");
            }
        }
        else
        {
            throw new ExceptionPersonnalisable("Le nom de la commune est invalide");
        }
    }

    @Override
    public void chargerInfos(String nomFichier) {

    }

    @Override
    public boolean lireInfos(String info) {
        return false;
    }

    @Override
    public void faireDemandeCollecte(String typeUser) {

    }

    @Override
    public void consulterPlanningRamassage(String commune) {

    }

    private void setEstConnecte(boolean estConnecte)
    {
        this.estConnecte = estConnecte;
    }
}
