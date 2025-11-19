package model;

import exceptions.ExceptionPersonnalisable;
import model.map.Arc;
import model.map.Plan;
import model.map.Station;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class EntrepriseModel
{
    // ATTRIBUTS
    private Plan p;
    // CONSTRUCTEUR
    public EntrepriseModel(Plan p)
    {
        this.p = p;
    }

    //METHODE n°1 : Calcul d plus court chemin
    // METHODE n°4 : Calcul du plus court chemin  ( méthode bsf)
    public Itineraire bfsPlusCourtChemin (String nomDepart, String nomArrivee) throws ExceptionPersonnalisable
    {
        Station depart = verifierStations(nomDepart, nomArrivee); // verifie qu'il n'y a pas d'exception
        Station destination = p.getStation(nomArrivee); // arc par lequel on découvre la station
        Map<Station, Arc> predecesseurs = new HashMap<>(); //mémoire pour garder toutes les stations passées
        Queue<Arc> fileArcs = new LinkedList<>();
        predecesseurs.put(depart, null);
        fileArcs.addAll(depart.getArcsSortants());
        Station stationArriveeTrouvee = null;
        while (!fileArcs.isEmpty() && stationArriveeTrouvee == null)
        { // tant que le fichier n'est pas vide et qu'on est pas arrivé
            Arc arcCourant = fileArcs.poll(); //initialise l'arc sur lequel on est
            Station successeur = arcCourant.getArrivee(); // regarde la station qui arrive
            if (!predecesseurs.containsKey(successeur))
            { //pour ne pas tourner en rond
                predecesseurs.put(successeur, arcCourant); //met en mémoir l'arc qu'on vient de fair et la station d'avant
                if (successeur.equals(destination))
                { // arrête si l'arrivé est trouver
                    stationArriveeTrouvee = successeur;
                }
                else
                {
                    fileArcs.addAll(successeur.getArcsSortants()); //sinon on continue
                }
            }
        }
        return reconstituerChemin(depart, destination, stationArriveeTrouvee, predecesseurs);//pour afficher grace à la méthode dans afficher itinéraire
    }


    // METHODE n°2 : Vérification des stations
    public Station verifierStations(String nomDepart, String nomArrivee) throws ExceptionPersonnalisable
    {
        Station depart = p.getStation(nomDepart);
        Station arrivee = p.getStation(nomArrivee);
        if (depart == null)
        {
            throw new ExceptionPersonnalisable("Station de départ inconnue: " + nomDepart);
        }
        if (arrivee == null)
        {
            throw new ExceptionPersonnalisable("Station de d'arrivée inconnue: " + nomArrivee);
        }
        if (depart.equals(arrivee))
        {
            throw new ExceptionPersonnalisable("Départ et Arrivée identiques.");
        }
        return arrivee;
    }
}
