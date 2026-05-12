# 🌍 Logique du TicTacToe Africain (Variante de Déplacement)

Ce document détaille l'algorithme et les règles implémentés dans le moteur du jeu. Contrairement au TicTacToe classique, ce jeu se divise en deux phases distinctes.

## 1. Initialisation
- **Plateau :** Grille carrée de 3x3.
- **Pions :** Chaque joueur dispose de **3 pions** au total.
- **État initial :** Le plateau est vide.

## 2. Phase 1 : La Pose (Placement)
Tant que les deux joueurs n'ont pas placé leurs 3 pions respectifs :
- Le joueur actif clique sur une **case vide**.
- Le pion est posé définitivement pour cette phase.
- Le tour passe à l'adversaire.
- *Note :* Il est techniquement possible de gagner dès cette phase si un alignement est créé.

## 3. Phase 2 : Le Mouvement (Glissement)
Une fois que les 6 pions (3 par joueur) sont sur le plateau, le jeu bascule en mode "Mouvement". Le clic change de comportement :

### A. Sélection (Premier Clic)
- Le joueur doit cliquer sur un de **ses propres pions**.
- Le modèle enregistre les coordonnées du pion (`ligSel`, `colSel`).
- La vue met en évidence le pion sélectionné (ex: changement de couleur).

### B. Action (Deuxième Clic)
Après avoir sélectionné un pion, le joueur choisit une destination :
1. **Déplacement :** Si la case est **vide** ET **adjacente** (distance de 1 case max, diagonales incluses) :
   - Le pion quitte l'ancienne case et occupe la nouvelle.
   - La sélection est réinitialisée.
   - Vérification de la victoire.
   - Changement de tour.
2. **Changement :** Si le joueur clique sur un autre de ses pions, la sélection se déplace sur ce nouveau pion.
3. **Annulation :** Si le joueur clique sur le pion déjà sélectionné, la sélection est annulée.

## 4. Conditions de Victoire
- **Alignement :** 3 pions du même joueur alignés horizontalement, verticalement ou diagonalement.
- **Blocage (Optionnel) :** Si un joueur ne peut plus bouger aucun de ses pions (tous coincés par l'adversaire ou les bords), il a perdu.

---
*Logique conçue pour une implémentation robuste en architecture MVC.*