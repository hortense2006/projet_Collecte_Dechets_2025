import model.*;
import controller.*;
import model.map.*;
import model.particulier.DemandeCollecte;
import model.particulier.FichiersProfil;
import model.particulier.ParticulierModel;
import view.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import static model.ChargeurFichiers.chargerGenerique;

public class Main
{
    public static void main(String[] args) {

        // ATTRIBUTS
        String typeUser = "";
        int choix;
        boolean exitAll = false;
        boolean exit = false;

        final String NOM_FICHIER_USERS = "Base_De_Donnees_Particuliers.txt";
        final String NOM_FICHIER_DEMANDES = "Liste_Demandes.txt";

        // IMPORT DES CLASSES :
        //pour le plan
        Plan plan = new Plan();
        PlanView planV = new PlanView();
        PlanController planC = new PlanController(plan,planV);

        // pour les particuliers
        Scanner sc = new Scanner(System.in);
        FichiersProfil f = new FichiersProfil(NOM_FICHIER_USERS);
        ParticulierView pv = new ParticulierView(sc);
        ParticulierModel pm = new ParticulierModel(NOM_FICHIER_USERS);
        ParticulierController pc = new ParticulierController(pm,pv,f);

        // pour l'entreprise
        FichierDemandes fd = new FichierDemandes(NOM_FICHIER_DEMANDES);
        EntrepriseModel em = new EntrepriseModel(plan,pm);
        EntrepriseController enc = new EntrepriseController(em,plan);

        // Pour le camion
        CamionController camC = new CamionController(enc,pm);

        // Chargement des différents fichiers texte
        chargerGenerique(NOM_FICHIER_USERS, f);
        chargerGenerique(NOM_FICHIER_DEMANDES, fd);

        plan = planC.choixFichier(plan); //permet de choisir le fichier qu'on utilise et affiche le plan de la ville associé

        //On récupère la liste de demandes
        Queue<DemandeCollecte> listeDemandes = fd.getFileDemandes();

        while (!exitAll)  //permet de faire tourner l'application sans fin tant que exitAll n'a pas été choisi
        {
            System.out.println("Choisissez votre profil : " +
                    "\n commune" +
                    "\n particulier" +
                    "\n entreprise" +
                    "\n quitter");
            typeUser = sc.nextLine();
            exit = false;
            switch (typeUser)
            {
                case "commune":
                {
                    while (!exit)
                    {
                        System.out.println("Que souhaitez-vous faire :");
                        System.out.println("\n1. Consulter le plan de Ranville."+
                                           "\n2. Changer de type d'utilisateur");
                        choix = sc.nextInt();
                        sc.nextLine();
                        switch (choix)
                        {
                            case 1 :
                            {
                                planV.afficherReseau(plan);
                                break;
                            }
                            case 2 :
                            {
                                exit = true; // Changement d'utilisateur
                                System.out.println("\n Changement d'utilisateur");
                                sc.nextLine();
                                break;
                            }
                        }
                    }
                    break;
                }
                case "particulier":
                {
                    pc.login();// Connexion/Inscription
                    while (!exit)
                    { //permet de faire tourner l'application utilisateur tant qu'on a pas demandé de changer de type d'utilisateur
                        System.out.println("Que souhaitez-vous faire :");
                        System.out.println("\n1. Demande de collecte"+
                                "\n2. Consulter le planning de ramassage"+
                                "\n3. Changer de type d'utilisateur");
                        choix = sc.nextInt();
                        sc.nextLine();
                        switch (choix)
                        {
                            case 1:
                            {
                                exit = true;
                                DemandeCollecte d = pc.DemandeCollecteE(); // Demander une collecte d'encombrants
                                Queue<DemandeCollecte> liste = pc.remplirListeDemandeCollecte(d,listeDemandes);// On remplit la liste de demandes.
                                fd.sauvegarderDemande(NOM_FICHIER_DEMANDES); // On enregistre la demande dans le bon fichier texte
                                System.out.println(liste);
                                break;
                            }
                            case 2:
                            {
                                exit = true;
                                pm.consulterPlanningRamassage("ranville");
                                break;
                            }
                            case 3: //Sortie
                            {
                                exit = true; // Changement d'utilisateur
                                System.out.println("\n Changement d'utilisateur");
                                sc.nextLine();
                                break;
                            }
                        }
                    }
                    break;
                }
                case "entreprise":
                {
                    while(!exit)
                    {
                        System.out.println("Que souhaitez-vous faire :");
                        System.out.println("\n1. Planifier une tournee"+
                                "\n2. Organiser une collecte d'encombrants"+
                                "\n3. Changer de type d'utilisateur");
                        choix = sc.nextInt();
                        sc.nextLine();
                        switch (choix)
                        {
                            case 1: // Tournée classique
                            {

                                break;
                            }
                            case 2: // Collecte d'encombrants
                            {
                                camC.executerTournee();
                                break;
                            }
                            case 3: // Sortie
                            {
                                exit = true; // Changement d'utilisateur
                                System.out.println("\n Changement d'utilisateur");
                                sc.nextLine();
                                break;
                            }
                        }
                    }
                    break;
                }
                case "quitter":
                {
                    exitAll = true;
                    System.out.println("\n Au revoir. ");
                    break;
                }
            }
        }
    }
}