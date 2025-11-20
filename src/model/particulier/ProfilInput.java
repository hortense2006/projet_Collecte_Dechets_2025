package model.particulier;
import model.map.Arc;

/* CLASSE TERMINEE */
// DT0 (Data Transfer Object)
public record ProfilInput(String prenom, String nom, double numero, Arc rue, String mdp) {}
