package model;

import exceptions.ExceptionPersonnalisable;
import model.particulier.DemandeCollecte;
import model.particulier.TypeEncombrant;
import java.io.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Queue;

public class FichierDemandes implements ChargerFichiers
{
    // ATTRIBUTS
    public String nomFichier;
    private Queue<DemandeCollecte> fileDemandes;

    // CONSTRUCTEUR
    public FichierDemandes(String nomFichier)
    {
        this.fileDemandes = new LinkedList<>();
        this.nomFichier = nomFichier;
    }

    // GETTER n°1
    public Queue<DemandeCollecte> getFileDemandes()
    {
        return fileDemandes;
    }
    
    // METHODE n°1 : Remplissage de la HashMap compte pour la première fois
    @Override
    public void chargerDepuisFichier()
    {
        try (BufferedReader br = new BufferedReader(new FileReader(nomFichier)))
        {
            String ligne;
            while ((ligne = br.readLine()) != null)
            {
                if (ligne.trim().isEmpty())
                {
                    continue;
                }
                String[] parts = ligne.split(";");
                String nomVille = parts[0].trim();
                String id = parts[1].trim();
                TypeEncombrant encombrant = TypeEncombrant.valueOf(parts[2].trim());
                int quantite = Integer.parseInt(parts[3].trim());
                LocalDate dateDemande = LocalDate.parse(parts[4].trim());
                String rue = parts[5].trim();
                double numero = Double.parseDouble(parts[6].trim());

                DemandeCollecte demande = new DemandeCollecte(nomVille,id,encombrant,quantite,dateDemande,rue,numero);
                fileDemandes.add(demande); //On ajoutes la demande à la file
            }
        }
        catch (IOException e)
        {
            throw new ExceptionPersonnalisable("Erreur de lecture du fichier");
        }

        System.out.println("Demandes chargées avec succès depuis " + nomFichier);
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
                if (ligne.trim().isEmpty()) {
                    continue;
                }
                String[] parts = ligne.split(";");

                if (parts.length != 7)
                {
                    System.out.println("Ligne ignorée : format invalide");
                    continue;
                }
                String nomVille = parts[0].trim();
                String id = parts[1].trim();
                TypeEncombrant encombrant = TypeEncombrant.valueOf(parts[2].trim());
                int quantite = Integer.parseInt(parts[3].trim());
                LocalDate dateDemande = LocalDate.parse(parts[4].trim());
                String rue = parts[5].trim();
                double numero = Double.parseDouble(parts[6].trim());

                DemandeCollecte demande = new DemandeCollecte(nomVille,id,encombrant,quantite,dateDemande,rue,numero);
                fileDemandes.add(demande); //On ajoutes la demande à la file
            }
        }
        catch (IOException e)
        {
            throw new ExceptionPersonnalisable("Erreur de lecture du fichier");
        }

        System.out.println("Demandes chargés avec succès depuis " + nomFichier);
    }

    // METHODE n°3 : Sauvegarder demande
    public void sauvegarderDemande(String nomFichier)
    {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nomFichier,true)))
        {
            for (DemandeCollecte demande : fileDemandes)
            {
                // Exemple de format : nomVille;idUtilisateur,typeEncombrant,quantite,date,rue,numero
                String ligne = demande.getNomVille() + ";" +
                        demande.getId() + ";" +
                        demande.getTypeEncombrant() + ";" +
                        demande.getQuantite() + ";" +
                        demande.getDateDemande() + ";" +
                        demande.getRue() + ";" +
                        demande.getNumero();
                bw.write(ligne);
                bw.newLine();
            }
            System.out.println("Liste des demandes enregistrée dans " + nomFichier);
        }
        catch (IOException e)
        {
            System.err.println("Erreur lors de l'écriture dans le fichier : " + e.getMessage());
        }
    }

    // METHODE n°4 : Vider le fichier texte des demandes
    public void viderDemande(String nomFichier)
    {
        // 1. On vide la liste Java
        fileDemandes.clear();

        // 2. On vide le fichier texte
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nomFichier, false)))
        {
            // Écrire rien = fichier effacé
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}
