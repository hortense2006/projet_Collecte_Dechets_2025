import model.*;
import controller.*;
import model.secteurs.*;
import model.map.*;
import model.particulier.DemandeCollecte;
import model.particulier.FichiersProfil;
import model.particulier.ParticulierModel;
import model.tournee.TourneePointCollecte;
import view.*;

import java.util.Queue;
import java.util.Scanner;

import static model.ChargeurFichiers.chargerGenerique;

public class Main
{
    public static void main(String[] args)
    {

        // ATTRIBUTS
        int typeUser ;
        String nom = "";
        int choix;
        boolean exitAll = false;
        boolean exit = false;
        int choixDeVille = 0;

        final String NOM_FICHIER_USERS = "Base_De_Donnees_Particuliers.txt";
        final String NOM_FICHIER_DEMANDES_RANVILLE = "Liste_Demandes_Ranville.txt";
        final String NOM_FICHIER_DEMANDES_BORDEAUX = "Liste_Demandes_Bordeaux.txt";
        final String NOM_FICHIER_SECTEURS_RANVILLE = "Graphe_Secteurs_Ranville.txt";
        final String NOM_FICHIER_SECTEURS_BORDEAUX = "Graphe_Quartiers_Bordeaux.txt";

        final String FICHIER_CAMION_RANVILLE = "Camion_Ranville.txt";
        final String FICHIER_CAMION_BORDEAUX = "Camion_Bordeaux.txt";
        final String FICHIER_PDC_RANVILLE = "Etat_point_collecte_Ranville.txt";
        final String FICHIER_PDC_BORDEAUX = "Etat_point_collecte_Bordeaux.txt";

        // IMPORT DES CLASSES :
        //pour le plan
        Plan plan = new Plan();
        PlanView planV = new PlanView();
        PlanController planC = new PlanController(plan,planV);
        Plan planDeVille = new Plan();

        // pour les particuliers
        Scanner sc = new Scanner(System.in);
        FichiersProfil f = new FichiersProfil(NOM_FICHIER_USERS);
        ParticulierView pv = new ParticulierView(sc);
        ParticulierModel pm = new ParticulierModel(NOM_FICHIER_USERS,choixDeVille);

        //permet de choisir le fichier qu'on utilise et affiche le plan de la ville associé
        while (choixDeVille < 1 || choixDeVille > 2)
        {
            System.out.println("Veuillez choisir la ville que vous voulez tester : " +
                    "\n 1. Ranville" +
                    "\n 2. Bordeaux");
            choixDeVille = sc.nextInt();

            planDeVille = planV.choixVille(planDeVille, choixDeVille);
        }
        /* PERMET DE CHARGER L'ETAT DE LA VILLE DE FACON A POUVOIR FAIRE LA
        COLLECTE D'ENCOMBRANTS PEU IMPORTE L'ORDRE DE CONNEXION
        (particulier->entreprise ou juste entreprise directement)*/
        // mais ça ne fait que charger les point de collecte ???
        if (choixDeVille == 1) {
            model.map.PointCollecte.chargerEtat(planDeVille, FICHIER_PDC_RANVILLE);
        }
        else if (choixDeVille == 2) {
            model.map.PointCollecte.chargerEtat(planDeVille, FICHIER_PDC_BORDEAUX);
        }

        // Pour les maisons
        Maison maison = new Maison(planDeVille,nom,choixDeVille);
        // pour l'entreprise
        FichierDemandes fd;
        ParticulierController pc; // Déclaration de la variable dans la portée principale
        // Pour les secteurs de la ville
        FichierSecteurs fs;
        SecteursModel secteursM;

        if(choixDeVille == 1) {
            // Initialisation avec le FichierDemandes de Ranville
            fd = new FichierDemandes(NOM_FICHIER_DEMANDES_RANVILLE);
            pc = new ParticulierController(pm,pv,f,fd);
            fs = new FichierSecteurs(NOM_FICHIER_SECTEURS_RANVILLE);

        } else if(choixDeVille == 2) {
            // Initialisation avec le FichierDemandes de Bordeaux
            fd = new FichierDemandes(NOM_FICHIER_DEMANDES_BORDEAUX);
            pc = new ParticulierController(pm,pv,f,fd);
            fs = new FichierSecteurs(NOM_FICHIER_SECTEURS_BORDEAUX);
        } else {
            System.err.println("Erreur: Choix de ville invalide après la boucle de sélection.");
            fd = new FichierDemandes(NOM_FICHIER_DEMANDES_RANVILLE);
            pc = new ParticulierController(pm,pv,f,fd);
            fs = new FichierSecteurs(NOM_FICHIER_SECTEURS_RANVILLE);
        }
        // Initialisation de la classe SecteursModel pour les quartiers des villes
        secteursM = new SecteursModel(fs,plan);
        // Chargement des fichiers texte des quartiers des villes
        chargerGenerique(fs.getNomFichier(),fs);
        // Coloration des secteurs via Welsh-Powell
       try
        {
            secteursM.welshPowell();
            System.out.println("Secteurs initialisés et coloriés avec succès.");
        }
        catch (java.io.IOException e)
        {
            System.err.println("Erreur critique : Impossible de charger les secteurs ! " + e.getMessage());
        }
        // Demande de collecte
        DemandeCollecte demandeC = new DemandeCollecte();

        //Entreprises
        EntrepriseModel em = new EntrepriseModel(planDeVille,pm);
        EntrepriseController enc = new EntrepriseController(em,planDeVille,maison,pv,pc);
        // Pour le camion
        CamionView camionV= new CamionView();
        CamionController camionC = new CamionController(enc,pm, camionV,fd);

        // point de collecte
        PointCollecteView pdcV = new PointCollecteView();
        PointCollecteController pcController = new PointCollecteController(planDeVille, pdcV);

        // Tournée des points de collecte
        TourneePointCollecte tourneePC = new TourneePointCollecte(planDeVille);
        TourneePointCollecteView tpcView = new TourneePointCollecteView(tourneePC);

        // Chargement du fichier texte des comptes des particuliers
        chargerGenerique(NOM_FICHIER_USERS, f);
        // Chargement du fichier texte des demandes de collecte d'encombrants
        chargerGenerique(NOM_FICHIER_DEMANDES_BORDEAUX, fd);
        chargerGenerique(NOM_FICHIER_DEMANDES_RANVILLE,fd);

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
                        System.out.println("\n 1. Consulter le plan de la ville."+
                                           "\n 2. Changer de type d'utilisateur");
                        choix = sc.nextInt();
                        sc.nextLine();
                        switch (choix)
                        {
                            case 1 : // consulter le plan de la ville
                            {
                                planV.afficherReseau(planDeVille);
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
                                if(choixDeVille == 1)
                                {
                                    DemandeCollecte d = pc.demandeCollecteE(); // Demander une collecte d'encombrants
                                    Queue<DemandeCollecte> liste = enc.recupListeDemandes(choixDeVille, fd, d);
                                }
                                else if (choixDeVille == 2)
                                {
                                    DemandeCollecte d = pc.demandeCollecteE(); // Demander une collecte d'encombrants
                                    Queue<DemandeCollecte> liste = enc.recupListeDemandes(choixDeVille, fd, d);
                                }
                                break;
                            }
                            case 2: //afficher le plan de la ville
                            {
                                exit = true;
                                if(choixDeVille == 1)
                                {
                                    pv.consulterPlanningRamassageRanville();
                                }
                                else if(choixDeVille == 2)
                                {
                                    pv.consulterPlanningRamassageBordeaux();
                                }
                                break;
                            }
                            case 3 : //aller jeter au point de collecte
                            {
                                if (choixDeVille == 1) {
                                    pcController.depotDechetAuPointCollecte(FICHIER_PDC_RANVILLE);
                                } else if (choixDeVille == 2) {
                                    pcController.depotDechetAuPointCollecte(FICHIER_PDC_BORDEAUX);
                                }
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
                                "\n 4. Faire la tournée au pied des habitations" +
                                "\n 5. Vider un ou plusieurs camions"+
                                "\n 6. Changer de type d'utilisateur");
                        choix = sc.nextInt();
                        sc.nextLine();
                        switch (choix)
                        {
                            case 1: // Collecte d'encombrants
                            {
                                if(choixDeVille == 1) {camionC.executerTournee("Ranville",NOM_FICHIER_DEMANDES_RANVILLE);}
                                else if(choixDeVille == 2) {camionC.executerTournee("Bordeaux",NOM_FICHIER_DEMANDES_BORDEAUX);}
                                break;
                            }
                            case 2: // faire la tournée des points de collecte
                            {
                                if (choixDeVille == 1){
                                    model.map.PointCollecte.chargerEtat(planDeVille, FICHIER_PDC_RANVILLE);
                                    model.CamionModel monCamion = camionC.selectionnerCamion(FICHIER_CAMION_RANVILLE); // Le camion passe à "occupé"
                                    tourneePC.tourneePlusProcheVoisinAvecCapacite(monCamion);
                                    tpcView.afficherResultats();
                                    pcController.mettreAJourFichierPoints(FICHIER_PDC_RANVILLE);
                                    if (monCamion.getCapaciteActuelle() < monCamion.getCapaciteMax()) { // s'il reste de la place dans mon camion à la fin de la tournée
                                        monCamion.setEtat("disponible");
                                        System.out.println("Info : Le camion n'est pas plein, il est marqué 'disponible'.");
                                    } else { // s'il est plein
                                        monCamion.setEtat("occupé");
                                        System.out.println("Info : Le camion est plein, il reste marqué 'occupé' (nécessite vidage).");
                                    }
                                    camionC.sauvegarderEtatCamion(monCamion, FICHIER_CAMION_RANVILLE);
                                    System.out.println("Bilan de la tournée"+
                                            "\nEtat des camions"+
                                            "ID : " + monCamion.getIdCamion() +
                                            "Charge finale : " + (int)monCamion.getCapaciteActuelle() + " / " + (int)monCamion.getCapaciteMax());
                                    PointCollecteView.afficherEtatPointCollecte(FICHIER_PDC_RANVILLE);

                                    System.out.println("\nTournée terminée. Les fichiers ont été mis à jour.");
                                } else if (choixDeVille == 2){
                                    model.map.PointCollecte.chargerEtat(planDeVille, FICHIER_PDC_BORDEAUX);
                                    model.CamionModel monCamion = camionC.selectionnerCamion(FICHIER_CAMION_BORDEAUX); // Le camion passe à "occupé"
                                    tourneePC = new TourneePointCollecte(planDeVille);
                                    tpcView = new TourneePointCollecteView(tourneePC);
                                    tourneePC.tourneePlusProcheVoisinAvecCapacite(monCamion);
                                    tpcView.afficherResultats();
                                    pcController.mettreAJourFichierPoints(FICHIER_PDC_BORDEAUX);
                                    if (monCamion.getCapaciteActuelle() < monCamion.getCapaciteMax()) { // s'il reste de la place dans mon camion à la fin de la tournée
                                        monCamion.setEtat("disponible");
                                        System.out.println("Info : Le camion n'est pas plein, il est marqué 'disponible'.");
                                    } else { // s'il est plein
                                        monCamion.setEtat("occupé");
                                        System.out.println("Info : Le camion est plein, il reste marqué 'occupé' (nécessite vidage).");
                                    }
                                    camionC.sauvegarderEtatCamion(monCamion, FICHIER_CAMION_BORDEAUX);
                                    System.out.println("Bilan de la tournée"+
                                            "\nEtat des camions"+
                                            "ID : " + monCamion.getIdCamion() +
                                            "Charge finale : " + (int)monCamion.getCapaciteActuelle() + " / " + (int)monCamion.getCapaciteMax());
                                    PointCollecteView.afficherEtatPointCollecte(FICHIER_PDC_BORDEAUX);

                                    System.out.println("\nTournée terminée. Les fichiers ont été mis à jour.");
                                }
                                break;
                            }
                            case 3 : // afficher le niveau des points de collectes
                            {
                                if (choixDeVille == 1)
                                {
                                    PointCollecteView.afficherEtatPointCollecte(FICHIER_PDC_RANVILLE);
                                } else if (choixDeVille == 2){
                                    PointCollecteView.afficherEtatPointCollecte(FICHIER_PDC_BORDEAUX);
                                }
                                break;
                            }
                            case 4 : // tournée au pied des habitation par secteur
                            {
                                if (choixDeVille == 1) {
                                    CamionModel monCamion = camionC.selectionnerCamion(FICHIER_CAMION_RANVILLE);
                                    if (monCamion != null) {
                                        TourneeAuPiedHabitationController tsc = new TourneeAuPiedHabitationController(planDeVille, camionC, secteursM);
                                        tsc.lancerProcessusComplet(monCamion);
                                        if (monCamion.getCapaciteActuelle() < monCamion.getCapaciteMax()) {
                                            monCamion.setEtat("disponible");
                                            System.out.println("Info Main : Camion non plein -> Disponible.");
                                        } else {
                                            monCamion.setEtat("occupé");
                                            System.out.println("Info Main : Camion plein -> Occupé.");
                                        }
                                        camionC.sauvegarderEtatCamion(monCamion, FICHIER_CAMION_RANVILLE);
                                    }
                                } else if (choixDeVille == 2) {
                                    CamionModel monCamion = camionC.selectionnerCamion(FICHIER_CAMION_BORDEAUX);
                                    if (monCamion != null) {
                                        TourneeAuPiedHabitationController tsc = new TourneeAuPiedHabitationController(planDeVille, camionC, secteursM);
                                        tsc.lancerProcessusComplet(monCamion);
                                        if (monCamion.getCapaciteActuelle() < monCamion.getCapaciteMax()) {
                                            monCamion.setEtat("disponible");
                                            System.out.println("Info Main : Camion non plein -> Disponible.");
                                        } else {
                                            monCamion.setEtat("occupé");
                                            System.out.println("Info Main : Camion plein -> Occupé.");
                                        }
                                        camionC.sauvegarderEtatCamion(monCamion, FICHIER_CAMION_BORDEAUX);
                                    }
                                }
                                break;
                            }
                            case 5 : // vider les camions
                            {
                                int choixCamion;
                                System.out.println("\n 1. Vider tous les camions" +
                                        "\n 2. Vider un camion spécifique");
                                choixCamion = sc.nextInt();
                                switch (choixCamion){
                                    case 1 :
                                    {
                                        if (choixDeVille == 1){
                                            camionC.viderTousCamions(FICHIER_CAMION_RANVILLE);
                                        } else if (choixDeVille == 2){
                                            camionC.viderTousCamions(FICHIER_CAMION_BORDEAUX);
                                        }
                                        break;
                                    }
                                    case 2 :
                                    {
                                        if (choixDeVille == 1){
                                            camionC.viderUnCamion(FICHIER_CAMION_RANVILLE);
                                        } else  if (choixDeVille == 2){
                                            camionC.viderUnCamion(FICHIER_CAMION_BORDEAUX);
                                        }
                                        break;
                                    }
                                }
                                break;
                            }
                            case 6:// Changement d'utilisateur
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