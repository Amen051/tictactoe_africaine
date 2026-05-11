package utils;

import java.util.ArrayList;

public abstract class AbstractModeleEcoutable implements ModeleEcoutable {
    // La liste qui stocke toutes les vues abonnées (console, interface graphique, etc.)
    //private List<EcouteurModele> ecouteurs;
    private ArrayList<EcouteurModele> ecouteurs = new ArrayList<>();

    

    @Override
    public void ajoutEcouteur(EcouteurModele e) {
            ecouteurs.add(e);
    }

    @Override
    public void retraitEcouteur(EcouteurModele e) {
        ecouteurs.remove(e);
    }

    /**
     * La méthode à appeler à chaque fois qu'une case du taquin bouge !
    */ 
    public void fireChangement() {
        for (EcouteurModele e : ecouteurs) {
            e.modeleMisAJour(this);
        }
    }
}
