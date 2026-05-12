package view;

import model.TicTacToeModel;
import utils.EcouteurModele;
import controllers.ControllerTicTacToe;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ViewGraphique extends JFrame implements EcouteurModele {

    private TicTacToeModel modele;
    private JButton[][] boutons;
    private JLabel labelCoups;
    private JPanel cardPanel;
    private CardLayout cardLayout;

    private boolean contreOrdinateur = false;
    private int difficulteIA = 0; 
    private BufferedImage backgroundImage = null;

    private static final String CHEMIN_BACKGROUND = "view/background.png";

    private static final Color COULEUR_PRIMAIRE    = new Color(52, 152, 219); // Bleu
    private static final Color COULEUR_SECONDAIRE  = new Color(46, 204, 113); // Vert
    private static final Color COULEUR_ACCENT      = new Color(231, 76, 60);  // Rouge
    private static final Color COULEUR_SOMBRE      = new Color(44, 62, 80);
    private static final Color COULEUR_OR          = new Color(241, 196, 15);

    public ViewGraphique(TicTacToeModel modele) {
        this.modele = modele;
        modele.ajoutEcouteur(this);

        chargerImageFond();

        setTitle("🌍 TicTacToe Africain - Variante Glissée");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setOpaque(false);

        cardPanel.add(creerEcranAccueil(),       "accueil");
        cardPanel.add(creerEcranChoixMode(),     "choixMode");
        cardPanel.add(creerEcranChoixDifficulte(),"choixDifficulte");
        cardPanel.add(creerEcranJeu(),           "jeu");

        // Panel de fond
        JPanel fondPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                    g.setColor(new Color(0, 0, 0, 150)); // Overlay plus sombre pour les lignes blanches
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        fondPanel.add(cardPanel, BorderLayout.CENTER);
        setContentPane(fondPanel);

        cardLayout.show(cardPanel, "accueil");
        setVisible(true);
    }

    private void chargerImageFond() {
        try {
            java.net.URL url = getClass().getResource("/" + CHEMIN_BACKGROUND);
            if (url != null) backgroundImage = ImageIO.read(url);
        } catch (IOException e) { backgroundImage = null; }
    }

    // --- ÉCRANS (Recyclés de ta version) ---

    private JPanel creerEcranAccueil() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        
        JLabel titre = new JLabel("🧩 TicTacToe Africain", SwingConstants.CENTER);
        titre.setFont(new Font("Segoe UI", Font.BOLD, 35));
        titre.setForeground(Color.WHITE);
        panel.add(titre, gbc);

        JButton btnJouer = creerBoutonStylise("  COMMENCER  ", COULEUR_SECONDAIRE, Color.WHITE, 22);
        btnJouer.addActionListener(e -> cardLayout.show(cardPanel, "choixMode"));
        gbc.insets = new Insets(30, 0, 10, 0);
        panel.add(btnJouer, gbc);

        return panel;
    }

    private JPanel creerEcranChoixMode() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        JPanel panelBoutons = new JPanel(new GridLayout(1, 2, 30, 0));
        panelBoutons.setOpaque(false);

        JPanel carteAmi = creerCarteMode("👥", "Contre un Ami", "Posez 3 pions puis glissez !", COULEUR_PRIMAIRE);
        carteAmi.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                contreOrdinateur = false;
                lancerJeu();
            }
        });

        JPanel carteIA = creerCarteMode("🤖", "Contre l'IA", "Défiez le moteur imbattable", COULEUR_OR);
        carteIA.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                contreOrdinateur = true;
                cardLayout.show(cardPanel, "choixDifficulte");
            }
        });

        panelBoutons.add(carteAmi);
        panelBoutons.add(carteIA);
        panel.add(panelBoutons, gbc);
        return panel;
    }

    private JPanel creerEcranChoixDifficulte() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        JPanel panelDiff = new JPanel(new GridLayout(1, 3, 20, 0));
        panelDiff.setOpaque(false);
        panelDiff.add(creerBoutonIA("👶", "Facile", 0));
        panelDiff.add(creerBoutonIA("🧠", "Normal", 1));
        panelDiff.add(creerBoutonIA("💀", "Expert", 2));

        panel.add(panelDiff, gbc);
        return panel;
    }

    private JPanel creerBoutonIA(String e, String n, int d) {
        JPanel p = creerCarteMode(e, n, "Niveau " + n, COULEUR_SOMBRE);
        p.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent ev) { difficulteIA = d; lancerJeu(); }
        });
        return p;
    }

    private JPanel creerEcranJeu() {
        return new JPanel(new BorderLayout(10, 10)) {{ setOpaque(false); }};
    }

    // --- LE COEUR DU JEU (LANCER JEU) ---

    private void lancerJeu() {
        boutons = new JButton[3][3];
        JPanel panelJeu = (JPanel) cardPanel.getComponent(3);
        panelJeu.removeAll();

        ControllerTicTacToe controller = new ControllerTicTacToe(modele, this);

        // LE PLATEAU DESSINÉ (Rectangle + Croix + Diagonales)
        JPanel plateauDesign = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setStroke(new BasicStroke(4));
                g2.setColor(Color.WHITE);

                int w = getWidth(), h = getHeight();
                int mX = 120; // Plus large sur les côtés
                int mY = 60;  // Plus serré en haut/bas

                g2.drawRect(mX, mY, w - 2*mX, h - 2*mY); 
                g2.drawLine(w/2, mY, w/2, h-mY);       
                g2.drawLine(mX, h/2, w-mX, h/2);       
                g2.drawLine(mX, mY, w-mX, h-mY);         
                g2.drawLine(w-mX, mY, mX, h-mY);        // x
            }
        };
        plateauDesign.setOpaque(false);

        // Positionnement des pions aux intersections
        plateauDesign.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int w = plateauDesign.getWidth(), h = plateauDesign.getHeight();
                int mX = 120; // Plus large sur les côtés
                int mY = 60;
                int size = 65;
                int[] cX = {mX, w/2, w-mX}, cY = {mY, h/2, h-mY};
                for (int i = 0; i < 3; i++)
                    for (int j = 0; j < 3; j++)
                        boutons[i][j].setBounds(cX[j]-size/2, cY[i]-size/2, size, size);
            }
        });

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                boutons[i][j] = creerBoutonPion(i, j, controller);
                plateauDesign.add(boutons[i][j]);
            }
        }

        panelJeu.add(plateauDesign, BorderLayout.CENTER);

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
        this.requestFocusInWindow(); 

        cardLayout.show(cardPanel, "jeu");
        panelJeu.revalidate();
        panelJeu.repaint();

        modele.rejouer();
    }

    private JButton creerBoutonPion(int l, int c, ControllerTicTacToe ctrl) {
        JButton b = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                int val = modele.getValeur(l, c);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Dessin du cercle (si pion présent)
                if (val != 0) {
                    g2.setColor(val == 2 ? COULEUR_PRIMAIRE : COULEUR_ACCENT);
                    g2.fillOval(5, 5, getWidth()-10, getHeight()-10);
                }

                // Halo de sélection (glissement)
                if (l == modele.getLigSel() && c == modele.getColSel()) {
                    g2.setColor(COULEUR_OR);
                    g2.setStroke(new BasicStroke(4));
                    g2.drawOval(2, 2, getWidth()-4, getHeight()-4);
                }
                g2.dispose();
            }
        };
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setActionCommand(l + "," + c);
        b.addActionListener(ctrl);
        return b;
    }

    @Override
    public void modeleMisAJour(Object source) {
        if (boutons != null) {
            for (JButton[] ligne : boutons) for (JButton b : ligne) b.repaint();
        }
        
        String txt = (modele.getJoueurActuel() == 2) ? "Bleu" : "Rouge";
        labelCoups.setText("Tour : " + txt);

        if (modele.estFini()) {
            int g = modele.verifierGagnant();
            utils.GestionnaireSon.jouerSon("win.wav");
            String texte = "🎉 Victoire du joueur " + (g == 2 ? "Bleu" : "Rouge") + " !";
            String message = texte + "\n"
                    + "⏱ Temps de jeu : " + modele.getTempsEcoule() + " secondes !";
            JOptionPane.showMessageDialog(this, message, "Fin de partie", JOptionPane.INFORMATION_MESSAGE);
            modele.rejouer();
        }
    }

    // --- UTILITAIRES DE CARTE (Recyclés) ---
    private JPanel creerCarteMode(String emoji, String titre, String desc, Color c) {
        JPanel carte = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                g.setColor(getBackground()); g.fillRoundRect(0,0,getWidth(),getHeight(),20,20);
            }
        };
        carte.setBackground(c); carte.setOpaque(false);
        carte.setPreferredSize(new Dimension(150, 200));
        carte.add(new JLabel(emoji, 0), BorderLayout.NORTH);
        carte.add(new JLabel("<html><center>"+titre+"<br><small>"+desc+"</small></center></html>", 0), BorderLayout.CENTER);
        return carte;
    }

    private JButton creerBoutonStylise(String t, Color f, Color tc, int s) {
        JButton b = new JButton(t);
        b.setBackground(f); b.setForeground(tc);
        b.setFont(new Font("Segoe UI", Font.BOLD, s));
        return b;
    }
}