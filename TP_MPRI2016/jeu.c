#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <time.h>

#include "all.h"

int main(void) {

	Coup* coup;
	FinDePartie fin;

	// initialisation
	Etat* etat = etat_initial(); 

	// Choisir qui commence : 
	printf("Qui commence (0 : ordinateur, 1 : humain) ? ");
	scanf("%d", &(etat->joueur) );

	// boucle de jeu
	do {
		printf("\n");
		afficheJeu(etat);

		if (etat->joueur == 1) {
			// tour de l'humain

			do {
				coup = demanderCoup();
			} while (!jouerCoup(etat, coup));
		}
		else {
			// tour de l'Ordinateur
			ordijoue_mcts(etat, TEMPS);
		}

		fin = testFin(etat);
	}	while (fin == NON) ;

	printf("\n");
	afficheJeu(etat);

	if (fin == ORDI_GAGNE)
		printf("** L'ordinateur a gagné **\n");
	else if ( fin == MATCHNUL )
		printf(" Match nul !  \n");
	else
		printf("** BRAVO, l'ordinateur a perdu  **\n");
	return 0;
}
