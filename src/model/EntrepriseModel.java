package model;

import exceptions.ExceptionPersonnalisable;
import model.map.Plan;
import model.map.Station;

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
    public void bsfPlusCourtChemin(){}

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
