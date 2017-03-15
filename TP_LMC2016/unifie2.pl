:- op(20,xfy,?=).
set_echo :- assert(echo_on).
clr_echo :- retractall(echo_on).
echo(T) :- echo_on, !, write(T).
echo(_).

%%% Question 1 %%%

%% Règles %%

regle(X?=T, rename) :-
    % Rename applicable si X variable et T variable
    var(X),
    var(T), !.

regle(X?=T, simplify) :-
    % Simplify applicable si X variable et T constante
    var(X),
    atomic(T), !.

regle(X?=T, expand) :-
    % Expand applicable si X variable et T composé (et pas une liste)..
    var(X),
    compound(T),
    \+ is_list(T),
    % .. et si T ne contient pas X
    \+ occur_check(X, T), !.

regle(X?=T, check) :-
    % Check applicable si X variable...
    var(X),
    % ...et X différent de X...
    X \== T,
    % ...et si T contient X
    occur_check(X, T), !.


regle(T?=X, orient) :-
    % Orient applicable si X variable et T non variable
    var(X),
    nonvar(T), !.

regle(Fgauche?=Fdroite, decompose) :-
    % Decompose applicable si les deux termes sont composés (et pas des listes)..
    compound(Fgauche),
    compound(Fdroite),
    \+ is_list(Fgauche),
    \+ is_list(Fdroite),
    % .. et si ils ont le même nom et la même arité
    functor(Fgauche, Nom, Arite),
    functor(Fdroite, Nom, Arite), !.

regle(Fgauche?=Fdroite, clash) :-
    % Clash applicable si les deux termes sont composés (et pas des listes)..
    compound(Fgauche),
    compound(Fdroite),
    \+ is_list(Fgauche),
    \+ is_list(Fdroite),
    % .. et dans les conditions contraires à decompose
    \+ regle(Fgauche?=Fdroite, decompose), !.

%% Occur_check %%

occur_check(X, T) :- contains_var(X, T).

%% Reduit %%

reduit(rename, X?=T, P, Q) :-
    % On unifie X avec T, et Q vaut la liste d'équations ainsi modifiée
    X = T,
    Q = P.
    
reduit(simplify, X?=T, P, Q) :-
    % On unifie X avec T, et Q vaut la liste d'équations ainsi modifiée
    X = T,
    Q = P.

reduit(expand, X?=T, P, Q) :-
    % On unifie X avec T, et Q vaut la liste d'équations ainsi modifiée
    X = T,
    Q = P.

reduit(orient, X?=T, P, Q) :-
    % On ajoute l'équation inversée à la liste de base, et on stocke le résultat dans Q
    append(P, [T?=X], Q).
    
reduit(decompose, Fgauche?=Fdroite, P, Q) :-
    % On prend les arguments des fonctions...
    Fgauche =.. [_|ListeFgauche],
    Fdroite =.. [_|ListeFdroite],
    % ...et on les fusionne deux à deux pour en faire des équations a?=b
    decompose_fusion(ListeFgauche, ListeFdroite, Resultat),
    % On ajoute la liste des a?=b à P et on stocke le résultat dans Q.
    append(P, Resultat, Q).

decompose_fusion([],[],[]) :- !. % Cas d'arrêt, lorsque les deux listes sont vides l'équation est nulle 

decompose_fusion([TeteGauche|QueueGauche], [TeteDroite|QueueDroite], R) :-
    % Récursion sur la queue des listes : on construit petit à petit la liste d'équations
    decompose_fusion(QueueGauche, QueueDroite, Rsuivant),
    append(Rsuivant, [TeteGauche?=TeteDroite], R).    

%% Unifie %%

% Cas d'arrêt, liste vide
unifie([]) :- !.

% Applique rename si possible
unifie([HeadP|TailP]) :-
    regle(HeadP, rename), !,
    echo('system: '),
    echo([HeadP|TailP]), echo('\n'),
    echo('rename: '),
    echo(HeadP), echo('\n'),
    reduit(rename, HeadP, TailP, Q),
    unifie(Q), !.

% Applique simplify si possible
unifie([HeadP|TailP]) :-
    regle(HeadP, simplify), !,
    echo('system: '),
    echo([HeadP|TailP]), echo('\n'),
    echo('simplify: '),
    echo(HeadP), echo('\n'),
    reduit(simplify, HeadP, TailP, Q),
    unifie(Q), !.

% Applique expand si possible
unifie([HeadP|TailP]) :-
    regle(HeadP, expand), !,
    echo('system: '),
    echo([HeadP|TailP]), echo('\n'),
    echo('expand: '),
    echo(HeadP), echo('\n'),
    reduit(expand, HeadP, TailP, Q),
    unifie(Q), !.

% Applique check si possible
unifie([HeadP|TailP]) :-
    regle(HeadP, check), !,
    echo('system: '),
    echo([HeadP|TailP]), echo('\n'),
    echo('check: '),
    echo(HeadP), echo('\n'),
    fail, !.

% Applique orient si possible
unifie([HeadP|TailP]) :-
    regle(HeadP, orient), !,
    echo('system: '),
    echo([HeadP|TailP]), echo('\n'),
    echo('orient: '),
    echo(HeadP), echo('\n'),
    reduit(orient, HeadP, TailP, Q),
    unifie(Q), !.

% Applique decompose si possible
unifie([HeadP|TailP]) :-
    regle(HeadP, decompose), !,
    echo('system: '),
    echo([HeadP|TailP]), echo('\n'),
    echo('decompose: '),
    echo(HeadP), echo('\n'),
    reduit(decompose, HeadP, TailP, Q),
    unifie(Q), !.

% Applique clash si possible
unifie([HeadP|TailP]) :-
    regle(HeadP, clash), !,
    echo('system: '),
    echo([HeadP|TailP]), echo('\n'),
    echo('clash: '),
    echo(HeadP), echo('\n'),
    fail, !.

% Lorsque plus aucune règle n'est applicable, finit
unifie(_) :- !.

%%% Question 2 %%%

% Choix_premier (pas utilisé) : indique simplement si on peut appliquer une règle sur la première équation de P
choix_premier([HeadP|TailP], TailP, HeadP, rename) :- regle(HeadP, rename), !.
choix_premier([HeadP|TailP], TailP, HeadP, simplify) :- regle(HeadP, simplify), !.
choix_premier([HeadP|TailP], TailP, HeadP, expand) :- regle(HeadP, expand), !.
choix_premier([HeadP|TailP], TailP, HeadP, check) :- regle(HeadP, check), !.
choix_premier([HeadP|TailP], TailP, HeadP, orient) :- regle(HeadP, orient), !.
choix_premier([HeadP|TailP], TailP, HeadP, decompose) :- regle(HeadP, decompose), !.
choix_premier([HeadP|TailP], TailP, HeadP, clash) :- regle(HeadP, clash), !.

% unifie(P) cas particulier de unifie(P, S)
unifie(P, choix_premier) :- unifie(P), !.

% choix_pondere %

% Renvoie la première équation E de P qui satisfait la règle R
premiere_applicable([HeadP|_], R, E) :- 
    regle(HeadP, R), % Si la tête de P satisfait R, on l'unifie avec E
    E = HeadP, !.
premiere_applicable([_|TailP], R, E) :-
    premiere_applicable(TailP, R, E). % Sinon, on regarde la queue de P

% Enlève l'équation E de P et met le reste P\{E} dans Q
retirer(E, [HeadP|TailP], TailP):-
    E == HeadP, !. % Si E est la tête de P, on renvoie la queue de P
retirer(E, [HeadP|TailP], Q):-
    retirer(E, TailP, Q1),
    append([HeadP], Q1, Q). % Sinon, on concatène la tête de P avec le résultat de l'appel récursif 

% choix_pondere : grâce au cut, on évaluera successivement tous les prédicats jusqu'à en trouver un applicable, puis on retournera dans unifie
choix_pondere(P, Q, E, clash) :-
    premiere_applicable(P, clash, E),
    retirer(E, P, Q), !.
choix_pondere(P, Q, E, check) :-
    premiere_applicable(P, check, E),
    retirer(E, P, Q), !.
choix_pondere(P, Q, E, rename) :-
    premiere_applicable(P, rename, E),
    retirer(E, P, Q), !.
choix_pondere(P, Q, E, simplify) :-
    premiere_applicable(P, simplify, E),
    retirer(E, P, Q), !.
choix_pondere(P, Q, E, orient) :-
    premiere_applicable(P, orient, E),
    retirer(E, P, Q), !.
choix_pondere(P, Q, E, decompose) :-
    premiere_applicable(P, decompose, E),
    retirer(E, P, Q), !.
choix_pondere(P, Q, E, expand) :-
    premiere_applicable(P, expand, E),
    retirer(E, P, Q), !.
choix_pondere(_, [], [], []) :- !. % Si aucune règle applicable : on renvoie des listes vides (cas d'arrêt)

% unifie(P, choix_pondere)

unifie([], choix_pondere) :- !. % Cas d'arrêt sur liste vide 

% Récursion
unifie(P, choix_pondere) :-
    choix_pondere(P, Q, E, R), % On cherche quelle règle appliquer sur quelle équation
    unifie_pondere(R, E, Q, Suite), % On applique cette règle sur cette équation
    unifie(Suite, choix_pondere). % Appel récursif sur la liste d'équations modifiée

% Applique rename
unifie_pondere(rename, E, Q, Suite) :-
    echo('system: '),
    echo([E,Q]), nl,
    echo('rename: '),
    echo(E), nl,
    reduit(rename, E, Q, Suite).

% Applique simplify
unifie_pondere(simplify, E, Q, Suite) :-
    echo('system: '),
    echo([E,Q]), nl,
    echo('simplify: '),
    echo(E), nl,
    reduit(simplify, E, Q, Suite).

% Applique expand
unifie_pondere(expand, E, Q, Suite) :-
    echo('system: '),
    echo([E,Q]), nl,
    echo('expand: '),
    echo(E), nl,
    reduit(expand, E, Q, Suite).

% Applique check
unifie_pondere(check, E, Q, Suite) :-
    echo('system: '),
    echo([E,Q]), nl,
    echo('check: '),
    echo(E), nl,
    reduit(check, E, Q, Suite).

% Applique orient
unifie_pondere(orient, E, Q, Suite) :-
    echo('system: '),
    echo([E,Q]), nl,
    echo('orient: '),
    echo(E), nl,
    reduit(orient, E, Q, Suite).

% Applique decompose
unifie_pondere(decompose, E, Q, Suite) :-
    echo('system: '),
    echo([E,Q]), nl,
    echo('decompose: '),
    echo(E), nl,
    reduit(decompose, E, Q, Suite).

% Applique clash
unifie_pondere(clash, E, Q, Suite) :-
    echo('system: '),
    echo([E,Q]), nl,
    echo('clash: '),
    echo(E), nl,
    reduit(clash, E, Q, Suite).

% Cas d'arrêt (aucune règle applicable)
unifie_pondere([], [], [], []).

%%% Question 3 %%%

unif(P, S) :- clr_echo, unifie(P, S). % Affichage sans trace 
trace_unif(P, S) :- set_echo, unifie(P, S). % Affichage avec trace
