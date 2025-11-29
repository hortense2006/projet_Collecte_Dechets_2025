import model.*;
import controller.*;
import model.map.*;
import model.particulier.DemandeCollecte;
import model.particulier.FichiersProfil;
import model.particulier.ParticulierModel;
import model.Tournee.TourneePointCollecte;
import view.*;
import java.io.*;
import java.util.Queue;
import java.util.Scanner;

import static model.ChargeurFichiers.chargerGenerique;

public class Main
{
    public static void main(String[] args) {

        // ATTRIBUTS
        int typeUser ;
        String nom = "";
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

        Plan planDeVille = planC.choixFichier(plan); //permet de choisir le fichier qu'on utilise et affiche le plan de la ville associé


        // Pour les maisons
        Maison maison = new Maison(planDeVille,nom);
        // pour l'entreprise
        FichierDemandes fd = new FichierDemandes(NOM_FICHIER_DEMANDES);
        EntrepriseModel em = new EntrepriseModel(planDeVille,pm);
        EntrepriseController enc = new EntrepriseController(em,planDeVille,maison);

        // Pour le camion
        CamionView camionView = new CamionView();
        CamionController camC = new CamionController(enc,pm,camionView);

        // Chargement des différents fichiers texte
        chargerGenerique(NOM_FICHIER_USERS, f);
        chargerGenerique(NOM_FICHIER_DEMANDES, fd);

        //On récupère la liste de demandes
        Queue<DemandeCollecte> listeDemandes = fd.getFileDemandes();

        while (!exitAll)  //permet de faire tourner l'application sans fin tant que exitAll n'a pas été choisi
        {
            System.out.println("Choisissez votre profil : " +
                    "\n 1. commune" +
                    "\n 2. particulier" +
                    "\n 3. entreprise" +
                    "\n 4. quitter");
            typeUser = sc.nextInt();
            exit = false;
            switch (typeUser)
            {
                case 1:
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
                            case 1 : // consulter le plan de la ville
                            {
                                planV.afficherReseau(plan);
                                break;
                            }
                            case 2 :// Changement d'utilisateur
                            {
                                exit = true;
                                System.out.println("\n Changement d'utilisateur");
                                sc.nextLine();
                                break;
                            }
                        }
                    }
                    break;
                }
                case 2:
                {
                    pc.login();// Connexion/Inscription
                    while (!exit)
                    { //permet de faire tourner l'application utilisateur tant qu'on a pas demandé de changer de type d'utilisateur
                        System.out.println("Que souhaitez-vous faire :");
                        System.out.println("\n1. Demande de collecte"+
                                "\n2. Consulter le planning de ramassage"+
                                "\n3. Aller jeter ses déchets au point de collecte " +
                                "\n4. Changer de type d'utilisateur");
                        choix = sc.nextInt();
                        sc.nextLine();
                        switch (choix)
                        {
                            case 1: // faire une demande d'encombrant
                            {
                                exit = true;
                                DemandeCollecte d = pc.DemandeCollecteE(); // Demander une collecte d'encombrants
                                Queue<DemandeCollecte> liste = pc.remplirListeDemandeCollecte(d,listeDemandes);// On remplit la liste de demandes.
                                fd.sauvegarderDemande(NOM_FICHIER_DEMANDES); // On enregistre la demande dans le bon fichier texte
                                System.out.println(liste);
                                break;
                            }
                            case 2: //afficher le plan de ranville
                            {
                                exit = true;
                                pm.consulterPlanningRamassage("ranville");
                                break;
                            }
                            case 3 : //aller jeter au point de collecte
                            {
                                PointCollecteView pcView = new PointCollecteView();
                                PointCollecteController pcController = new PointCollecteController(planDeVille, pcView);
                                PointCollecte.chargerEtat(planDeVille);
                                pcController.depotDechetAuPointCollecte();
                                break;
                            }
                            case 4: //changement d'utilisateur
                            {
                                exit = true; // Changement d'utilisateur
                                System.out.println("\n Changement d'utilisateur");
                                break;
                            }
                        }
                    }
                    break;
                }
                case 3:
                {
                    while(!exit)
                    {
                        System.out.println("Que souhaitez-vous faire :");
                        System.out.println("\n1. Planifier une tournee"+
                                "\n2. Organiser une collecte d'encombrants"+
                                "\n3. Faire une tournée des points de collectes" +
                                "\n4. Afficher l'état des points de collectes"+
                                "\n5. Changer de type d'utilisateur");
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
                                camC.executerTournee(NOM_FICHIER_DEMANDES);
                                break;
                            }
                            case 3: // faire la tournée des points de collecte
                            {
                                TourneePointCollecte tourneePC = new TourneePointCollecte(planDeVille);
                                tourneePC.tourneePlusProcheVoisinSansCapacite();
                                TourneePointCollecteView tpcView = new TourneePointCollecteView(tourneePC);
                                tpcView.afficherResultats();
                                break;
                            }
                            case 4 : // afficher le niveau des points de collectes
                            {
                                PointCollecteView pcView = new PointCollecteView();
                                pcView.afficherEtatPointCollecte();
                                break;
                            }
                            case 5:// Changement d'utilisateur
                            {
                                exit = true; // Changement d'utilisateur
                                System.out.println("\n Changement d'utilisateur");
                                break;
                            }
                        }
                    }
                    break;
                }
                case 4: // sortie du programme
                {
                    exitAll = true;
                    System.out.println("\n Au revoir. ");
                    break;
                }
            }
        }
    }
}