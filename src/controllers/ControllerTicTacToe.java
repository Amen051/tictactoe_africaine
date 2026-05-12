package controllers;

import java.awt.event.*;
import model.TicTacToeModel;
import view.ViewGraphique;

/**
 * Contrôleur pour la variante africaine (Pose + Glissement)
 */
public class ControllerTicTacToe implements ActionListener {

    private TicTacToeModel modele;
    private ViewGraphique vue;

    public ControllerTicTacToe(TicTacToeModel modele, ViewGraphique vue) {
        this.modele = modele;
        this.vue = vue;
    }

    
    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if (cmd.equals("restart")) {
            utils.GestionnaireSon.jouerSon("restart.wav");
            modele.rejouer();
            return;
        }

        String[] pos = cmd.split(",");
        int l = Integer.parseInt(pos[0]);
        int c = Integer.parseInt(pos[1]);

        // --- Logique pour le son ---
        int pionsAvant = modele.getNbPionsJoueur(modele.getJoueurActuel());
        int selAvant = modele.getLigSel();

        modele.jouerCoup(l, c);

        // On détermine quel son jouer après le coup
        if (modele.getNbPionsJoueur(modele.getJoueurActuel() == 1 ? 2 : 1) > pionsAvant) {
            // Si le nombre de pions a augmenté, c'est une POSE
            utils.GestionnaireSon.jouerSon("pose.wav");
        } 
        else if (selAvant != -1 && modele.getLigSel() == -1) {
            // Si on avait une sélection et qu'elle a disparu, c'est un MOUVEMENT
            utils.GestionnaireSon.jouerSon("glisse.wav");
        }
        else if (modele.getLigSel() != -1) {
            // Si on vient de sélectionner un pion
            utils.GestionnaireSon.jouerSon("select.wav");
        }
    }
}