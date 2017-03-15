#ifndef MCTS_H_INCLUDED
#define MCTS_H_INCLUDED

#include <math.h>
#include <time.h>
#include <float.h>

#include "types.h"
#include "etat.h"
#include "coup.h"
#include "noeud.h"
#include "partie.h"

// Renvoie la B-valeur du noeud i
float B(Noeud i) {

  if (i.nb_simus == 0) return FLT_MAX;

  float mu, c;

  mu = i.parent->joueur ? -i.moy_recompense : i.moy_recompense;
  c = sqrt(2);

  return mu+c*sqrt(log(i.parent->nb_simus)/i.nb_simus);
}

// Calcule et joue un coup de l'ordinateur avec MCTS-UCT
// en tempsmax secondes
void ordijoue_mcts(Etat* etat, int tempsmax) {

  clock_t tic, toc;
  tic = clock();
  int temps, tempsBase = 0;

  Coup** coups;
  Coup* meilleur_coup ;

  // Créer l'arbre de recherche
  Noeud* racine = nouveauNoeud(NULL, NULL);	
  racine->etat = copieEtat(etat); 

  // Créer les premiers noeuds:
  coups = coups_possibles(racine->etat); 
  int k = 0;
  while (coups[k] != NULL) {
    ajouterEnfant(racine, coups[k]);
    k++;
  }

  int iter = 0;

  do {

    // Sélection du noeud de plus grande B-valeur
    Noeud* choisi = racine;

    while (testFin(choisi->etat) == NON && choisi->nb_enfants == nb_coups_possibles(choisi->etat)) {
      float Bmax = B(*(choisi->enfants[0]));
      int i, indice = 0;
      float Benfant;
      for (i = 1; i < choisi->nb_enfants; i++) {
	Benfant = B(*(choisi->enfants[i]));
	if (Benfant > Bmax) {
	  Bmax = Benfant;
	  indice = i;
	}
      }
      choisi = choisi->enfants[indice];
    }

    // Création d'un tableau des coups qui créent des fils non-développés
    int i, j;
    int k = 0;
    int deja_joue = 0;
    int nb_enfants_possibles = nb_coups_possibles(choisi->etat) - choisi->nb_enfants;
    
    Coup** liste_coups_possibles = coups_possibles(choisi->etat);
    Coup** liste_coups_non_joues = malloc((nb_enfants_possibles+1)*sizeof(Coup*));
    liste_coups_non_joues[nb_enfants_possibles] = NULL;

    // On sélectionne les enfants non-développés parmi les enfants possibles
    for (j=0; j<nb_coups_possibles(choisi->etat); j++) {
      deja_joue = 0;
      for (i=0; i<choisi->nb_enfants; i++) {
	if (liste_coups_possibles[j]->colonne == choisi->enfants[i]->coup->colonne) {
	  deja_joue = 1;
	}
      }
      if (!deja_joue) {
	liste_coups_non_joues[k] = liste_coups_possibles[j];
	k++;
      }
    }

    // Si on n'est pas sur un cas terminal : Simulation de la fin de la partie
    if (nb_enfants_possibles != 0) {

      //Sélection aléatoire du fils à développer
      srand(time(NULL));
      int alea = rand()%nb_enfants_possibles;
      choisi = ajouterEnfant(choisi, copie_coup(liste_coups_non_joues[alea]));

      Coup** c_p;

      if (!SIMULATION) {
	//Simulation de la fin de la partie avec marche aléatoire
	while (testFin(choisi->etat) == NON){
	  c_p = coups_possibles(choisi->etat);
	  alea = rand()%nb_coups_possibles(choisi->etat);	
	  choisi = ajouterEnfant(choisi, copie_coup(c_p[alea]));
	  free_coups(c_p);
	}
      }	else {
	// Simulation de la fin de la partie avec choix biaisé
	while (testFin(choisi->etat) == NON){
	  c_p = coups_possibles(choisi->etat);
	  int enfant = 0, nb_cp = nb_coups_possibles(choisi->etat);
	  int gagne = 0; // Booléen

	  // On cherche un enfant gagnant
	  while (enfant < nb_cp && !gagne){
	    Noeud* candidat = nouveauNoeud(choisi, copie_coup(c_p[enfant]));
	    if (testFin(candidat->etat) != NON){
	      choisi = ajouterEnfant(choisi, copie_coup(c_p[enfant]));
	      gagne = 1;
	    }
	    freeNoeud(candidat);
	    enfant++;
	  }

	  // Si pas d'enfant gagnant : sélection aléatoire
	  if (!gagne) {
	    alea = rand()%nb_coups_possibles(choisi->etat);	
	    choisi = ajouterEnfant(choisi, copie_coup(c_p[alea]));
	  }
	  free_coups(c_p);
	}
      }
    }

    // Libération des listes temporaires
    free(liste_coups_non_joues);
    free_coups(liste_coups_possibles);

    // Mise à jour des B-valeurs jusqu'à la racine
    int r = (testFin(choisi->etat) == ORDI_GAGNE) ? 1 : 0;
    do {
      choisi->moy_recompense = ((choisi->nb_simus * choisi->moy_recompense) + r) / (choisi->nb_simus + 1);
      choisi->nb_simus += 1;
      choisi = choisi->parent;
    } while (choisi != NULL);

    // Gestion du temps et affichage
    toc = clock(); 
    temps = (int)(((double) (toc - tic)) / CLOCKS_PER_SEC);
    if (tempsBase != temps){
      tempsBase = temps;
      printf("%d... ", tempsBase);
      fflush(stdout);
    }
    iter++;
  } while (temps < tempsmax);

  //Sélection du meilleur coup selon le critère
  k = 1;
  int meilleur = 0;

  while (coups[k] != NULL) {
    if (CRITERE == 0) { // Max
      if (racine->enfants[k]->moy_recompense > racine->enfants[meilleur]->moy_recompense) meilleur = k;
    } else if (CRITERE == 1) { // Robuste
      if (racine->enfants[k]->nb_simus > racine->enfants[meilleur]->nb_simus) meilleur = k;
    }
    k++;
  }
  meilleur_coup = coups[meilleur];

  // Jouer le meilleur premier coup
  jouerCoup(etat, meilleur_coup);

  // Affichage des statistiques
  printf("\nsimulations : %d; victoire : %f%%\n", iter, racine->enfants[meilleur]->moy_recompense * 100);
  
  // Penser à libérer la mémoire :
  freeNoeud(racine);
  free(coups);
}

#endif
