public abstract class Utilisateurs
{
    // CONSTRUCTEUR
    public Utilisateurs(){}

    // METHODE ABSTRAITE n°1 : connexion de la commune/ du particulier
    public abstract void login();
    // METHODE ABSTRAITE n°2 : Enregistrer les infos de la commune/du particulier
    public abstract void chargerInfos();
    // METHODE ABSTRAITE n°3 : Lire les infos du fichier texte
    public abstract void lireInfos();
}
