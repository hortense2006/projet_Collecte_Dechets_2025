import model.*;
import controller.*;
//import model.Secteurs.FichierSecteurs;
import model.map.*;
import model.particulier.DemandeCollecte;
import model.particulier.FichiersProfil;
import model.particulier.ParticulierModel;
import model.Tournee.TourneePointCollecte;
import view.*;

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
        final String NOM_FICHIER_SECTEURS = "Graphe_Secteurs_Ranville.txt";

        // IMPORT DES CLASSES :
        //pour le plan
        Plan plan = new Plan();
        PlanView planV = new PlanView();
        PlanController planC = new PlanController(plan,planV);
        Plan planDeVille = new Plan();

        // Pour les secteurs de la ville
        //FichierSecteurs fs = new FichierSecteurs(NOM_FICHIER_SECTEURS);
        // pour les particuliers
        Scanner sc = new Scanner(System.in);
        FichiersProfil f = new FichiersProfil(NOM_FICHIER_USERS);
        ParticulierView pv = new ParticulierView(sc);
        ParticulierModel pm = new ParticulierModel(NOM_FICHIER_USERS);
        ParticulierController pc = new ParticulierController(pm,pv,f);

       planDeVille = planV.ChoixVille(planDeVille);

         //permet de choisir le fichier qu'on utilise et affiche le plan de la ville associé


        // Pour les maisons
        Maison maison = new Maison(planDeVille,nom);
        // pour l'entreprise
        FichierDemandes fd = new FichierDemandes(NOM_FICHIER_DEMANDES);
        EntrepriseModel em = new EntrepriseModel(planDeVille,pm);
        EntrepriseController enc = new EntrepriseController(em,planDeVille,maison,pv);

        // Pour le camion
        CamionView camionV= new CamionView();
        CamionController camionC = new CamionController(enc,pm, camionV);

        // point de collecte
        PointCollecteView pdcV = new PointCollecteView();
        PointCollecteController pcController = new PointCollecteController(planDeVille, pdcV);
        // Tournée des points de collecte
        TourneePointCollecte tourneePC = new TourneePointCollecte(planDeVille);
        TourneePointCollecteView tpcView = new TourneePointCollecteView(tourneePC);

        // Chargement des différents fichiers texte
        chargerGenerique(NOM_FICHIER_USERS, f);
        chargerGenerique(NOM_FICHIER_DEMANDES, fd);
        //chargerGenerique(NOM_FICHIER_SECTEURS,fs);

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
                        System.out.println("\n 1. Consulter le plan de Ranville."+
                                           "\n 2. Changer de type d'utilisateur");
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
                        System.out.println("\n 1. Demande de collecte"+
                                "\n 2. Consulter le planning de ramassage"+
                                "\n 3. Aller jeter ses déchets au point de collecte " +
                                "\n 4. Changer de type d'utilisateur");
                        choix = sc.nextInt();
                        sc.nextLine();
                        switch (choix)
                        {
                            case 1: // faire une demande d'encombrant
                            {
                                DemandeCollecte d = pc.DemandeCollecteE(); // Demander une collecte d'encombrants
                                Queue<DemandeCollecte> liste = pc.remplirListeDemandeCollecte(d,listeDemandes);// On remplit la liste de demandes.
                                fd.sauvegarderDemande(NOM_FICHIER_DEMANDES); // On enregistre la demande dans le bon fichier texte
                                System.out.println(liste);
                                break;
                            }
                            case 2: //afficher le plan de ranville
                            {
                                exit = true;
                                pv.consulterPlanningRamassage();
                                break;
                            }
                            case 3 : //aller jeter au point de collecte
                            {
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
                        System.out.println("\n 1. Organiser une collecte d'encombrants"+
                                "\n 2. Faire une tournée des points de collectes" +
                                "\n 3. Afficher l'état des points de collectes" +
                                "\n 4. Vider un ou plusieurs camions"+
                                "\n 5. Changer de type d'utilisateur");
                        choix = sc.nextInt();
                        sc.nextLine();
                        switch (choix)
                        {
                            case 1: // Collecte d'encombrants
                            {
                                camionC.executerTournee(NOM_FICHIER_DEMANDES);
                                break;
                            }
                            case 2: // faire la tournée des points de collecte
                            {
                                model.map.PointCollecte.chargerEtat(planDeVille);
                                model.CamionModel monCamion = camionC.selectionnerCamion(); // Le camion passe à "occupé"
                                tourneePC.tourneePlusProcheVoisinAvecCapacite(monCamion);
                                tpcView.afficherResultats();
                                pcController.mettreAJourFichierPoints();
                                if (monCamion.getCapaciteActuelle() < monCamion.getCapaciteMax()) { // s'il reste de la place dans mon camion à la fin de la tournée
                                    monCamion.setEtat("disponible");
                                    System.out.println("Info : Le camion n'est pas plein, il est marqué 'disponible'.");
                                } else { // s'il est plein
                                    monCamion.setEtat("occupé");
                                    System.out.println("Info : Le camion est plein, il reste marqué 'occupé' (nécessite vidage).");
                                }
                                camionC.sauvegarderEtatCamion(monCamion);
                                System.out.println("Bilan de la tournée"+
                                                    "\nEtat des camions"+
                                                    "ID : " + monCamion.getIdCamion() +
                                                    "Charge finale : " + (int)monCamion.getCapaciteActuelle() + " / " + (int)monCamion.getCapaciteMax());
                                PointCollecteView.afficherEtatPointCollecte();

                                System.out.println("\nTournée terminée. Les fichiers ont été mis à jour.");
                                break;
                            }
                            case 3 : // afficher le niveau des points de collectes
                            {
                                PointCollecteView.afficherEtatPointCollecte();
                                break;
                            }
                            case 4 : // vider les camions
                            {
                                int choixCamion;
                                System.out.println("\n 1. Vider tous les camions" +
                                        "\n 2. Vider un camion spécifique");
                                choixCamion = sc.nextInt();
                                switch (choixCamion){
                                    case 1 :
                                    {
                                        CamionView cv = new CamionView();
                                        CamionController ccGlobal = new CamionController(enc, pm, cv);
                                        ccGlobal.viderTousCamions();
                                        break;
                                    }
                                    case 2 :
                                    {
                                        camionC.viderUnCamion();
                                        break;
                                    }
                                }
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