package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import model.TicTacToeModel;

public class PlateauSwing extends JPanel {
    private TicTacToeModel modele;
    private JButton[][] boutons; // Pour les 9 points de jeu
    private static final int MARGE = 50; // Pour ne pas coller aux bords

    public PlateauSwing(TicTacToeModel modele, ActionListener controlleur) {
        this.modele = modele;
        this.setLayout(null); // On va positionner les boutons à la main

        int ligs = modele.getLignes();
        int cols = modele.getColonnes();
        boutons = new JButton[ligs][cols];

        // Création des 9 boutons de jeu
        for (int i = 0; i < ligs; i++) {
            for (int j = 0; j < cols; j++) {
                JButton btn = new JButton();
                btn.setActionCommand(i + "," + j); // Coordonnées
                btn.addActionListener(controlleur);
                btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
                
                // --- DESIGN DES BOUTONS ---
                // Tu voulais des pions, on va leur donner une forme ronde et les rendre plats
                btn.setFocusPainted(false);
                btn.setBorderPainted(false); // Pas de bordure classique
                btn.setBackground(new Color(0,0,0,0)); // Transparent par défaut
                
                boutons[i][j] = btn;
                this.add(btn);
            }
        }
    }

    /**
     * C'est ici qu'on dessine le plateau traditionnel (Rectangle, +, x)
     * Exactement comme sur ton image
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        // On lisse les lignes pour un rendu propre
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(new BasicStroke(3)); // Lignes un peu épaisses
        g2.setColor(Color.BLACK);

        int w = getWidth();
        int h = getHeight();

        // Calcul des dimensions utiles (Zone de jeu)
        int zoneW = w - (2 * MARGE);
        int zoneH = h - (2 * MARGE);

        // 1. Dessin du Rectangle extérieur
        g2.drawRect(MARGE, MARGE, zoneW, zoneH);

        // --- DESSIN DU RÉSEAU DE LIGNES DE MOUVEMENT ---

        // Les Médianes (+)
        // Verticale centrale
        g2.drawLine(w / 2, MARGE, w / 2, h - MARGE);
        // Horizontale centrale
        g2.drawLine(MARGE, h / 2, w - MARGE, h / 2);

        // Les Diagonales (x)
        g2.drawLine(MARGE, MARGE, w - MARGE, h - MARGE);
        g2.drawLine(w - MARGE, MARGE, MARGE, h - MARGE);
    }

    /**
     * Méthode appelée par la fenêtre principale pour redimensionner
     * les boutons et les placer aux intersections
     */
    public void mettreAJourPositions() {
        int w = getWidth();
        int h = getHeight();
        int taillePion = Math.min(w, h) / 7; // Taille relative

        // Positions des 3 colonnes et 3 lignes
        int[] posX = { MARGE, w / 2, w - MARGE };
        int[] posY = { MARGE, h / 2, h - MARGE };

        // Placement absolu
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                // On centre le bouton sur l'intersection
                int xCoord = posX[j] - (taillePion / 2);
                int yCoord = posY[i] - (taillePion / 2);
                boutons[i][j].setBounds(xCoord, yCoord, taillePion, taillePion);
            }
        }
        this.revalidate();
    }
}