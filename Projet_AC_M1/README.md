## Partie 1

### Question 1

Montrer que C ci-dessous est satisfiable. Pour cela, trouver toutes les assignations qui satisfont :

    C = {[(x1, R)], [(x1, B),(x2, B)], [(x1, V ),(x2, R)]} 


Soit :


    O : x1 → V, x2 → V

    O : x1 → V, x2 → B

    N :  x1 → V, x2 → R


    O : x1 → B, x2 → V

    O : x1 → B, x2 → R

    N : x1 → B, x2 → B

     

    N : x1 → R, x2 → R

    N : x1 → R, x2 → V

    N : x1 → R, x2 → B


### Question 2

Montrer que C ci-dessous n’est pas satisfiable.

Supposons que C soit satisfiable donc qu'il existe une assignation φ telle que φ satisfait C.

Donc, selon φ, x1 -> a tel que a € {R, V, B} et selon les contraintes  [(x1, R),(x2, R)] , [(x1, V ),(x2, V )] , [(x1, B),(x2, B)] [(x1, R),(x3, R)] , [(x1, V ),(x3, V )] , [(x1, B),(x3, B)] [(x1, R),(x4, R)] , [(x1, V ),(x4, V )] , [(x1, B),(x4, B)], x2, x3 et x4 -/-> a

Donc selon φ, x2 -> b tel que b =/= a et b € {R, V, B} et selon les contraintes [(x2, R),(x3, R)] , [(x2, V ),(x3, V )] , [(x2, B),(x3, B)] [(x2, R),(x4, R)] , [(x2, V ),(x4, V )] , [(x2, B),(x4, B)] , x3 et x4 -/-> b

Donc selon φ, x3 -> c tel que c =/= a et c =/= b et c € {R, V, B} et selon les contraintes [(x3, R),(x4, R)] , [(x3, V ),(x4, V )] , [(x3, B),(x4, B)], x4 -/-> c

Donc x4 -> d tel que d =/= c et d =/= b et d =/= a et d € {R, V, B}
Or,  contradiction : parmi {R, V, B} il est impossible de trouver a, b, c et d tels qu'ils soient tous différents.
Donc C n'est pas satisfiable.

## Partie 2

### Question 3

Si on a deux contraintes [(x, a)] et [(x, b)], et a =/= b, alors on peut supprimer les contraintes de la forme [(x, a), (y, ·)] ou  [(x, b), (y, ·)] car ces contraintes sont ainsi satisfaites quel que soit  (y, ·) vu que x sera nécessairement assigné à c, et transformer les contraintes  [(x, c), (y, ·)] en [(y, ·)].

### Question 4

1) Eliminer toutes les contraintes de la forme [(x, R),(y, ·)] 

La contrainte [(x, R)] nous garantit que x ne sera pas assigné à R - donc toutes les contraintes de la forme [(x, R),(y, ·)] seront du coup satisfaites.

2) Pour tout couple de contraintes [(x, V ),(y, b)] et [(x, B),(z, c)], ajouter la contrainte [(y, b),(z, c)]

La contrainte  [(x, R)] nous garantit que x sera soit B soit V, on aura donc forcément pour un couple de contraintes [(x, V), (y, b)] et [(x, B),(z, c)] une des contraintes du couple satisfaite grâce à x. Ensuite, selon la valeur qu'on aura assignée à x, on aura besoin de valider soit (y, b), soit (z, c), on peut donc ajouter la contrainte [(y, b),(z, c)] sans changer la valeur du problème initial. 

### Question 5

a.
Si C n'est pas satisfiable aucune assignation ne satisfait C. Or C' = C U { c1, c2 } avec c1 et c2 étant deux contraintes unaires. Donc pour que C' soit satisfiable alors C et { c1, c2 } doivent être satisfiables (avec la même assignation). C n'étant pas satisfiable donc C' n'est pas satisfiable non plus.

b.
Soit c0 la première contrainte de C qui est donc de la forme : [(x, a), (y, b)]
avec a, b, c € { R, V, B } et a=/=b=/=c

On peut alors :

    - ajouter [(x, a)], [(y, a)] (1)

    - ajouter [(x, a)], [(y, c)] (2)

    - ajouter [(x, c)], [(y, b)] (3)

    - ajouter [(x, b)], [(y, b)] (4)


Les assignations satisfaisant c0 sont :
- x = a et y = a
- x = a et y = c
- x = b et y = a
- x = b et y = b
- x = b et y = c
- x = c et y = a
- x = c et y = b
- x = c et y = c

Il y a alors 4/8 assignations qui satisfont c0 et (1) :
- x = b et y = b
- x = b et y = c
- x = c et y = b
- x = c et y = c

Il y a alors 4/8 assignations qui satisfont c0 et (2) :
- x = b et y = a
- x = b et y = b
- x = c et y = a
- x = c et y = b

Il y a alors 4/8 assignations qui satisfont c0 et (3) :
- x = a et y = a
- x = a et y = c
- x = b et y = a
- x = b et y = c

Il y a alors 4/8 assignations qui satisfont c0 et (4) :
- x = a et y = a
- x = a et y = c
- x = c et y = a
- x = c et y = c

On voit alors que, s'il n'existe qu'une seule assignation A possible pour C, alors il y a toujours deux possibilités d'ajout de C' qui sont satisfaites par A (chaque possibilité a une assignation en commun avec les trois autres).
S'il existe plusieurs assignations possibles pour C, alors il y a + de deux possibilités d'ajout de C' qui sont satisfaites par une de ces assignations.
Donc, comme il y a toujours au moins deux possibilités pour C' qui sont satisfaites par l'assignation satisfaisant C, on peut dire que si C est satisfiable alors C' est satisfiable avec probabilité au moins 1/2.

. graphe 1 : 0% coloriable
. graphe 2 : 100% coloriable
. graphe 3 : 0% coloriable
. graphe 4 : 95.37% coloriable
. graphe 5 : 87.86% coloriable
. graphe 6 : 0% coloriable
. graphe 7 : 0% coloriable
. graphe 8 : 0% coloriable
. graphe 9 : 100% coloriable
. graphe 10 : 0% coloriable
. graphe bonus : 0% coloriable

