#ifndef GLOBAL_H_INCLUDED
#define GLOBAL_H_INCLUDED

/*
   Canvas pour algorithmes de jeux à 2 joueurs

   joueur 0 : humain
   joueur 1 : ordinateur

*/
#define LARGEUR_PLATEAU 7 //Largeur du plateau de jeu
#define HAUTEUR_PLATEAU 6 // Hauteur du plateau de jeu
#define LARGEUR_MAX 7 // nb max de fils pour un noeud (= nb max de coups possibles)
#define TEMPS 1 //temps de calcul pour un coup avec MCTS (en secondes)
#define CRITERE 1 // 0 : max, 1 : robuste
#define SIMULATION 1 // 0 : aléatoire, 1 : coup gagnant prioritaire

// Macros
#define AUTRE_JOUEUR(i) (1-(i))
#define min(a, b)       ((a) < (b) ? (a) : (b))
#define max(a, b)       ((a) < (b) ? (b) : (a))

#endif
