package model.Secteurs;

import model.map.Arc;
import model.map.PointCollecte;
import model.map.Station;

import java.util.List;

// Création des secteurs dans la ville
public class Secteurs
{
    // ATTRIBUTS
    private String nomSecteur;
    private String couleur;
    private String PointCollecteAssocie;
    private String sommets;
    private String arcAssocie;
    // CONSTRUCTEUR
    public Secteurs(String nomSecteur, String couleur, String PointCollecteAssocie, String sommets,String arcAssocie)
    {
        this.nomSecteur = nomSecteur;
        this.couleur = couleur;
        this.PointCollecteAssocie = PointCollecteAssocie;
        this.sommets = sommets;
        this.arcAssocie = arcAssocie;
    }
    // GETTER n°1 : nom
    public String getNom() {return nomSecteur;}
    // GETTER n°2 : zone
    public String  getCouleur() {return couleur;}
    // GETTER n°3
    public String getPointCollecteAssocie() {return PointCollecteAssocie;}
    // GETTER n°4
    public String getSommets() {return sommets;}
    // GETTER n°5
    public String getArcAssocie() {return arcAssocie;}
}
