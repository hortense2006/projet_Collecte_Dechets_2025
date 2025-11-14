package Model;

public interface Utilisateur
{
    // METHODE n°1 : connexion de la commune/ du particulier
    void login();

    // METHODE n°2 : Enregistrer les infos de la commune/du particulier
    void chargerInfos(String nomFichier);

    // METHODE n°3 : Lire les infos du fichier texte
    boolean lireInfos(String info);

    // METHODE n°4
    void faireDemandeCollecte(String typeUser);

    // METHODE n°5
    void consulterPlanningRamassage(String commune);
}
