package model.particulier;
import exceptions.ExceptionPersonnalisable;
import model.ChargerFichiers;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

// Cette classe s'occupe de la logique des fichiers pour les particuliers.
// ATTENTION : On enregistre le numéro de la maison (distance entre la maison & le bout de la rue)
// dans la variable double numero.
// Le nom de la rue est enregistré comme "Route de la Mouline" par exemple, mais ce nom est associé à un arc.
public class FichiersProfil implements ChargerFichiers
{
    // ATTRIBUTS
    private HashMap<String, Profil> compte;
    public String nomFichier;

    // CONSTRUCTEUR
    public FichiersProfil(String nomFichier)
    {
        this.compte = new HashMap<>();
        this.nomFichier = nomFichier;
    }
    // GETTER n°1
    public Map<String, Profil> getCompte() {return compte;}

    // METHODE n°1 : Remplissage de la HashMap compte pour la première fois
    @Override
    public void chargerDepuisFichier()
    {
        try (BufferedReader br = new BufferedReader(new FileReader(nomFichier)))
        {
            String ligne;
            while ((ligne = br.readLine()) != null)
            {
                String[] parts = ligne.split(";");
                String nomVille = parts[0].trim();
                String prenom = parts[1].trim();
                String nom = parts[2].trim();
                double numero = Double.parseDouble(parts[3].trim());
                String rue = parts[4].trim();
                String id = parts[5].trim();
                String mdp = parts[6].trim();

                Profil p = new Profil(nomVille,prenom,nom,numero,rue,id,mdp);
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
    @Override
    public void chargerDepuisBuffer(BufferedReader br) throws IOException
    {
        try (br)
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
                String nomVille = parts[0].trim();
                String prenom = parts[1].trim();
                String nom = parts[2].trim();
                double numero = Double.parseDouble(parts[3].trim());
                String rue = parts[4].trim();
                // La méthode getArcs associe le nom entré par l'utilisateur
                // (ex: Route de la Mouline) à un arc (par exemple R4)
                // Dans le fichier texte, on aura enregistré Route de la Mouline.
                String id = parts[5].trim();
                String mdp = parts[6].trim();

                Profil p = new Profil(nomVille,prenom,nom,numero,rue,id,mdp);
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
    public void sauvegarderProfil(Profil p)
    {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nomFichier, true)))
        {
            String ligne = p.getNomVille()+ ";"+
                           p.getPrenom() + ";" +
                           p.getNom() + ";" +
                           p.getNumero() + ";" +
                           p.getRue()+ ";" +
                           p.getId() + ";" +
                           p.getMdp();
            bw.write(ligne);
            bw.newLine();
        }
        catch (IOException e)
        {
            throw new ExceptionPersonnalisable("Erreur d'écriture dans le fichier : " + e.getMessage());
        }
    }
}
