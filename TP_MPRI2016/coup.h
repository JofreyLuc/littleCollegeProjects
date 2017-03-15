#ifndef COUP_H_INCLUDED
#define COUP_H_INCLUDED

#include <stdlib.h>
#include <stdio.h>

#include "global.h"
#include "types.h"

// Copie un coup (utile pour éviter de libérer des coups utiles)
Coup* copie_coup(Coup* src){
	Coup* coup = (Coup*)malloc(sizeof(Coup));
	coup->colonne = src->colonne;
	return coup;
}

// Nouveau coup 
// Exemple : Coup* nouveauCoup(int i, int j) {
Coup* nouveauCoup(int c) {
	Coup* coup = (Coup*)malloc(sizeof(Coup));

	coup->colonne = c;

	return coup;
}

// Demander à l'humain quel coup jouer 
Coup* demanderCoup(void) {

	int c;
	printf("\n Quelle colonne ? ");
	scanf("%d", &c);

	return nouveauCoup(c);
}

// Retourne une liste de coups possibles à partir d'un etat 
// (tableau de pointeurs de coups se terminant par NULL)
Coup** coups_possibles(Etat* etat) {
	Coup** coups = (Coup**)malloc((1+LARGEUR_MAX) * sizeof(Coup*));

	int k = 0;

	int c;
	for (c=0; c<LARGEUR_PLATEAU; c++){
		if (etat->plateau[c][0] == ' ') {
			coups[k] = nouveauCoup(c);
			k++;
		}
	}

	coups[k] = NULL;

	return coups;
}

// Retourne le nombre de coups possibles à partir d'un état
int nb_coups_possibles(Etat* e){
	int i, nb_coups = 0;
	for (i = 0; i<LARGEUR_PLATEAU; i++){ 
		if (e->plateau[i][0] == ' ') nb_coups++;
	}
	return nb_coups;
}

// Libère chaque coup d'une liste de coups, puis la liste elle-même
void free_coups(Coup ** coups){
	int i;
	for (i = 0; coups[i] != NULL; i++) free(coups[i]);
	free(coups);
}

#endif
