import model.TicTacToeModel;
import view.ViewGraphique;

/**
 * Classe de lancement pour la version graphique (Menu + Jeu)
 */
public class TestViewGraphique {

    public static void main(String[] args) {
        // On crée un modèle par défaut (ex: 3x3). 
        // La taille pourra être rechangée via le menu de la vue.
        TicTacToeModel modele = new TicTacToeModel(3, 3);

        // On lance la vue (qui contient les menus et le CardLayout) comme un Thread c'estmieux histoire que l'écran ne freeze
        javax.swing.SwingUtilities.invokeLater(() -> {
            new ViewGraphique(modele);
        });
    }
}