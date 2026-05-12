# 🌍 TicTacToe Africain (Variante Shisima / Glissée)

Une implémentation moderne en Java Swing du **TicTacToe traditionnel africain**, également connu sous le nom de **Shisima**. Contrairement au Morpion classique, ce jeu introduit une phase de déplacement qui transforme radicalement la stratégie.

## 📜 Règles du Jeu

Le jeu se déroule en deux étapes distinctes :

1.  **Phase de Pose** : Chaque joueur dispose de **3 pions**. À tour de rôle, ils placent leurs pions sur les intersections vides du plateau.
2.  **Phase de Glissement** : Une fois tous les pions posés, les joueurs doivent déplacer un de leurs pions vers un emplacement vide **adjacent** (en suivant les lignes du plateau).
3.  **Objectif** : Aligner ses 3 pions (horizontalement, verticalement ou en diagonale) en passant obligatoirement par le centre du plateau.

## 🛠 Architecture du Projet (MVC)

Le projet respecte scrupuleusement le patron de conception **Modèle-Vue-Contrôleur** pour assurer une séparation nette des responsabilités :

* **`model`** : Contient `TicTacToeModel`. Gère l'état de la grille, la logique de mouvement (adjacence), et la détection de victoire.
* **`view`** : Contient `ViewGraphique`. Gère l'interface Swing, le rendu du plateau rectangulaire, les menus via `CardLayout` et les retours visuels.
* **`controllers`** : Contient `ControllerTicTacToe`. Intercepte les actions utilisateur (clics) et coordonne les mises à jour entre le modèle et la vue.
* **`utils`** : Classes utilitaires pour la gestion de l'audio (`GestionnaireSon`) et les interfaces d'observation (`ModeleEcoutable`).

## 🎮 Fonctionnalités

* **Multijoueur Local** : Affrontez un ami sur le même écran.
* **Intelligence Artificielle** : Défiez l'ordinateur avec 3 niveaux de difficulté (Facile, Normal, Expert).
* **Interface Graphique Riche** : 
    * Menu d'accueil et écrans de sélection stylisés.
    * Plateau rectangulaire dynamique.
    * Système de surbrillance pour le pion sélectionné lors du glissement.
* **Immersion Sonore** : Effets audio distincts pour la pose, le déplacement, la sélection et la victoire.

## 🚀 Installation et Lancement

### Prérequis
* **Java JDK 17** ou version ultérieure.

### Compilation
Un script `compile.sh` est fourni pour automatiser la compilation, la génération du JAR et de la Javadoc :
```bash
chmod +x compile.sh
./compile.sh