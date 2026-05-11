import model.TicTacToeModel;
import view.ViewConsole;
import controllers.ControllerTicTacToe;

/**
 * Pour la vue console
 * Créer le modèle (4x4 par défaut)
 * Créer le contrôleur (branché sur le modèle)
 * Créer la vue console et lancer le jeu
 */
public class Main {
    public static void main(String[] args) {
        TicTacToeModel modele = new TicTacToeModel(4, 4);
        ViewConsole vue = new ViewConsole(modele);
        vue.jouer();
    }
}