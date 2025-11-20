package model.particulier;
import exceptions.ExceptionPersonnalisable;
import model.map.Arc;
import model.map.Plan;
import java.io.*;
import java.util.HashMap;

/* CLASSE TERMINEE*/
// Cette classe s'occupe de la logique des fichiers pour les particuliers.
// ATTENTION : On enregistre le numéro de la maison (distance entre la maison & le bout de la rue)
// dans la variable double numero.
// Le nom de la rue est enregistré
public class FichiersProfil
{
    // ATTRIBUTS
    private HashMap<String, Profil> compte;
    public String nomFichier;
    private Plan plan;

    // CONSTRUCTEUR
    public FichiersProfil(String nomFichier, Plan plan)
    {
        this.compte = new HashMap<>();
        this.nomFichier = nomFichier;
        this.plan = plan;
    }

    // METHODE n°1 : Remplissage de la HashMap compte pour la première fois
    public void chargerInfos()
    {
        try (BufferedReader br = new BufferedReader(new FileReader(nomFichier)))
        {
            String ligne;
            while ((ligne = br.readLine()) != null)
            {
                String[] parts = ligne.split(";");
                String prenom = parts[0].trim();
                String nom = parts[1].trim();
                double numero = Integer.parseInt(parts[2].trim());
                String nomRue = parts[3].trim();
                Arc rue = plan.getArcs().get(nomRue); // conversion du texte → Arc
                String id = parts[4].trim();
                String mdp = parts[5].trim();

                Profil p = new Profil(prenom,nom,numero,rue,id,mdp);
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
    public void chargerInfosDepuisBuffer()
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
                String nomRue = parts[3].trim();
                Arc rue = plan.getArcs().get(nomRue); // conversion du texte → Arc
                // La méthode getArcs associe le nom entré par l'utilisateur
                // (ex: Route de la Mouline) à un arc (par exemple R4)
                // Dans le fichier texte, on aura enregistré Route de la Mouline.
                String id = parts[4].trim();
                String mdp = parts[5].trim();

                Profil p = new Profil(prenom,nom,numero,rue,id,mdp);
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
            String ligne = p.getPrenom() + ";" +
                    p.getNom() + ";" +
                    p.getNumero() + ";" +
                    p.getRue().getIdLigne() + ";" +
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
