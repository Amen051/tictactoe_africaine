package view;

import model.TicTacToeModel;
import utils.EcouteurModele;
import controllers.ControllerTicTacToe;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Vu graphique
 */

public class ViewGraphique extends JFrame implements EcouteurModele {

    private TicTacToeModel modele;
    private JButton[][] boutons;
    private JLabel labelCoups;
    private JPanel cardPanel;
    private CardLayout cardLayout;

    // Nouveaux Modes pour le TicTacToe
    private boolean contreOrdinateur = false;
    private int difficulteIA = 0; // 0: Facile, 1: Normal, 2: Expert
    private int tailleGrille = 3; 
    private BufferedImage backgroundImage = null;

    /**
     *  Chemins des images selon notre structure de projet
    */ 
    private static final String[] CHEMINS_IMAGES = {
        "view/image1.jpg",
        "view/image2.jpg",
        "view/image3.jpg"
    };
    private static final String CHEMIN_BACKGROUND = "view/background.png";

    /**
     * Couleurs du thème
     */
    
    private static final Color COULEUR_PRIMAIRE    = new Color(52, 152, 219);
    private static final Color COULEUR_SECONDAIRE  = new Color(46, 204, 113);
    private static final Color COULEUR_ACCENT      = new Color(231, 76, 60);
    private static final Color COULEUR_SOMBRE      = new Color(44, 62, 80);
    private static final Color COULEUR_CLAIR       = new Color(236, 240, 241);
    private static final Color COULEUR_OR          = new Color(241, 196, 15);

    /**
     * Constructeur de la vue graphique
     *  Charger le fond
     * CardLayout pour naviguer entre les écrans
     * Panel de fond avec image
     */

    public ViewGraphique(TicTacToeModel modele) {
        this.modele = modele;
        modele.ajoutEcouteur(this);

        chargerImageFond();

        setTitle("🧩 Jeu de Morpion");
        setSize(600, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setOpaque(false);

        cardPanel.add(creerEcranAccueil(),       "accueil");
        cardPanel.add(creerEcranChoixMode(),     "choixMode");
        cardPanel.add(creerEcranChoixTaille(),   "choixTaille");
        cardPanel.add(creerEcranChoixDifficulte(),    "choixDifficulte");
        cardPanel.add(creerEcranJeu(),           "jeu");

        JPanel fondPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    Graphics2D g2d = (Graphics2D) g;
                    // Dessiner l'image de fond étirée
                    g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                    // Overlay semi-transparent
                    g2d.setColor(new Color(0, 0, 0, 100));
                    g2d.fillRect(0, 0, getWidth(), getHeight());
                } else {
                    // Dégradé de secours
                    Graphics2D g2d = (Graphics2D) g;
                    GradientPaint gp = new GradientPaint(
                        0, 0, new Color(44, 62, 80),
                        0, getHeight(), new Color(52, 73, 94)
                    );
                    g2d.setPaint(gp);
                    g2d.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        fondPanel.add(cardPanel, BorderLayout.CENTER);
        setContentPane(fondPanel);

        cardLayout.show(cardPanel, "accueil");
        setVisible(true);
    }
    /**
    * Chargement de l'image de fond
    * On cherche l'image dans le "classpath" (le dossier build ou src)
    */
    private void chargerImageFond() {
    try {
        java.net.URL url = getClass().getResource("/" + CHEMIN_BACKGROUND);
        if (url != null) {
            backgroundImage = ImageIO.read(url);
        }
    } catch (IOException e) {
        backgroundImage = null;
    }
}


    /**
     * ÉCRAN 1  ACCUEIL (selon notre CardLayout)
     */
    private JPanel creerEcranAccueil() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(10, 20, 10, 20);

        // Titre principal
        JLabel titre = new JLabel("🧩 Bienvenue au Jeu TicTacToe !", SwingConstants.CENTER);
        titre.setFont(new Font("Segoe UI Emoji", Font.BOLD, 30));
        titre.setForeground(Color.WHITE);
        gbc.insets = new Insets(40, 20, 5, 20);
        panel.add(titre, gbc);

        // Sous-titre
        JLabel sousTitre = new JLabel("Testez votre logique et votre patience !", SwingConstants.CENTER);
        sousTitre.setFont(new Font("Segoe UI", Font.ITALIC, 16));
        sousTitre.setForeground(new Color(189, 195, 199));
        gbc.insets = new Insets(0, 20, 40, 20);
        panel.add(sousTitre, gbc);

        // Icône décorative (puzzle animé)
        JLabel icone = new JLabel("🎮", SwingConstants.CENTER);
        icone.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 80));
        gbc.insets = new Insets(10, 20, 40, 20);
        panel.add(icone, gbc);

        // Bouton Jouer
        JButton btnJouer = creerBoutonStylise("  JOUER  ", COULEUR_SECONDAIRE, Color.WHITE, 22);
        btnJouer.addActionListener(e -> cardLayout.show(cardPanel, "choixMode"));
        gbc.insets = new Insets(10, 60, 10, 60);
        panel.add(btnJouer, gbc);

        // Bouton Quitter
        JButton btnQuitter = creerBoutonStylise("  QUITTER  ", COULEUR_ACCENT, Color.WHITE, 16);
        btnQuitter.addActionListener(e -> System.exit(0));
        gbc.insets = new Insets(5, 80, 40, 80);
        panel.add(btnQuitter, gbc);

        // Version
        JLabel version = new JLabel("v1.0 - Projet MVC Java Swing", SwingConstants.CENTER);
        version.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        version.setForeground(new Color(127, 140, 141));
        gbc.insets = new Insets(20, 20, 10, 20);
        panel.add(version, gbc);

        return panel;
    }

    /**
     * ÉCRAN 2  CHOIX DU MODE (Chiffres / Image)
     * Bouton Jouer avec Chiffres (gauche)
     * Bouton Jouer avec Image (droite)
     * Bouton Retour
     */
    private JPanel creerEcranChoixMode() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(10, 20, 10, 20);

        // Titre
        JLabel titre = new JLabel("Choisissez votre mode", SwingConstants.CENTER);
        titre.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titre.setForeground(Color.WHITE);
        gbc.insets = new Insets(50, 20, 10, 20);
        panel.add(titre, gbc);

        JLabel sousTitre = new JLabel("Comment voulez-vous jouer ?", SwingConstants.CENTER);
        sousTitre.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        sousTitre.setForeground(new Color(189, 195, 199));
        gbc.insets = new Insets(0, 20, 50, 20);
        panel.add(sousTitre, gbc);

        // Panel horizontal pour les deux boutons
        JPanel panelBoutons = new JPanel(new GridLayout(1, 2, 30, 0));
        panelBoutons.setOpaque(false);

       // Carte Gauche : Jouer avec un Ami
        JPanel carteAmi = creerCarteMode("👥", "Contre un Ami", "Affrontez-vous à deux\nsur le même écran", COULEUR_PRIMAIRE);
        carteAmi.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                contreOrdinateur = false;
                cardLayout.show(cardPanel, "choixTaille");
            }
        });

        // Carte Droite : Jouer contre l'IA
        JPanel carteIA = creerCarteMode("🤖", "Contre l'IA", "Défiez l'ordinateur\net ses 3 niveaux", COULEUR_OR);
        carteIA.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                contreOrdinateur = true;
                cardLayout.show(cardPanel, "choixTaille");
            }
        });

        panelBoutons.add(carteAmi);
        panelBoutons.add(carteIA);
        


        gbc.insets = new Insets(10, 40, 30, 40);
        panel.add(panelBoutons, gbc);

        // Bouton retour
        JButton btnRetour = creerBoutonStylise("← Retour", new Color(127, 140, 141), Color.WHITE, 14);
        btnRetour.addActionListener(e -> cardLayout.show(cardPanel, "accueil"));
        gbc.insets = new Insets(10, 200, 20, 200);
        panel.add(btnRetour, gbc);

        return panel;
    }

    private JPanel creerCarteMode(String emoji, String titre, String description, Color couleur) {
        JPanel carte = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(getBackground());
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2d.dispose();
            }
        };
        carte.setBackground(couleur);
        carte.setOpaque(false);
        carte.setBorder(new EmptyBorder(20, 15, 20, 15));
        carte.setPreferredSize(new Dimension(180, 220));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel emojiLabel = new JLabel(emoji, SwingConstants.CENTER);
        emojiLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 50));
        carte.add(emojiLabel, gbc);

        for (String ligne : titre.split("\n")) {
            JLabel l = new JLabel(ligne, SwingConstants.CENTER);
            l.setFont(new Font("Segoe UI", Font.BOLD, 16));
            l.setForeground(Color.WHITE);
            carte.add(l, gbc);
        }

        for (String ligne : description.split("\n")) {
            JLabel l = new JLabel(ligne, SwingConstants.CENTER);
            l.setFont(new Font("Segoe UI", Font.PLAIN, 11));
            l.setForeground(new Color(255, 255, 255, 200));
            carte.add(l, gbc);
        }

        return carte;
    }
    
    /**
     * ÉCRAN 3  CHOIX DE LA TAILLE DE GRILLE
     */
    private JPanel creerEcranChoixTaille() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        JLabel titre = new JLabel("Choisissez la taille", SwingConstants.CENTER);
        titre.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titre.setForeground(Color.WHITE);
        gbc.insets = new Insets(40, 20, 5, 20);
        panel.add(titre, gbc);

        JLabel sousTitre = new JLabel("De 2×2 (facile) à 8×8 (expert)", SwingConstants.CENTER);
        sousTitre.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        sousTitre.setForeground(new Color(189, 195, 199));
        gbc.insets = new Insets(0, 20, 30, 20);
        panel.add(sousTitre, gbc);

        // Grille de boutons de taille
        JPanel panelTailles = new JPanel(new GridLayout(2, 4, 15, 15));
        panelTailles.setOpaque(false);

        int[] tailles = {2, 3, 4, 5, 6, 7, 8};
        String[] labels = {"2×2\n😊 Facile", "3×3\n🙂 Normal", "4×4\n😐 Moyen", "5×5\n🤔 Difficile",
                           "6×6\n😰 Dur", "7×7\n😱 Très Dur", "8×8\n💀 Expert"};

        for (int i = 0; i < tailles.length; i++) {
            final int t = tailles[i];
            JPanel btnPanel = new JPanel(new GridBagLayout());
            btnPanel.setOpaque(false);

            JButton btn = new JButton("<html><center>" + labels[i].replace("\n","<br>") + "</center></html>");
            btn.setFont(new Font("Segoe UI Emoji", Font.BOLD, 13));
            btn.setPreferredSize(new Dimension(100, 75));
            btn.setBackground(COULEUR_SOMBRE);
            btn.setForeground(Color.WHITE);
            btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COULEUR_PRIMAIRE, 2, true),
                new EmptyBorder(5, 5, 5, 5)
            ));
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btn.setFocusPainted(false);
            btn.addActionListener(e -> {
                tailleGrille = t;
                // Recréer le modèle avec la bonne taille
                modele = new TicTacToeModel(t, t);
                modele.ajoutEcouteur(ViewGraphique.this);
               if (contreOrdinateur) {
                    cardLayout.show(cardPanel, "choixDifficulte"); // Va à l'écran de l'IA
                } else {
                    lancerJeu(); // Lance direct pour jouer avec un ami
                }
            });
            btn.addMouseListener(new MouseAdapter() {
                @Override public void mouseEntered(MouseEvent e) { btn.setBackground(COULEUR_PRIMAIRE); }
                @Override public void mouseExited(MouseEvent e) { btn.setBackground(COULEUR_SOMBRE); }
            });
            panelTailles.add(btn);
        }
        // Case vide pour compléter la grille 2x4
        JPanel vide = new JPanel();
        vide.setOpaque(false);
        panelTailles.add(vide);

        gbc.insets = new Insets(10, 40, 30, 40);
        panel.add(panelTailles, gbc);

        JButton btnRetour = creerBoutonStylise("← Retour", new Color(127, 140, 141), Color.WHITE, 14);
        btnRetour.addActionListener(e -> cardLayout.show(cardPanel, "choixMode"));
        gbc.insets = new Insets(10, 200, 20, 200);
        panel.add(btnRetour, gbc);

        return panel;
    }

    /**
     * ÉCRAN 4  CHOIX DE L'IA
     */
    private JPanel creerEcranChoixDifficulte() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        JLabel titre = new JLabel("Niveau de l'IA", SwingConstants.CENTER);
        titre.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titre.setForeground(Color.WHITE);
        gbc.insets = new Insets(40, 20, 5, 20);
        panel.add(titre, gbc);

        // IL MANQUAIT CE PANEL CI-DESSOUS
        JPanel panelDifficultes = new JPanel(new GridLayout(1, 3, 20, 0));
        panelDifficultes.setOpaque(false);

        String[] noms = {"👶 Facile", "🧠 Normal", "💀 Expert"};
        String[] desc = {"Coup aléatoire", "IA maligne", "Imbattable !"};
        Color[] couleurs = {COULEUR_SECONDAIRE, COULEUR_PRIMAIRE, COULEUR_ACCENT};

        for (int i = 0; i < 3; i++) {
            final int niveau = i;
            JPanel carte = creerCarteMode(noms[i].split(" ")[0], noms[i], desc[i], couleurs[i]);
            carte.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    difficulteIA = niveau;
                    lancerJeu();
                }
            });
            panelDifficultes.add(carte);
        }
        
        gbc.insets = new Insets(10, 30, 30, 30);
        panel.add(panelDifficultes, gbc); // On ajoute le panel au menu
        return panel;
    }

    /**
     *  ÉCRAN 5  JEU
     */
    private JPanel creerEcranJeu() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        return panel;
    }

    private void lancerJeu() {
       int lignes = modele.getLignes();
        int colonnes = modele.getColonnes();
        boutons = new JButton[lignes][colonnes];

        JPanel panelJeu = (JPanel) cardPanel.getComponent(4);
        panelJeu.removeAll();

        // On crée le contrôleur TicTacToe
        ControllerTicTacToe controller = new ControllerTicTacToe(modele);

        JPanel panelGrille = new JPanel(new GridLayout(lignes, colonnes, 5, 5));
        panelGrille.setOpaque(false);

        for (int i = 0; i < lignes; i++) {
            for (int j = 0; j < colonnes; j++) {
                JButton b = new JButton("");
                b.setFont(new Font("Arial", Font.BOLD, 50));
                b.setFocusPainted(false);
                b.setActionCommand(i + "," + j);
                b.addActionListener(controller); // Le contrôleur gère le clic
                
                boutons[i][j] = b;
                panelGrille.add(b);
            }
        }
        
        panelJeu.add(panelGrille, BorderLayout.CENTER);

        // Panneau bas
        JPanel panelBas = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelBas.setOpaque(false);

        labelCoups = new JLabel("Coups : 0");
        labelCoups.setFont(new Font("Segoe UI", Font.BOLD, 16));
        labelCoups.setForeground(COULEUR_OR);

        JButton btnMelanger = creerBoutonStylise("🔀 Recommencer", COULEUR_PRIMAIRE, Color.WHITE, 14);
        btnMelanger.setActionCommand("restart");
        btnMelanger.addActionListener(controller);

        JButton btnMenu = creerBoutonStylise("🏠 Menu", new Color(127, 140, 141), Color.WHITE, 14);
        btnMenu.addActionListener(e -> cardLayout.show(cardPanel, "accueil"));

        panelBas.add(labelCoups);
        panelBas.add(btnMelanger);
        panelBas.add(btnMenu);
        panelJeu.add(panelBas, BorderLayout.SOUTH);

        setFocusable(true);
        this.requestFocusInWindow(); // <--- INDISPENSABLE pour que les touches fonctionnent immédiatement

        // Rafraîchir les images une fois que la grille est réellement affichée (taille réelle des boutons)
        panelGrille.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                afficherGrille();
            }
        });

        cardLayout.show(cardPanel, "jeu");
        panelJeu.revalidate();
        panelJeu.repaint();

        afficherGrille();
        modele.rejouer();
    }

    /**
     * MISE À JOUR DE L'AFFICHAGE
     * S'affiche quand le puzzle est Résolu
     */
   @Override
    public void modeleMisAJour(Object source) {
        afficherGrille();
        if (modele.estFini()) {
            int gagnant = modele.verifierGagnant();
            String texte;
            if (gagnant == 0) texte = "🤝 Match nul !";
            else texte = "🎉 Victoire du joueur " + (gagnant == 1 ? "X" : "O") + " !";

            String message = texte + "\n"
                    + "⏱ Temps de jeu : " + modele.getTempsEcoule() + " secondes !";

            JOptionPane.showMessageDialog(this, message, "Fin de partie", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    

    /**
     * Méthode afficher grille qui fait le design de la grille et l'affiche
     */
    private void afficherGrille() {
        if (boutons == null) return;
        for (int i = 0; i < modele.getLignes(); i++) {
            for (int j = 0; j < modele.getColonnes(); j++) {
                int v = modele.getValeur(i, j);
                if (v == 1) {
                    boutons[i][j].setText("X");
                    boutons[i][j].setForeground(COULEUR_ACCENT);
                } else if (v == 2) {
                    boutons[i][j].setText("O");
                    boutons[i][j].setForeground(COULEUR_PRIMAIRE);
                } else {
                    boutons[i][j].setText("");
                }
            }
        }
        if (labelCoups != null) {
            labelCoups.setText("Coups : " + modele.getNbCoups());
        }
    }


    /**
     * UTILITAIRES
     */
    private JButton creerBoutonStylise(String texte, Color fond, Color texteCouleur, int fontSize) {
        JButton btn = new JButton(texte) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(getBackground());
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g2d.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(new Font("Segoe UI Emoji", Font.BOLD, fontSize));
        btn.setBackground(fond);
        btn.setForeground(texteCouleur);
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setBorder(new EmptyBorder(10, 25, 10, 25));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    /**
     * Pour charger les images quand on choisit le jeu avec les images
     */
    private BufferedImage chargerImage(String chemin) {
        try {
            java.net.URL url = getClass().getResource("/" + chemin);
            if (url != null) {
                return ImageIO.read(url);
            }
        } catch (IOException e) {
        // Silencieux
        }
        return null;
    }

    /**
     * On découpe en fragments l'image choisi selon la taille du puzzle que l'utilisateur a choisi
     */
    private BufferedImage[][] decouperImage(BufferedImage img, int lignes, int colonnes,
                                             int largeurTotale, int hauteurTotale) {
        Image imgScaled = img.getScaledInstance(largeurTotale, hauteurTotale, Image.SCALE_SMOOTH);
        BufferedImage imgBuf = new BufferedImage(largeurTotale, hauteurTotale, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = imgBuf.createGraphics();
        g2d.drawImage(imgScaled, 0, 0, null);
        g2d.dispose();

        int w = largeurTotale / colonnes;
        int h = hauteurTotale / lignes;
        BufferedImage[][] fragments = new BufferedImage[lignes][colonnes];

        for (int i = 0; i < lignes; i++) {
            for (int j = 0; j < colonnes; j++) {
                fragments[i][j] = imgBuf.getSubimage(j * w, i * h, w, h);
            }
        }
        return fragments;
    }
}
