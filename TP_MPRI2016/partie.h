#ifndef PARTIE_H_INCLUDED
#define PARTIE_H_INCLUDED

#include <stdio.h>

#include "global.h"
#include "types.h"

// Affiche le plateau de jeu pour un état donné
void afficheJeu(Etat* etat) {

	int i,j;
	printf("   |");
	for (j=0; j<LARGEUR_PLATEAU; j++) {
		printf(" %d |", j);
	}
	printf("\n");
	printf("--------------------------------");
	printf("\n");

	for(i=0; i<HAUTEUR_PLATEAU; i++) {
		printf(" %d |", i);
		for (j=0; j<LARGEUR_PLATEAU; j++) {
			printf(" %c |", etat->plateau[j][i]);
		}
		printf("\n");
		printf("--------------------------------");
		printf("\n");
	}

}

// Modifier l'état en jouant un coup 
// retourne 0 si le coup n'est pas possible
int jouerCoup(Etat* etat, Coup* coup) {

	if (etat->plateau[coup->colonne][0] != ' ') {
		return 0;
	} else {

		int dispo;    
		for(dispo=HAUTEUR_PLATEAU-1; etat->plateau[coup->colonne][dispo] != ' '; dispo--);

		etat->plateau[coup->colonne][dispo] = etat->joueur ? 'O' : 'X';
		etat->joueur = AUTRE_JOUEUR(etat->joueur);

		return 1;
	}
}

// Test si l'état est un état terminal 
// et retourne NON, MATCHNUL, ORDI_GAGNE ou HUMAIN_GAGNE
FinDePartie testFin(Etat* etat) {

	int x,y,k,n = 0;
	for (x=0; x<LARGEUR_PLATEAU; x++) {
		for (y=0; y<HAUTEUR_PLATEAU; y++) {
			if (etat->plateau[x][y] != ' ') {
				n++;
				if (x < LARGEUR_PLATEAU - 3 || y < HAUTEUR_PLATEAU - 3) {

					// Lignes
					for (k = 0;
							k < 4
							&& x + k < LARGEUR_PLATEAU
							&& x + k >= 0
							&& etat->plateau[x+k][y] == etat->plateau[x][y];
							k++);
					if (k==4)
						return etat->plateau[x][y] == 'X'? ORDI_GAGNE : HUMAIN_GAGNE;

					// Colonnes
					for (k = 0;
							k < 4
							&& y + k < HAUTEUR_PLATEAU
							&& y + k >= 0
							&& etat->plateau[x][y+k] == etat->plateau[x][y];
							k++);
					if (k==4) 
						return etat->plateau[x][y] == 'X' ? ORDI_GAGNE : HUMAIN_GAGNE;

					// Diagonales bas-droite
					for (k = 0;
							k < 4
							&& x + k < LARGEUR_PLATEAU
							&& y + k < HAUTEUR_PLATEAU
							&& x + k >= 0
							&& y + k >= 0
							&& etat->plateau[x+k][y+k] == etat->plateau[x][y];
							k++);
					if (k==4) 
						return etat->plateau[x][y] == 'X' ? ORDI_GAGNE : HUMAIN_GAGNE;

					// Diagonale haut-droite	 
					for (k = 0;
							k < 4
							&& x + k < LARGEUR_PLATEAU
							&& y - k < HAUTEUR_PLATEAU
							&& x + k >= 0
							&& y - k >= 0
							&& etat->plateau[x+k][y-k] == etat->plateau[x][y];
							k++);
					if (k==4) 
						return etat->plateau[x][y] == 'X' ? ORDI_GAGNE : HUMAIN_GAGNE;

				}
			}
		}
	}

	if(n==HAUTEUR_PLATEAU*LARGEUR_PLATEAU) return MATCHNUL;

	return NON;
}

#endif
