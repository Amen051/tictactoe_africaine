package model;

import java.util.Random;
import utils.AbstractModeleEcoutable;

/**
 * Classe moteur qui gère le modele avec les méthodes de déplacement
 */

public class TicTacToeModel extends AbstractModeleEcoutable {
    private int[][] grille;      
    private int lignes;
    private int colonnes;
    private int nbCoups;
    private static final int CASE_VIDE = 0; 
    private long tempsDebut;
    private long tempsFin;
    private int joueurActuel;

    /**
     * COnstructeur
     * @param lignes = nombre de lignes du puzzle
     * @param colonnes = nombre  de colonnes
     * @requires  lignes superieur ou egale à 2 et colonnes superieure ou egale à 2
     */
    
    public TicTacToeModel(int lignes, int colonnes) {
        this.lignes = lignes;
        this.colonnes = colonnes;
        this.grille = new int[lignes][colonnes];
        this.nbCoups = 0;
        this.joueurActuel = 1;
        this.tempsDebut = System.currentTimeMillis();
        
    }
    
    public TicTacToeModel() {
        this(3, 3);
    }
    
    /**
     * Déplace la pièce à la position (l, c) si possible (pour le joueur)
     * @param l ligne de la pièce à déplacer
     * @param c colonne de la pièce à déplacer
     * @requires   l &gt;= 0 et l inferieure à  getLignes() et c &gt;= 0 et c inferieur à getColonnes()
     * @ensures   le nombre de coups augmente de 1 si le joueur joue un coup
     * @ensures si joueurActuel == 1, il devient 2, sinon il devient 1.
     */
    public void jouerCoup(int l, int c) {
        if(grille[l][c] == CASE_VIDE && !estFini())
        {
            grille[l][c] = joueurActuel;
            nbCoups++;
            if(joueurActuel==1)
            {
                joueurActuel = 2;
            }else{ joueurActuel = 1;}
        }
        
        fireChangement();
    }
    

    /**
     * Verifier l'id du joueur qui a gagné
     * On capture le temps de fin du mélange comme le temps de début d'un nouveau jeu
     * pour calculer la durée de résolution du jeu
     */
    public int verifierGagnant()
    {
        //Logique de la ligne qui gagne en mode les contenus ont aligné donc on gange
        for(int i = 0; i<lignes; i++)
        {
            int premier = grille[i][0];
            if(premier==0) continue;
            boolean ligneGagnante = true;
            for(int j=1;j<colonnes;j++)
            {
                if(grille[i][j] != premier)
                {
                    ligneGagnante = false;break;
                }
            }
            if(ligneGagnante) return premier;
        }
        //LOgique si c'est plutot sur les colonnes que les valeurs sont les mm donc on gagne
        for(int j = 0; j<colonnes; j++)
        {
            int premier2 = grille[0][j];
            if(premier2==0) continue;
            boolean colonneGagnante2 = true;
            for(int i=1;i<lignes;i++)
            {
                if(grille[i][j] != premier2)
                {
                    colonneGagnante2 = false;break;
                }
            }
            if(colonneGagnante2) return premier2;
        }

        //Logique de gagner en mode diagonale montante en mode Haut gauche vers Bas Droit si les contenus des diagonales sont identiques
        int premierDiag1 = grille[0][0];
        if(premierDiag1 != 0)
        {
            boolean diag1Gagnante = true;
            for(int i =0;i<lignes;i++)
            {
                if(grille[i][i] != premierDiag1)
                { 
                    diag1Gagnante = false;
                    break;
                }
            }
            if(diag1Gagnante) return premierDiag1;
        }

        //Logique de gagner en mode diagonale montante en mode Bas Gauche vers Haut Droit si les contenus des diagonales sont identiques
        int premierDiag2 = grille[lignes-1][0];
        if(premierDiag2 != 0)
        {
            boolean diag2Gagnante = true;
            for(int i =0;i<lignes;i++)
            {
                if(grille[lignes-1-i][i] != premierDiag2)
                { 
                    diag2Gagnante = false;
                    break;
                }
            }
            if(diag2Gagnante) return premierDiag2;
        }
        return 0;
    }
   
   /**
    * le match est nul si le nombre de coups est atteint et il n'y a pas de gagnant
    */
   public boolean estMatchNul()
   {
    return nbCoups== (lignes*colonnes) && verifierGagnant()==0;
   }
    
    /**
     * Vérifie si le tictactoe est résolu
     * C'est fini si quelqu'un a gagné ou c'est un match nul
     * @return  1 si le jeu est fini, 0 sinon
     */
    public boolean estFini() {
        return verifierGagnant()!=0 || estMatchNul();
    }
    /**
     * Calculer le temps écoulé pour finir
     * on prend le temps auquel le jeu est résolu - temps de début du jeu
     * @return le temps de résolution du jeu
     */
    public long getTempsEcoule() {
        if (estFini()) {
            return (System.currentTimeMillis() - tempsDebut) / 1000; 
        }
        return (System.currentTimeMillis() - tempsDebut) / 1000;
    }
   
   /**
    * rejouer la partie si on clique sur restart
    */
    public void rejouer() {
        this.grille = new int[lignes][colonnes]; // On vide la grille
        this.nbCoups = 0;
        this.joueurActuel = 1; // X recommence
        fireChangement(); // On prévient la vue qu'il faut tout effacer
    }
    
    /**
     * On veut returner la valeur de la grille
     * @param l ligne
     * @param c colonne
     * @return un entier si vrai, -1 sinon
     */
    public int getValeur(int l, int c) {
        if (l >= 0 && l < lignes && c >= 0 && c < colonnes) {
            return grille[l][c];
        }
        return -1;
    }
    
    public int getLignes() {
        return lignes;
    }
    
    public int getColonnes() {
        return colonnes;
    }
    public int getJoueurActuel()
    {
        return joueurActuel;
    }
    
    public int getNbCoups() {
        return nbCoups;
    }
    
    public void resetNbCoups() {
        this.nbCoups = 0;
        fireChangement();
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lignes; i++) {
            for (int j = 0; j < colonnes; j++) {
                if (grille[i][j] == CASE_VIDE) {
                    sb.append("   ");
                } else {
                    sb.append(String.format("%2d ", grille[i][j]));
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

}