#!/usr/bin/env python3
import random
import copy
import math
import sys
import argparse
import os.path

R = 0
V = 1
B = 2

# Affiche les contraintes binaires et unaires
def afficheContraintes(binaires, unaires, n):
    for x in range(n):
        for y in range(n):
            for c1 in range(3):
                for c2 in range(3):
                    if binaires[x][y][c1][c2]:
                        if c1 == 0: c1b = 'R'
                        if c1 == 1: c1b = 'V'
                        if c1 == 2: c1b = 'B'
                        if c2 == 0: c2b = 'R'
                        if c2 == 1: c2b = 'V'
                        if c2 == 2: c2b = 'B'
                        print('[(X%d,%s),(X%d,%s)]' % (x, c1b, y, c2b))

    for x in range(n):
        for c in range(3):
            if unaires[x][c]:
                if c == 0: cb = 'R'
                if c == 1: cb = 'V'
                if c == 2: cb = 'B'
                print('[(X%d,%s)]' % (x, cb))

# Renvoie le compose d'une couleur (i.e. les deux autres couleurs)
def compose(c):
    if c == R: return [V, B]
    if c == V: return [R, B]
    if c == B: return [R, V]

# Créer un tableau de contraintes binaires à partir d'un fichier de graphe
def creerContraintesBinaires(fichier):
    # Lecture fichier
    f = open(fichier, 'r')
    ligne = f.readline()
    n = len(ligne)-1

    # Creation tableau
    binaires = [[[[False for i in range(3)]
            for i in range(3)]
        for i in range(n)]
    for i in range(n)]

    # Remplissage tableau
    for y in range(n):
        for x in range(y+1, n):
            if ligne[x] == '1':
                binaires[x][y][R][R] = True
                binaires[x][y][V][V] = True
                binaires[x][y][B][B] = True
        ligne = f.readline()

    f.close()
    
    return binaires

# Elimine la variable (correspondant à numVar dans les tableaux de contraintes)
def cas2(numVar, nbVar, couleurObligatoire, unaires, binaires):
    composeVar = compose(couleurObligatoire)
    unaires[numVar][composeVar[0]] = False
    unaires[numVar][composeVar[1]] = False

    for i in range(nbVar):
        if i != numVar:
            for color1 in range(3):
                for color2 in range(3):
                    # Si on trouve une contrainte contenant la variable
                    if binaires[numVar][i][color1][color2]:
                        binaires[numVar][i][color1][color2] = False
                        # Et si la variable est de la couleur obligatoire
                        if color1 == couleurObligatoire:
                            unaires[i][color2] = True

                    # Symétrique
                    if binaires[i][numVar][color1][color2]:
                        binaires[i][numVar][color1][color2] = False
                        if color2 == couleurObligatoire:
                            unaires[i][color1] = True

# Applique le cas 3 de l'algorithme                     
def cas3(numVar, colorVar, nbVar, binaires, unaires):
    # On élimine la contrainte unaire
    unaires[numVar][colorVar] = False
    # On élimine toutes les contraintes binaires de la forme [(numVar, colorVar], (., .)]
    for i in range(nbVar):
        for colorI in range(3):
            binaires[numVar][i][colorVar][colorI] = False
            binaires[i][numVar][colorI][colorVar] = False

    composeColor = compose(colorVar) 
    otherColor1 = composeColor[0]
    otherColor2 = composeColor[1]

    # Liste contenant les couples de variables qu'on va "fusionner"
    aSupprimer = []

    # On cherche les contraintes du type [(var, otherColor1), (y, b)]
    for i in range(nbVar):
        for colorI in range(3):
            # Si on trouve une contrainte du type [(var, otherColor1), (y, b)]
            if binaires[numVar][i][otherColor1][colorI] or binaires[i][numVar][colorI][otherColor1]:
                aSupprimer.append([numVar, i, otherColor1, colorI])
                aSupprimer.append([i, numVar, colorI, otherColor1])
                # On cherche une contrainte correspondante du type [(var, otherColor2), (z, c)]
                for j in range(nbVar):
                    for colorJ in range(3):
                        # Si on trouve le couple de contraintes
                        if (binaires[numVar][j][otherColor2][colorJ] or binaires[j][numVar][colorJ][otherColor2]):
                            if i == j:
                                if colorJ == colorI:
                                    unaires[i][colorI] = True
                            else:
                                binaires[i][j][colorI][colorJ] = True    # On ajoute la contrainte [(y, b), (z, c)

                            aSupprimer.append([numVar, j, otherColor2, colorJ])
                            aSupprimer.append([j, numVar, colorJ, otherColor2])

    # Suppression des couples de variables
    supprimeBinaires(aSupprimer, binaires)

# Supprime les contraintes binaires présentes dans la liste aSupprimer du tableau binaires
def supprimeBinaires(aSupprimer, binaires):
    for i in aSupprimer:
        binaires[i[0]][i[1]][i[2]][i[3]] = False
    
# Ajoute deux contraintes unaires à partir d'une binaire (cas 4)
def ajoutAleatoire(x, y, cx, cy, unaires):
    choix = random.randint(1,4)
    composecx = compose(cx)
    composecy = compose(cy)
    if choix == 1:
        unaires[x][cx] = True
        unaires[y][composecy[0]] = True
    elif choix == 2:
        unaires[x][cx] = True
        unaires[y][composecy[1]] = True
    elif choix == 3:
        unaires[x][composecx[0]] = True
        unaires[y][cy] = True    
    else:
        unaires[x][composecx[1]] = True
        unaires[y][cy] = True         

# Vérifie l'assignation des variable dans les tableaux des contraintes
def existeAssignation(nbVar, unaires, binaires):

    while True:	

        continueLoop = False	
        
        # 1er cas : s'il existe 3 contraintes unaires sur une même variable
        for i in range(nbVar):
        # alors l'ensemble des contraintes n'est pas satisfiable
            if unaires[i][R] and unaires[i][V] and unaires[i][B]:
                return False

        # 2ème cas : s'il existe une variable qui apparaît dans 2 contraintes unaires
        for i in range(nbVar):
            if unaires[i][R] and unaires[i][V]:
                cas2(i, nbVar, B, unaires, binaires)
                continueLoop = True
                break
            elif unaires[i][R] and unaires[i][B]:
                cas2(i, nbVar, V, unaires, binaires)
                continueLoop = True
                break
            elif unaires[i][V] and unaires[i][B]:
                cas2(i, nbVar, R, unaires, binaires)
                continueLoop = True
                break
            
        # Si on est passé dans le 2ème cas, on relance la boucle principale sans passer par les autres cas
        if continueLoop: continue
                    
        # 3ème cas : s'il existe une variable qui apparaît dans une seule contrainte unaire
        for i in range(nbVar):
            if unaires[i][R]:
                cas3(i, R, nbVar, binaires, unaires)
                continueLoop = True
                break
            elif unaires[i][V]:
                cas3(i, V, nbVar, binaires, unaires)
                continueLoop = True
                break
            elif unaires[i][B]:
                cas3(i, B, nbVar, binaires, unaires)
                continueLoop = True
                break
                            
        # Si on est passé dans le 3ème cas, on relance la boucle principale sans passer par les autres cas
        if continueLoop: continue
                            
        # 4ème cas : il n'y a que des contraintes binaires
        i = 0
        while i < nbVar and not continueLoop:
            j = 0
            while j < nbVar and not continueLoop:
                colorI = 0
                while colorI < 3 and not continueLoop:
                    colorJ = 0
                    while colorJ < 3 and not continueLoop:
                        if binaires[i][j][colorI][colorJ]:
                            ajoutAleatoire(i, j, colorI, colorJ, unaires)
                            continueLoop = True
                        colorJ+=1    
                    colorI+=1
                j+=1
            i+=1

        # Si on est passé dans aucun cas, il n'y a plus de variables
        if not continueLoop: return True    # on a donc trouvé une assignation possible
        
# Vérifie si la chaîne de caractères passées en paramètres correspond bien à un fichier existant 
def is_valid_file(filename):
    if not os.path.isfile(filename):
         raise argparse.ArgumentTypeError("%s n'est pas un entier positif." % filename)
    return filename  # retourne le nom du fichier

# Vérifie si la valeur passée en paramètre est bien un entier positif
def check_positive(value):
    ivalue = int(value)
    if ivalue <= 0:
         raise argparse.ArgumentTypeError("%s n'est pas un entier positif." % value)
    return ivalue    
    
def main():
    # On parse les arguments
    parser = argparse.ArgumentParser(description="Programme python implementant l'algortihme randomise permettant de resoudre le probleme (3, 2) - SSS (probleme des motifs interdits avec 3 couleurs et avec des contraintes unaires ou binaires.)")
    parser.add_argument("filename", metavar="fichier",
                        help="fichier contenant la matrice d'adjacence d'un graphe.",
                        type=is_valid_file)
    parser.add_argument("-r", "--repetitions", dest="repetitions", required=False,
                       help="le nombre de repetitions de l'algorithme randomise (par defaut : 10 * 2^(n/2)).", metavar="Nb_repetitions",
                       type=check_positive)
    parser.add_argument("-b", "--brief", action="store_true",
                       help="met le niveau de verbosite du programme au plus bas (n'affiche pas l'avancement).")
    args = parser.parse_args()
    
    verbosityLevel = 1
    if args.brief:
        verbosityLevel = 0
        
    # Tableau de contraintes binaires
    binaires = creerContraintesBinaires(args.filename)
    
    n = len(binaires)
    # Nombre de répétitions de l'algorithme
    if args.repetitions is not None:
        repetitions = args.repetitions
    else:   # par défaut
        repetitions = math.floor(10*pow(2, n/2)) 
        
    # Tableau de contraintes unaires
    unaires = [[False for i in range(3)] for i in range(n)]

    coloriable = 0

    # Affichage progression
    if verbosityLevel > 0:
        sys.stdout.write("\r0/%d" % (repetitions))
        sys.stdout.flush
    # Boucle des répétitions
    for i in range(repetitions):
        unairesBis = copy.deepcopy(unaires)
        binairesBis = copy.deepcopy(binaires)
        if existeAssignation(n, unairesBis, binairesBis): coloriable += 1
        # Affichage progression
        if verbosityLevel > 0:
            sys.stdout.write("\r%d/%d" % (i+1, repetitions))
            sys.stdout.flush

    print("\nColoriable a %f%%" % (coloriable/repetitions*100))

# Main
main()
