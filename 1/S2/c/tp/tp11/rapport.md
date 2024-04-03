# Le Taquin

## Objectif

#### Le jeu de taquin est un puzzle classique qui consiste à déplacer des tuiles dans un espace limité jusqu'à atteindre une configuration spécifique. Ce rapport présente mon implémentation du jeu de taquin, réalisée en langage de programmation C et avec l'utilisation de la bibliothèque graphique MLV pour l'interface utilisateur.

## Utilisation

Pour utiliser ce programme, une fois la compilations des sources effectuée, il vous faudra donner en argument votre
image, comme par exemple, attention votre image devra etre taille 512x512:

```shell
./taquin data/discord-incon.png
```

## Structure du Projet :

#### Le projet se compose de plusieurs fichiers sources, principalement board.c, display.c, et main.c, chacun ayant un rôle spécifique dans la mise en œuvre du jeu.

#### board.c : Contient la logique de gestion du plateau de jeu, y compris la création du plateau, la manipulation des tuiles, et la vérification des conditions de victoire.
#### display.c : Gère l'affichage du jeu dans le terminal et via l'interface graphique, utilisant les fonctionnalités fournies par MLV pour dessiner les tuiles et actualiser l'écran.

#### main.c : Le point d'entrée du programme, où le jeu est initialisé, et la boucle principale est exécutée, gérant les entrées utilisateur et la progression du jeu.

## Fonctionnalités Principales :

#### Initialisation et Affichage du Plateau : Le plateau de jeu est initialisé avec une image découpée en tuiles, chaque tuile étant ### associée à une position initiale. Le plateau est affiché à l'écran, avec une tuile manquante pour permettre les déplacements.

#### Déplacements des Tuiles : Les tuiles peuvent être déplacées en cliquant sur celles adjacentes à l'espace vide, permettant ainsi de ### mélanger et de tenter de résoudre le puzzle.

#### Aide pour le joueur : En cas de difficulté, le joueur à la possibilité d'appuyer sur "h" durant la partie pour l'aider à voir plus clair durant un court instant (1 seconde). 

#### Vérification de la Victoire : Le jeu vérifie si les tuiles sont dans l'ordre correct après chaque mouvement, permettant de déterminer ### si le joueur a réussi à résoudre le puzzle.