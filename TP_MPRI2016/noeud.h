#ifndef NOEUD_H_INCLUDED
#define NOEUD_H_INCLUDED

#include <stdlib.h>

#include "global.h"
#include "types.h"
#include "etat.h"
#include "partie.h"

// Créer un nouveau noeud en jouant un coup à partir d'un parent 
// utiliser nouveauNoeud(NULL, NULL) pour créer la racine
Noeud* nouveauNoeud (Noeud* parent, Coup* coup ) {
	Noeud* noeud = (Noeud*)malloc(sizeof(Noeud));

	if (parent != NULL && coup != NULL) {
		noeud->etat = copieEtat(parent->etat);
		jouerCoup(noeud->etat, coup);
		noeud->coup = coup;
		noeud->joueur = AUTRE_JOUEUR(parent->joueur);
	}
	else {
		noeud->etat = NULL;
		noeud->coup = NULL;
		noeud->joueur = 0; 
	}
	noeud->parent = parent; 
	noeud->nb_enfants = 0; 

	// POUR MCTS:
	noeud->moy_recompense = 0;
	noeud->nb_simus = 0;

	return noeud;
}

// Ajouter un enfant à un parent en jouant un coup
// retourne le pointeur sur l'enfant ajouté
Noeud* ajouterEnfant(Noeud* parent, Coup* coup) {
	Noeud* enfant = nouveauNoeud(parent, coup);
	parent->enfants[parent->nb_enfants] = enfant;
	parent->nb_enfants++;
	return enfant;
}

// Libère un noeud
void freeNoeud(Noeud* noeud) {
	if (noeud->etat != NULL)
		free(noeud->etat);

	while (noeud->nb_enfants > 0) {
		freeNoeud(noeud->enfants[noeud->nb_enfants-1]);
		noeud->nb_enfants --;
	}
	if (noeud->coup != NULL)
		free(noeud->coup); 

	free(noeud);
}

#endif
