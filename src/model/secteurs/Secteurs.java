package model.secteurs;

import model.map.Arc;
import model.map.Plan;
import model.map.Station;

import java.util.ArrayList;
import java.util.List;

// Création des secteurs dans la ville
public class Secteurs
{
    // ATTRIBUTS
    private String nomSecteur;
    private String couleur;
    private String sommets;
    private String arcAssocie;
    private ArrayList<Arc> arcsSortantsSecteurs;

    private boolean etat = false;

    // CONSTRUCTEUR
    public Secteurs(String nomSecteur, String couleur, String sommets,String arcAssocie) {
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

    public boolean getEtat() {
        return this.etat;
    }
    // SETTER n°1
    public void setCouleur(String couleur) {this.couleur = couleur;}

    public void setEtat(boolean etat) {
        this.etat = etat;
    }

    @Override
    public String toString() {
        return nomSecteur + " (" + couleur + ")";
    }

    public List<Station> getStationsDuSecteur(Plan plan) {
        List<Station> liste = new ArrayList<>();
        if (sommets == null || sommets.isEmpty()) return liste;
        String[] noms = sommets.split(","); //separe les virgule
        for (String nom : noms) {
            Station s = plan.getStation(nom.trim());
            if (s != null) {
                liste.add(s);
            }
        }
        return liste;
    }

    public boolean aUnVoisinBloque(List<Secteurs> tousLesSecteurs) {
        if (arcAssocie == null || arcAssocie.isEmpty()) return false;
        String[] nomsVoisins = arcAssocie.split(",");
        for (String nomVoisin : nomsVoisins) {
            String voisinClean = nomVoisin.trim();
            for (Secteurs s : tousLesSecteurs) {
                if (s.getNom().trim().equalsIgnoreCase(voisinClean)) {
                    if (s.getEtat()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
