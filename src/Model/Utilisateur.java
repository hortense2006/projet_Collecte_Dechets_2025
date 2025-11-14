package Model;

public interface Utilisateur
{
    // METHODE n°1 : connexion de la commune/ du particulier
    void login();

    // METHODE n°2 : Enregistrer les infos de la commune/du particulier
    void chargerInfos(String nomFichier);

    // METHODE n°3
    void faireDemandeCollecte(String typeUser);

    // METHODE n°4
    void consulterPlanningRamassage(String commune);
}
