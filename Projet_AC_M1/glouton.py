#!/usr/bin/env python3
import itertools
import copy

R = 0
V = 1
B = 2

# Classe représentant une contrainte
class Contrainte :
    # int poids
    # list couples[variable][couleur]
    def __init__(self, p, c):
        self.poids = p
        self.couples = c

# Retourne le nombre de variables présents dans les contraintes
def getNbVariables(contraintes):
    nbVar = 0
    for c in contraintes:
        for couple in c.couples:
            if couple[0] + 1 > nbVar:    # couple[0] correspond au numéro de la variable
                nbVar = couple[0] + 1
    return nbVar
  
# Trie les contraintes par ordre décroissant de poids
def trier(contraintes):
    return sorted(contraintes, key=lambda x: x.poids/len(x.couples), reverse=True)

# Renvoie vrai si l'assignation satisfait la contrainte
def assignationPossible(assignation, contrainte):
    for i in range(len(contrainte.couples)):
        if assignation[contrainte.couples[i][0]] != contrainte.couples[i][1]:
            return True
    return False
           
# Renvoie un tableau d'assignations amputé des assignations qui ne satisfont pas la contrainte
def trouverAssignationsPossibles(assignations, contrainte):
    assignations[:] = [x for x in assignations if assignationPossible(x, contrainte)]
    return assignations

# Génère toutes les assignations possibles avec nbVar variables et 3 couleur (R, V, B)
def genererAssignations(nbVar):
    return list(itertools.product(range(3), repeat=nbVar))

# Implémentation de l'algorithme glouton
def glouton(contraintes):
    contraintes = trier(contraintes)
    nbVar = getNbVariables(contraintes)
    
    # On génère toutes les assignations possibles
    assignations = genererAssignations(nbVar)
    # On initialise le poids à 0
    maxPoids = 0
    
    # Pour chaque contrainte
    for c in contraintes:
        # On copie la liste d'assignations
        copieAssignations = copy.deepcopy(assignations)
        # On regarde s'il existe une assignation incompatible avec la contrainte parmi la liste
        if trouverAssignationsPossibles(copieAssignations, c):  # si la liste n'est pas vide = s'il existe une assignation incompatible
            assignations = copieAssignations    # On prend la nouvelle liste d'assignations
            maxPoids += c.poids                 # On ajoute le poids de la contrainte

    #afficheAssignations(assignations)
    return maxPoids

# Affiche la liste d'assignations
def afficheAssignations(assi):
    for i in assi:
        print(i)

def main():
    #contraintes = [Contrainte(10, [[0, R], [1, V], [2, B]]), Contrainte(5, [[1, B], [2, R]])]
    #contraintes = [Contrainte(10000, [[0, R]]), Contrainte(10000, [[0, V]]), Contrainte(30000, [[0, B]])]
    #contraintes = [Contrainte(2, [[0, R], [1, R]]), Contrainte(2, [[0, V], [1, R]]), Contrainte(2, [[0, B], [1, R]]), Contrainte(7, [[0, B], [1, V]])]
    # Ensemble de contraintes prouvant la non optimalité de l'algorithme glouton (résultat optimal = 13)
    contraintes = [Contrainte(2, [[1, 0], [0, 2]]),
                   Contrainte(2, [[1, 1],[0, 2]]),
                   Contrainte(2, [[1, 2],[0, 2]]),
                   Contrainte(3, [[0, 2]]),
                   Contrainte(4, [[0, 1]]),
                   Contrainte(4, [[0, 0]])]
    
    maxPoids = glouton(contraintes)
    print("Poids cumule maximal : %d" % maxPoids)
    
# Main
main()
