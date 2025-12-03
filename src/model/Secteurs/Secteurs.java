package model.Secteurs;

import model.map.Arc;

import java.util.ArrayList;

// Création des secteurs dans la ville
public class Secteurs
{
    // ATTRIBUTS
    private String nomSecteur;
    private String couleur;
    private String sommets;
    private String arcAssocie;
    private ArrayList<Arc> arcsSortantsSecteurs;
    // CONSTRUCTEUR
    public Secteurs(String nomSecteur, String couleur, String sommets,String arcAssocie)
    {
        this.nomSecteur = nomSecteur;
        this.couleur = couleur;
        this.sommets = sommets;
        this.arcAssocie = arcAssocie;
        this.arcsSortantsSecteurs = new ArrayList<>();
    }
    // GETTER n°1 : nom
    public String getNom() {return nomSecteur;}
    // GETTER n°2 : zone
    public String  getCouleur() {return couleur;}
    // GETTER n°3
    public String getSommets() {return sommets;}
    // GETTER n°4
    public String getArcAssocie() {return arcAssocie;}
    //GETTER n°5
    public int getDegre() {
        return this.arcsSortantsSecteurs.size();
    }
    // SETTER n°1
    public void setCouleur(String couleur) {this.couleur = couleur;}

}
