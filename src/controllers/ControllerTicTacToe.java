package controllers;

import java.awt.event.*;
import model.TicTacToeModel;
import utils.AbstractModeleEcoutable;
/**
 * Controlleur  implemente  ActionLIstener
 * ActionListener  pour la souris et les autres
 * A chaque fois on notifie les vues avec le fireChangement
 */

public class ControllerTicTacToe implements ActionListener {

    private TicTacToeModel modele;

    public ControllerTicTacToe(TicTacToeModel modele){
        this.modele = modele;
    }

  

    /**
     * Gestion des boutons(en mode les cases si je peux dire çà comme çà)
     * @param  e évènement , une action
     * ici si on déplace avec les souris on notifie les vues
     */
    @Override
    public void actionPerformed(ActionEvent e){

        String cmd = e.getActionCommand();

        // Si la commande est "restart"  on remet les coups à 0 et on nettoie les grilles
        if(cmd.equals("restart")){
            modele.rejouer();
            return;
        }

        // bouton d'une case de la grille
        String[] pos = cmd.split(",");

        int l = Integer.parseInt(pos[0]);
        int c = Integer.parseInt(pos[1]);

        modele.jouerCoup(l,c);
    }
}
