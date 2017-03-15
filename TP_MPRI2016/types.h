#ifndef TYPES_H_INCLUDED
#define TYPES_H_INCLUDED

#include "global.h"

// Critères de fin de partie
typedef enum {NON, MATCHNUL, ORDI_GAGNE, HUMAIN_GAGNE } FinDePartie;

// Definition du type Etat (état/position du jeu)
typedef struct EtatSt {

	int joueur; // à qui de jouer ?
	char plateau[LARGEUR_PLATEAU][HAUTEUR_PLATEAU];

} Etat;

// Definition du type Coup
typedef struct {
	int colonne;

} Coup;

// Definition du type Noeud 
typedef struct NoeudSt {

	int joueur; // joueur qui a joué pour arriver ici
	Coup* coup;   // coup joué par ce joueur pour arriver ici

	Etat* etat; // etat du jeu

	struct NoeudSt* parent; 
	struct NoeudSt* enfants[LARGEUR_MAX]; // liste d'enfants : chaque enfant correspond à un coup possible
	int nb_enfants;	// nb d'enfants présents dans la liste

	// POUR MCTS:
	float moy_recompense;
	int nb_simus;

} Noeud;


#endif
