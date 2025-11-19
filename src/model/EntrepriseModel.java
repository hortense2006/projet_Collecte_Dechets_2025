package model;

import exceptions.ExceptionPersonnalisable;
import model.map.Station;

public class EntrepriseModel
{
    //METHODE n°3 : Calcul d plus court chemin
    public void bsfPlusCourtChemin(){}

    // METHODE n°4 : Vérification des stations
    public Station verifierStations(String nomDepart, String nomArrivee) throws ExceptionPersonnalisable
    {
        Station depart = pc.getStation(nomDepart);
        Station arrivee = pc.getStation(nomArrivee);
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
