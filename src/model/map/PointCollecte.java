package model.map;

public class PointCollecte extends Station {

    private int capaciteMax;
    private int niveauRemplissage;

    public PointCollecte(String nom, int capaciteMax) {
        super(nom);
        this.capaciteMax = capaciteMax;
    }

    public String toString() {
        return "PointDeCollecte : " + getNom();
    }
    public int getNiveauRemplissage () {return this.niveauRemplissage;}
    public int getCapaciteMax() {return this.capaciteMax;}

    public void setNiveauRemplissage (int niveauRemplissage) {this.niveauRemplissage = niveauRemplissage;}

    public void remplir(int quantite) { //pour qu'un particulier puisse jeter ces dechets en points de collecte
        if (quantite < 0) {
            System.out.println("Erreur : On ne peut pas ajouter une quantité négative.");
            return;
        }

        // On vérifie si ça déborde (optionnel mais réaliste)
        if (this.niveauRemplissage + quantite > capaciteMax) {
            this.niveauRemplissage = capaciteMax;
            System.out.println("Attention : Le conteneur " + getNom() + " est plein !");
        } else {
            this.niveauRemplissage += quantite;
        }
    }
}
