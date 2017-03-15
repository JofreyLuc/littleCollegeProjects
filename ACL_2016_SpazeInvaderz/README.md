# SpazeInvaderz

Projet M1 Informatique, FST Nancy, Université de Lorraine, 2016.

## Build et exécution

Le jeu peut être construit et exécuté soit avec Gradle soit avec Ant.

### Grade (recommandé)

Pour compiler :

	./gradlew build

Pour exécuter :

	./gradlew run

Pour lancer les tests :

	./gradlew test
	
Les résultats des tests sous la forme d'une page HTML se trouvent dans le répertoire :

	ACL_2016_SpazeInvaderz/core/build/reports/tests/test/classes/

Note : Il est également possible d'utiliser une version de Gradle déjà installé sur le système. Le script gradlew fourni a cependant l'avantage de garantir un fonctionnement et un rendu identique à celui prévu (téléchargement automatique de la bonne version de Gradle).

### Ant

Pour compiler :

	ant

Le build Ant du projet permet uniquement de compiler le programme, les exécutables et les tests sont à lancer à la main classiquement.

## Backlogs

### Backlog S1 (séance du 10/11/16) :
  1. Le vaisseau du joueur est affiché
  2. Le joueur peut déplacer le vaisseau de droite à gauche
  
### Backlog S2 (du 10/11 au 24/11/16) :
  1. Les ennemis sont affichés
  2. Les ennemis se déplacent
  3. Le joueur peut tirer
  
### Backlog S3 (24/11/16) :
  1. Les ennemis tirent
  2. Les ennemis descendent
  3. Amélioration du déplacement des ennemis
  
### Backlog S4 :
  1. Les tirs détruisent les ennemis / le vaisseau
  2. Affichage des vies
  3. Affichage des points
  4. Ecran de fin