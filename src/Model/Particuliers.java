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

    // CONSTRUCTEUR
    public Particuliers()
    {
        this.typeUser = typeUser;
        this.estConnecte = false;
        this.compte = new HashMap<>();
    }

    // METHODE n°1 : Remplissage de la HashMap compte pour la première fois
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

    // METHODE n°2 : lecture après une première ouverture
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

    // METHODE n°3 : Sauvegarde dans le fichier texte
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

    // METHODE n°4 : Demander une collecte d'encombrants
    @Override
    public void faireDemandeCollecte(String typeUser)
    {
        System.out.println("Voulez-vous demander une collecte d'encombrants");

    }
    // METHODE n°5 : Consulter le planning de collecte (ramassage devant les maisons)
    @Override
    public void consulterPlanningRamassage(String commune) {

    }
}
