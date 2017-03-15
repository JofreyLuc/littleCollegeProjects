# Projet MPRI M1

## Structure du projet

Le projet est réalisé en C et reprend le fichier jeu.c de base. Cependant, le code à été réparti en plusieurs fichiers .h afin d'en faciliter la lecture. Un Makefile est également présent.

## Rapport

### Question 1

cf. code.

### Question 2

Quelque soit le critère appliqué (max ou robuste), l'ordinateur est capable de gagner à tous les coups" avec un temps de calcul de 1 seconde.

Plus en détail, une stratégie garantissant la victoire consiste à placer le premier pion dans la colonne centrale (et à jouer les coups suivant correctement, bien sur...). En ayant connaissance de cette stratégie, on constate plusieurs choses :

- Si l'ordinateur commence la partie, il va systématiquement placer le pion dans la colonne centrale (preuve qu'il "sait jouer").
- Si on place le pion dans la colonne centrale, il est possible, en théorie, de vaincre l'ordinateur. Cependant, cela demanderait de jouer parfaitement, ce qui n'est hélas pas le cas. L'ordinateur en revanche, en est visiblement capabable, même avec un temps de calcul d'une seconde.

Il serait possible de pousser l'étude plus loin, car le temps de calcul n'est pas le seul facteur clé, la puissance de calcul disponible durant ce temps l'est aussi. Ainsi, il faudrait pouvoir laisser un temps de calcul de moins d'une seconde ou maîtriser la quantité de calcul autorisée durant ce temps.

### Question 3

Par rapport à la méthode de la marche aléatoire, cette nouvelle version :

1. Fait en général moins de simulations. Vu qu'il est plus rapide de choisir un coup au hasard que de vérifier les conditions de victoire des coups possibles, on ne peut pas simuler autant de fois dans le même laps de temps. Cette différence tend néanmoins à se réduire au fil de la partie : en effet, lorsque par exemple un seul coup suffira à terminer la partie, la simulation nécéssairement celui-ci puis s'arrêtera (au contraire de la marche aléatoire qui aurait pu continuer à développer l'arbre en houant un coup différent). De ce fait, plus un coup garantissant la victoire d'un camp est proche, plus on effectuera de simulaions.

2. Estime en général plus haut les chances de victoire de l'ordinateur. En effet, elle permet "d'éliminer" tous les cas où la marche aléatoire laisse une possibilité facile au joueur de gagner ou rate une possibilité pour l'ordinateur de gagner. Cela permet à l'algorithme de perdre moins de temps à explorer l'arbre là où c'est inutile (i.e. les noeuds qui ne se produiront probablement pas puisqu'un des participants aura saisi sa chance de gagner avant), et donc de passer plus de temps à affiner ses choix.

### Question 4

L'ajout de l'option de compilation -O3 permet d'activer de nombreuses optimisations à la compilation. Ces dernières permettent théoriquement de rendre le code plus rapide car certains calculs inutiles (décidé par le compilateur) seront ignorés. Dans la pratique, il n'est pas possible de voir la différence du le jeu car il est capable de battre le joueur en 1 seconde de temps de calcul.

En revanche, on peut voir l'impact de -O3 sur d'autres exemples, notamment :

```
#include <limits.h>

int main(void) {

	int i = 0;
	int j = INT_MAX;

	for(i=0; i<j; i++);

	return 0;
}
```

Si ce bout de code est compilé sans -03, il prendra un certain temps (environ 6 secondes sur la machine qui a servi à tester) tandis qu'avec, il se terminera instantanément sans aucun temps de calcul. On peut donc en déduire que le compilateur a évité les calculs inutiles (ici, les variables ne sont pas utilisées, la boucle est donc totalement inutile).

En revanche, sur un exemple avec des affichages (permet l'utilisation des variables pour que le compilateur ne les considère pas comme inutiles) :

```
#include <limits.h>
#include <stdio.h>

	int main(void) {

		int i = 0;
		int j = INT_MAX/10;
		int k = 0;

		for(i=0; i<j; i++) {
			if((k = (float)i/(float)j*100)%10 == 0) {
				printf("\r%d%%", k);
				fflush(stdout);
			}
		}

		return 0;
	}
```

l'utilisation de -O3 n'aura aucun impact, les temps d'affichages étant ici limitants.

### Question 5

Les critères max et robuste ne donnent pas vraiment de différences visibles (peut-être à cause de la puissance des pc de test), l'ordinateur joue toujours le coup "optimal" selon la situation (premier coup au milieu, etc.). Néanmoins, on a pu observer au cours du développement que le critère robuste était en général plus "tranché" que le critère max (la différence entre le nombre de simulations du coup optimal et celui des autre coups semblait plus importante que la différence entre les chances de victoire).

### Question 6

Si on applique l'algorithme minimax sans amélioration pour jouer le premier coup, on peut considérer qu'on aura besoin au pire d'évaluer tous les noeuds de l'arbre. On pourrait donc dire que 7*7*7*....\*7 (profondeur de l'arbre = nombre de coups dans une partie) = 7^42 est un bon majorant du nombre de noeuds qu'on devra évaluer.
