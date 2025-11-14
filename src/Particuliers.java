import Exceptions.ExceptionPersonnalisable;
import java.io.*;
import java.util.*;

public class Particuliers implements Utilisateur
{
    // ATTRIBUTS
    private String typeUser; // "collectivité", "entreprise", "particulier"
    private String id;
    private String mdp;
    public String nomFichier;
    private boolean estConnecte;
    private Map<String,Profil> compte;

    Scanner sc = new Scanner(System.in);

    // CONSTRUCTEUR
    public Particuliers(String typeUser)
    {
        this.typeUser = typeUser;
        this.estConnecte = false;
        this.compte = new HashMap<>();
    }
    // SETTER n°1
    public boolean setEstConnecte(boolean estConnecte)
    {
        this.estConnecte = estConnecte;
        return estConnecte;
    }

    // METHODE n°1 : Login de l'utilisateur
    @Override
    public void login()
    {
        String choix;
        System.out.println("Etes-vous déjà inscrit ?");
        choix = sc.nextLine();
        switch(choix)
        {
            case "oui":
            { // Connexion (lecture du fichier texte)
                signin();
                break;
            }
            case "non":
            { // Se créer un compte (écriture d'un fichier)
                signup();
                break;
            }
            default:{throw new ExceptionPersonnalisable("Choix invalide.");}
        }
    }

    // METHODE n°1 : CONNEXION
    public void signin()
    {
        System.out.println("Saisissez votre identifiant:");
        String idPropose = sc.nextLine(); // On récupère l'identifiant saisi
        if(compte.containsKey(idPropose)) // Lecture du fichier : l'identifiant existes
        {
            System.out.println("Saisissez votre mot de passe:");
            String mdpPropose = sc.nextLine(); // On récupère le mdp saisi
            if(existenceMdp(idPropose,mdpPropose))// On vérifie son existence
            {
                setEstConnecte(true);
            }
            else
            {
                System.out.println("Ce mot de passe est invalide.");
                signin();
            }
        }
        else
        {
            System.out.println("Cet identifiant n'existes pas.");
            signin();
        }
    }

    // METHODE n°2 : INSCRIPTION
    public void signup()
    {
        System.out.println("Saisissez votre prenom");
        String prenom = sc.nextLine();
        System.out.println("Saisissez votre nom de famille:");
        String nom = sc.nextLine();
        System.out.println("Saisissez votre numero d'habitation:");
        int numero = sc.nextInt();
        System.out.println("Saisissez votre numero de rue:");
        String rue = sc.nextLine();
        String id = UUID.randomUUID().toString(); // Chaque id est différent
        System.out.println("Voici votre identifiant:" + this.id);
        System.out.println("Saisissez un mot de passe:");
        String mdp = sc.nextLine();
        // On crée un profil
        Profil p = new Profil(prenom,nom,numero,rue,id,mdp);
        compte.put(id,p);
        // On enregistre les infos dans le fichier texte
        chargerInfos(nomFichier);
    }
    // METHODE n°3
    @Override
    public void chargerInfos(String nomFichier)
    {
        // On enregistre les Hashmap personne, adresse et compte dans le fichier texte
        try (BufferedReader br = new BufferedReader(new FileReader(nomFichier)))
        {
            String ligne;
            while ((ligne = br.readLine()) != null)
            {
                String[] parties = ligne.split(";"); //sépare chaque ligne en 4 morceaux
                String prenom = parties[0].trim();
                String nom = parties[1].trim();
                String nomArrivee = parties[2].trim();
            }
        }
        catch (IOException e)
        {
            throw new ExceptionPersonnalisable("Erreur de lecture du fichier");
        }
        System.out.println("Réseau chargé avec succès depuis " + nomFichier +" stations uniques trouvées.");
    }
    // METHODE n°4
    @Override
    public boolean lireInfos(String info)
    {
        // On lit le fichier texte
        return false;
    }
    // METHODE n°5 : Demander une collecte d'encombrants
    @Override
    public void faireDemandeCollecte(String typeUser) {

    }
    // METHODE n°6 : Consulter le planning de collecte (ramassage devant les maisons
    @Override
    public void consulterPlanningRamassage(String commune) {

    }
    // METHODE n°7
    public boolean existenceMdp(String id,String mdp){return false;}
}
