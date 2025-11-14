public interface Utilisateur
{
    // METHODE n°1 : connexion de la commune/ du particulier
    void login(String typeUser);
    // METHODE n°2 : Enregistrer les infos de la commune/du particulier
    void chargerInfos();
    // METHODE n°3 : Lire les infos du fichier texte
    boolean lireInfos();
    void faireDemandeCollecte(String typeUser);
    void consulterPlanningRamassage(String commune);
}
