# 🧩 Projet Jeu de Puzzle (Taquin) - Java Swing

Ce projet est une implémentation du célèbre jeu de puzzle (Taquin) en Java, utilisant une architecture **MVC** (Modèle-Vue-Contrôleur) et une interface graphique **Swing**.

## 👥 Équipe et Répartition
| Nom du Participant | Partie du code / Responsabilités |
| :--- | :--- |
| Mamadou Saliou Bah | [PuzzleModel(une partie) + AbsractModeleEcouteur+EcouteurModel+ModeleEcoutable  ] |
| ATTIOGBE Amen | [PuzzleModel, javadoc, fichier de compilation, Fichier Jar ] |
| Assia Lahdar | [Controlleur] + Rapport +  main.java
| Chaima Haddad | [VueGraphique(interface du jeux) + TestViewGraphique + Rapport + VueConsole + Readme ] |

## 🚀 Fonctionnalités
* **Grille personnalisable** (4x4 par défaut).
* **Mode Image** : Jouez avec des images découpées dynamiquement.
* **Mode Chiffres** : Mode classique avec des numéros.
* **Contrôles hybrides** : Jouez à la souris ou avec les touches du clavier (G/B/H/D ou flèches)
* **Système de score** : Compteur de coups et chronomètre intégrés.
* **Mélange automatique** : Bouton pour recommencer une partie.

## 🛠️ Compilation et Lancement
Nous avons automatisé la gestion du projet via un script Bash.

### Utilisation du script (Recommandé)
Pour compiler, générer la documentation et lancer le jeu d'un seul coup :
```bash
chmod +x compile.sh
./compile.sh
