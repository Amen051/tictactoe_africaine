package utils;

import javax.sound.sampled.*;
import java.net.URL;

public class GestionnaireSon {
    public static void jouerSon(String nomFichier) {
        try {
            // On récupère l'URL du fichier dans le dossier sounds
            URL url = GestionnaireSon.class.getResource("/sounds/" + nomFichier);
            if (url == null) {
                System.err.println("Fichier non trouvé : " + nomFichier);
                return;
            }

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
            
            // On ferme proprement le stream une fois chargé dans le clip
            // Le clip continuera de jouer de façon autonome
        } catch (Exception e) {
            System.err.println("Erreur son : " + e.getMessage());
        }
    }
}