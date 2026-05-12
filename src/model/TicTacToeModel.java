package model;

import utils.AbstractModeleEcoutable;

/**
 * Modèle pour le TicTacToe Africain (3 pions max + déplacements)
 */
public class TicTacToeModel extends AbstractModeleEcoutable {
    private int[][] grille;
    private final int lignes = 3;   // Fixé à 3 pour cette variante
    private final int colonnes = 3;
    
    private int joueurActuel;
    private int pionsPosesJ1;
    private int pionsPosesJ2;
    private final int MAX_PIONS = 3;
    private int nbCoups = 0;

    // Pour la phase de mouvement : mémorise le pion sélectionné
    private int ligSel = -1;
    private int colSel = -1;

    private long tempsDebut;
    private boolean fini;

    public TicTacToeModel() {
        this.grille = new int[lignes][colonnes];
        this.joueurActuel = 1;
        this.pionsPosesJ1 = 0;
        this.pionsPosesJ2 = 0;
        this.nbCoups = 0;
        this.fini = false;
        this.tempsDebut = System.currentTimeMillis();
    }
    public TicTacToeModel(int l, int c) {
        this(); 
    }

    /**
     * Logique principale : Pose ou Déplacement
     */
    public void jouerCoup(int l, int c) {
        if (fini) return;

        int jAvant = joueurActuel;

        // PHASE 1 : Pose des pions
        if (getNbPionsJoueur(joueurActuel) < MAX_PIONS) {
            poserPion(l, c);
        } 
        // PHASE 2 : Mouvement des pions
        else {
            gererMouvement(l, c);
        }
        if (joueurActuel != jAvant) {
            nbCoups++;
        }
    }

    private void poserPion(int l, int c) {
        if (grille[l][c] == 0) {
            grille[l][c] = joueurActuel;
            if (joueurActuel == 1) pionsPosesJ1++;
            else pionsPosesJ2++;
            
            verifierFin();
            if (!fini) changerTour();
            fireChangement();
        }
    }

    private void gererMouvement(int l, int c) {
        // Étape A : Sélection du pion à bouger
        if (ligSel == -1) {
            if (grille[l][c] == joueurActuel) {
                ligSel = l;
                colSel = c;
                fireChangement(); // Pour que la vue puisse surbriller le pion
            }
        } 
        // Étape B : Destination
        else {
            // Si on clique sur le même pion, on déselectionne
            if (l == ligSel && c == colSel) {
                ligSel = -1;
                colSel = -1;
            } 
            // Si on clique sur une case vide adjacente
            else if (grille[l][c] == 0 && sontAdjacents(ligSel, colSel, l, c)) {
                grille[l][c] = joueurActuel;
                grille[ligSel][colSel] = 0;
                ligSel = -1;
                colSel = -1;
                
                verifierFin();
                if (!fini) changerTour();
            }
            // Si on clique sur un autre de ses propres pions, on change la sélection
            else if (grille[l][c] == joueurActuel) {
                ligSel = l;
                colSel = c;
            }
            fireChangement();
        }
    }

    /**
     * Vérifie si deux cases sont voisines (Haut, Bas, Gauche, Droite, Diagonales)
     */
    public boolean sontAdjacents(int l1, int c1, int l2, int c2) {
        return Math.abs(l1 - l2) <= 1 && Math.abs(c1 - c2) <= 1;
    }

    private void changerTour() {
        joueurActuel = (joueurActuel == 1) ? 2 : 1;
    }

    private void verifierFin() {
        if (verifierGagnant() != 0) {
            fini = true;
        }
        // Optionnel : On peut ajouter ici une vérification si un joueur est bloqué
    }

    public int verifierGagnant() {
        // On réutilise ta logique d'alignement (lignes, colonnes, diagonales)
        // Lignes
        for (int i = 0; i < 3; i++) {
            if (grille[i][0] != 0 && grille[i][0] == grille[i][1] && grille[i][0] == grille[i][2])
                return grille[i][0];
        }
        // Colonnes
        for (int j = 0; j < 3; j++) {
            if (grille[0][j] != 0 && grille[0][j] == grille[1][j] && grille[0][j] == grille[2][j])
                return grille[0][j];
        }
        // Diagonales
        if (grille[0][0] != 0 && grille[0][0] == grille[1][1] && grille[0][0] == grille[2][2])
            return grille[0][0];
        if (grille[0][2] != 0 && grille[0][2] == grille[1][1] && grille[0][2] == grille[2][0])
            return grille[0][2];

        return 0;
    }

    public void rejouer() {
        this.grille = new int[3][3];
        this.pionsPosesJ1 = 0;
        this.pionsPosesJ2 = 0;
        this.joueurActuel = 1;
        this.ligSel = -1;
        this.colSel = -1;
        this.fini = false;
        this.tempsDebut = System.currentTimeMillis();
        fireChangement();
    }

    // --- GETTERS & SETTERS ---

    public int getValeur(int l, int c) { return grille[l][c]; }
    public int getJoueurActuel() { return joueurActuel; }
    public int getLignes() { return lignes; }
    public int getColonnes() { return colonnes; }
    public boolean estFini() { return fini; }
    
    public int getNbPionsJoueur(int joueur) {
        return (joueur == 1) ? pionsPosesJ1 : pionsPosesJ2;
    }

    public int getLigSel() { return ligSel; }
    public int getColSel() { return colSel; }

    public long getTempsEcoule() {
        return (System.currentTimeMillis() - tempsDebut) / 1000;
    }
    
    // Pour l'IA (Simulation)
    public void setValeurSimulation(int l, int c, int val) {
        this.grille[l][c] = val;
    }

    public int getNbCoups() {
        return nbCoups;
    }
}