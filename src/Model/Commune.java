package Model;

import Exceptions.ExceptionPersonnalisable;

import java.util.Scanner;

public class Commune
{
    // ATTRIBUTS
    String id;
    String mdp;
    private boolean estConnecte;

    // APPEL DE CLASSES
    Scanner sc = new Scanner(System.in);

    // CONSTRUCTEUR
    public Commune(boolean estConnecte)
    {
        this.estConnecte = estConnecte;
    }

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


    public void chargerInfos(String nomFichier) {

    }


    public void faireDemandeCollecte(String typeUser) {

    }


    public void consulterPlanningRamassage(String commune) {

    }

    private void setEstConnecte(boolean estConnecte)
    {
        this.estConnecte = estConnecte;
    }
}
