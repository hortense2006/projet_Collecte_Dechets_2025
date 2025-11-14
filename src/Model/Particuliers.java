package Model;

import Exceptions.ExceptionPersonnalisable;
import java.io.*;
import java.util.*;

public class Particuliers implements Utilisateur
{
    // ATTRIBUTS
    private String typeUser; // "collectivité", "entreprise", "particulier"
    public static final String nomFichier = "Base_De_Donnees_Particuliers.txt";
    private boolean estConnecte;
    private Map<String, Profil> compte;

    Scanner sc = new Scanner(System.in);

    // CONSTRUCTEUR
    public Particuliers()
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
                register();
                break;
            }
            default:{throw new ExceptionPersonnalisable("Choix invalide.");}
        }
    }

    // METHODE n°2 : CONNEXION
    public void signin()
    {
        System.out.println("Saisissez votre identifiant:");
        String idPropose = sc.nextLine(); // On récupère l'identifiant saisi
        if(compte.containsKey(idPropose)) // Lecture du fichier : l'identifiant existes
        {
            Profil p = compte.get(idPropose);
            System.out.println("Saisissez votre mot de passe:");
            String mdpPropose = sc.nextLine(); // On récupère le mdp saisi
            if(p.getMdp().equals(mdpPropose))// On vérifie si c'est le bon mot de passe
            {
                setEstConnecte(true);
                System.out.println("Connexion réussie !");
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

    // METHODE n°3 : INSCRIPTION
    public void register()
    {
        try{}
        catch(ExceptionPersonnalisable e){}
        System.out.println("Saisissez votre prenom");
        String prenom = sc.nextLine();
        System.out.println("Saisissez votre nom de famille:");
        String nom = sc.nextLine();
        System.out.println("Saisissez votre numero d'habitation:");
        int numero = sc.nextInt();
        sc.nextLine(); // Vide le buffer
        System.out.println("Saisissez le nom de votre rue:");
        String rue = sc.nextLine();
        String id = UUID.randomUUID().toString(); // Chaque id est différent
        System.out.println("Voici votre identifiant:" + id);
        System.out.println("Saisissez un mot de passe:");
        String mdp = sc.nextLine();
        // On crée un profil
        Profil p = new Profil(prenom,nom,numero,rue,id,mdp);
        compte.put(id,p); // On l'ajoutes à la HashMap compte
        // On enregistre les infos dans le fichier texte
        sauvegarderProfil(nomFichier,p);
    }
    // METHODE n°4 : Remplissage de la HashMap compte pour la première fois
    @Override
    public void chargerInfos(String nomFichier)
    {
        try (BufferedReader br = new BufferedReader(new FileReader(nomFichier)))
        {
            String ligne;
            while ((ligne = br.readLine()) != null)
            {
                String[] parts = ligne.split(";");
                String prenom = parts[0].trim();
                String nom = parts[1].trim();
                int numero = Integer.parseInt(parts[2].trim());
                String rue = parts[3].trim();
                String id = parts[4].trim();
                String mdp = parts[5].trim();

                Profil p = new Profil(prenom, nom, numero, rue, id, mdp);
                compte.put(id, p); // id comme clé, Profil comme valeur
            }
        }
        catch (IOException e)
        {
            throw new ExceptionPersonnalisable("Erreur de lecture du fichier");
        }

        System.out.println("Profils chargés avec succès depuis " + nomFichier);
    }

    // METHODE n°5 : lecture après une première ouverture
    public void chargerReseauDepuisBuffer()
    {
        try (BufferedReader br = new BufferedReader(new FileReader(nomFichier)))
        {
            String ligne;
            while ((ligne = br.readLine()) != null)
            {
                String[] parts = ligne.split(";");

                if (parts.length != 6)
                {
                    System.out.println("Ligne ignorée : format invalide");
                    continue;
                }
                String prenom = parts[0].trim();
                String nom = parts[1].trim();
                int numero = Integer.parseInt(parts[2].trim());
                String rue = parts[3].trim();
                String id = parts[4].trim();
                String mdp = parts[5].trim();

                Profil p = new Profil(prenom, nom, numero, rue, id, mdp);
                compte.put(id, p); // id comme clé, Profil comme valeur
            }
        }
        catch (IOException e)
        {
            throw new ExceptionPersonnalisable("Erreur de lecture du fichier");
        }

        System.out.println("Profils chargés avec succès depuis " + nomFichier);
    }

    // METHODE n°6 : Sauvegarde dans le fichier texte
    public void sauvegarderProfil(String nomFichier, Profil p)
    {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nomFichier, true)))
        {
            String ligne = p.getPrenom() + ";" +
                    p.getNom() + ";" +
                    p.getNumero() + ";" +
                    p.getRue() + ";" +
                    p.getId() + ";" +
                    p.getMdp();
            bw.write(ligne);
            bw.newLine();
        }
        catch (IOException e)
        {
            System.out.println("Erreur d'écriture dans le fichier : " + e.getMessage());
        }
    }

    // METHODE n°7 : Demander une collecte d'encombrants
    @Override
    public void faireDemandeCollecte(String typeUser)
    {
        System.out.println("Voulez-vous demander une collecte d'encombrants");

    }
    // METHODE n°8 : Consulter le planning de collecte (ramassage devant les maisons)
    @Override
    public void consulterPlanningRamassage(String commune) {

    }
}
