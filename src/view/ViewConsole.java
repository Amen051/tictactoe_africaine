package view;

import java.util.Scanner;
import model.TicTacToeModel;
import utils.EcouteurModele;

public class ViewConsole implements EcouteurModele {
    private TicTacToeModel modele;
    private Scanner scanner;
    
    public ViewConsole(TicTacToeModel modele) {
        this.modele = modele;
        this.scanner = new Scanner(System.in);
        // S'enregistrer comme écouteur
        this.modele.ajoutEcouteur(this);
    }
    /**
     * Est appelé quand on résoud le jeu
     */
    @Override
    public void modeleMisAJour(Object source) {
        afficherGrille();
        
        if (modele.estFini()) {
            int g = modele.verifierGagnant();
            if (g != 0) {
                System.out.println("Le joueur " + (g == 1 ? "X (joueur 1)" : "O (joueur 2)") + " a gagné !");
            } else {
                System.out.println("Match nul ! Bien joué");
            }
            System.out.println("   BRAVO ! Vous avez gagné !");
            System.out.println("   Nombre total de Coups : " + modele.getNbCoups());
            System.out.println("   Temps de jeux : " + modele.getTempsEcoule() + " secondes ");
        }
    }
    /**
     * Méthode qui affiche la grille selon le nombre de lignes et de colonnes mis
     * Bien sûr ici c'est la vue console donc en terminal
     */
    public void afficherGrille() {
        int nbLignes = modele.getLignes();
        int nbCol = modele.getColonnes();

        // 1. Haut de la grille
        System.out.println("\n" + "    " + "┌───" + "┬───".repeat(nbCol - 1) + "┐");

        for (int i = 0; i < nbLignes; i++) {
            // 2. Contenu de la ligne (avec symbole X ou O)
            System.out.print(String.format(" %d  │", i)); // Numéro de ligne à gauche
            for (int j = 0; j < nbCol; j++) {
                int val = modele.getValeur(i, j);
                String symbole = " ";
                if (val == 1) symbole = "X";
                else if (val == 2) symbole = "O";
                
                System.out.print(String.format(" %s │", symbole));
            }
            System.out.println();

            // 3. Séparateur entre les lignes (sauf après la dernière)
            if (i < nbLignes - 1) {
                System.out.println("    ├───" + "┼───".repeat(nbCol - 1) + "┤");
            }
        }

        // 4. Bas de la grille
        System.out.println("    └───" + "┴───".repeat(nbCol - 1) + "┘");
        
        // 5. Numéros de colonnes en bas pour aider le joueur
        System.out.print("      ");
        for (int j = 0; j < nbCol; j++) {
            System.out.print(j + "   ");
        }
        System.out.println("\nCoups: " + modele.getNbCoups());
    }
    
    /**
     * Méthode qui permet de jouer
     * Elle appelle à chaque fois la méthode adéquate selon le caractère entré
     * h = deplacerHaut(), b= deplacerBas(), g= deplacerGauche, d= deplacerDroite()
     * m = melanger(), q=quitter
     */
    public void jouer() {
        System.out.println("=== JEU DE MORPION ===");
        System.out.println("Format : ligne,colonne (ex: 0,1)");
        System.out.println("[q] Quitter");

        boolean continuer = true;
        while (continuer && !modele.estFini()) {
            System.out.print("Action (l,c) > ");
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("q")) {
                continuer = false;
                System.out.println("Partie abandonnée.");
                break;
            }

            try {
                // On sépare l'entrée "0,1" en deux nombres
                String[] coord = input.split(",");
                int l = Integer.parseInt(coord[0]);
                int c = Integer.parseInt(coord[1]);
                
                modele.jouerCoup(l, c);
                
            } catch (Exception e) {
                System.out.println("Entrée invalide ! Tapez deux chiffres séparés par une virgule.");
            }
        }
    }
}