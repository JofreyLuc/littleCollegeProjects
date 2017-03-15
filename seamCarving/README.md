# SeamCarving

Projet de Modélisation de L3 Informatique, FST, Université de Lorraine

## Groupe

- Jofrey LUC (Jyeil)
- Quentin SONREL (Sudiukil)

## Améliorations envisagées

- [x] Interface graphique
- [x] Suppression par ligne ("en beta")
- [x] Gestion des images couleur ("en beta")
- [ ] Ajout d'une barre de progression à l'interface
- [ ] Augmentation de la taille (au lieu de diminution)
- [ ] Affichage de l'image dans l'interface graphique
- [ ] Manipulation de l'image dans l'interface graphique
- [ ] Fonction d'énergie avant

## Rapport pour la partie 1

### Répartition du travail

La répartition du travail s'est faite assez naturellement : découpage en tâches logiques et indépendantes et répartition selon nos affinités avec le travail à réaliser.

---

#### Jofrey

- Question 1 (méthode writepgm)
- Question 2 (méthode interest)
- Question 3 (méthode toGraph)
- Question 4 (Dijkstra)
- Reprise et modifications nécessaires à la gestion des couleurs et de la suppression par lignes

#### Quentin

- Question 4 (Dijkstra)
- Question 5 :
	- Méthode carve
	- Méthode removeInpgm
	- Classe principale (Main)
- Interface graphique

---

Ajouté à cela la réflexion globale portée sur le projet qui s'est faite en commun (en particulier concernant les améliorations à apporter), ainsi que, évidemment, de longues séances de debug !

### Description de l'utilisation du logiciel

Le logiciel s'utilise à l'aide d'une interface graphique (qui est l'une des améliorations apportées au sujet de base).

Pour la lancer, il suffit d'executer, au choix :
- La classe principale du projet (java fichiers.Main), préalablement compilée
- Le .jar fourni (java -jar seamCarving.jar)

L'interface graphique permet de choisir un fichier PGM (noir et blanc) ou PPM (couleur) via un sélecteur de fichier ou en saisissant son chemin directement.

Il faut ensuite sélectionner si la suppression de pixels doit se faire dans le sens de la largeur ("Traitement en ligne" décoché (par défaut)) ou dans le sens de la hauteur (coché).

Enfin, il faut choisir combien de pixels supprimer et cliquer sur le bouton "Go".

L'image sera alors traitée et le résultat enregistré dans un fichier "out.pgm" (ou "out.ppm") dans le même répertoire que l'image source.

Il est à noter que lors du traitement, l'interface graphique est figée : la progression du traitement est (pour l'instant) affichée en sortie texte dans le terminal.

#### Modifications apportées

Outre l'interface graphique, les autres modifications apportées sont :
- La possibilité de réduire l'image verticalement (au lieu d'horizontalement uniquement)
- La possibilité de traiter des images couleur (format PPM) (au lieu de noir et blanc uniquement)

## Rapport pour la partie 2

### Répartition du travail

#### Jofrey

- Modification de toGraph (méthode toGraphSuur) et écriture de twopath/addAndRevert
- Changement de la façon de calculer l'intérêt pour les images couleur (remplacement de coloredInterest par ppmTopgm+interest)
- Modification de removeInpgm et removeInppm (removeInpgm et removeInppm) (en commun)

#### Quentin

- Modification de carve, carvePgm et carvePpm (méthodes carve2, carvePgm2 et carvePpm2)
- Refonte de l'interface graphique
- Modification de removeInpgm et removeInppm (removeInpgm et removeInppm) (en commun)

---

À nouveau, les phases de réflexion se sont faites en commun et à nouveau, un quantité non négligeable de temps a été passée à debugger les différentes nouvelles fonctions... ou les anciennes !

### Description de l'utilisation du logiciel

L'utilisation de base du logiciel est sensiblement la même que pour la partie 1 :

Pour la lancer, il suffit d'executer, au choix :
- La classe principale du projet (java fichiers.Main), préalablement compilée
- Le .jar fourni (java -jar seamCarving.jar)

L'interface graphique permet les mêmes choses que dans la partie précédente mais permet également de lancer le traitement de l'image avec la méthode 2 (traitement des pixels en deux par deux colonnes).

#### Difficultés et problèmes rencontrés

La principale difficulté de cette partie a été la gestion des sommets supplémentaires (ajouté par la méthode Suurballe). En effet, les sommets ajoutés perturbent la numérotation des sommets qui n'est donc plus cohérente avec la numerotation des pixels de l'image.

Cela nous a donc posé problème pour supprimer les pixels d'une image (la liste de pixels à supprimer contenant des sommets (et donc pixels) inexistant dans l'image à traiter).

Nous avons pu régler cela en utilisant la numerotation des sommets et pixels qui se fait au fur et à mesure de la suppression (comme précédement) mais en pensant à augmenter, à chaque ligne qui doit être sautée (sommets à ignorer), le compte de sommets, de façon à toujours utiliser les bons.

Du temps a également été passé à améliorer ou adapter des éléments de la partie 1. En effet, Dijkstra a été revu, la méthode de calcul de la valeur d'intérêt des pixels également, ainsi qu'une adaptation globale des principales méthodes de suppression de pixels, pour s'adapter à la nouvelle méthode.

Il est à noter que le traitement horizontal d'images couleurs avec la méthode 1 n'est plus fonctionnel (problème de dernière minute).

Il était également prévu d'implémenter la fonction d'énergie avant, mais les changements à apporter ont été sous-estimés et le temps nous a manqué, malgré un travail bien entâmé...
