#ifndef ETAT_H_INCLUDED
#define ETAT_H_INCLUDED

#include <stdlib.h>

#include "global.h"
#include "types.h"

// Copier un état 
Etat* copieEtat(Etat* src) {
	Etat* etat = (Etat*)malloc(sizeof(Etat));

	etat->joueur = src->joueur;	

	int i,j;	
	for (i=0; i<LARGEUR_PLATEAU; i++) {
		for (j=0; j<HAUTEUR_PLATEAU; j++) {
			etat->plateau[i][j] = src->plateau[i][j];
		}
	}

	return etat;
}

// Etat initial 
Etat* etat_initial(void) {
	Etat* etat = (Etat*)malloc(sizeof(Etat));

	int i,j;	
	for (i=0; i<LARGEUR_PLATEAU; i++) {
		for (j=0; j<HAUTEUR_PLATEAU; j++) {
			etat->plateau[i][j] = ' ';
		}
	}

	return etat;
}

#endif
