% Version du programme évitant l'utilisation du prédicat "=" (implémentation "maison" des prédicats de remplacement de variables)

:- op(20,xfy,?=).
set_echo :- assert(echo_on).
echo(T) :- echo_on, !, write(T).
echo(_).

echo_on.

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
    % Expand applicable si X variable et T composé
    var(X),
    compound(T),
    % .. et si T ne contient pas X
    \+ occur_check(X, T), !.

regle(X?=T, check) :-
    % Check applicable si X variable...
    var(X),
    % ...et X différent de X...
    X \== T,
		% ...et si T contient X
    occur_check(X, T), !.


regle(X?=T, orient) :-
    % Orient applicable si X variable et T non variable
    var(X),
    nonvar(T), !.


regle(Fgauche?=Fdroite, decompose) :-
    % Decompose applicable si les deux termes ont le même nom et la même arité
    compound(Fgauche),
    compound(Fdroite),
    functor(Fgauche, Nom, Arite),
    functor(Fdroite, Nom, Arite), !.

regle(E, clash) :-
    % Clash applicable dans les conditions contraires à decompose
    \+ regle(E, decompose), !.

%% Occur_check %%

occur_check(X, T) :- contains_var(X, T).

%% Réduit : Format de P et Q : contiennent chacun deux listes correspondant à P' et à S du sujet %%

reduit(rename, X?=T, [HeadP|TailP], [HeadQ|TailQ]):-
    remplace(X, T, HeadP, HeadQ),
    remplace(X, T, TailP, TailPBis),
    append(TailPBis, [X=T], TailQ).
    
reduit(simplify, X?=T, [HeadP|TailP], [HeadQ|TailQ]):-
    remplace(X, T, HeadP, HeadQ),
    remplace(X, T, TailP, TailPBis),
    append(TailPBis, [X=T], TailQ).

reduit(expand, X?=T, [HeadP|TailP], [HeadQ|TailQ]):-
    remplace(X, T, HeadP, HeadQ),
    remplace(X, T, TailP, TailPBis),
    append(TailPBis, [X=T], TailQ).

reduit(orient, X?=T, [HeadP|TailP], [HeadQ|TailP]):-
    % On ajoute l'équation inversée à la tête de Q (P'), la queue de Q (S) ne change pas.
    append(HeadP, [T?=X], HeadQ).

reduit(decompose, Fgauche?=Fdroite, [HeadP|TailP], [HeadQ|TailP]):-
    % On prend les arguments des fonctions..
    Fgauche =.. [_|ListeFgauche],
    Fdroite =.. [_|ListeFdroite],
    % .. et on les fusionne deux à deux pour en faire des équations a?=b
    decompose_fusion(ListeFgauche, ListeFdroite, Resultat),
    % On ajoute la liste des a?=b à la tête de Q (P'), la queue de Q (S) ne change pas.
    append(HeadP, Resultat, HeadQ).

reduit(check, X?=T, P, Q) :-
    Q = "Non".

reduit(clash, X?=T, P, Q) :-
    Q = "Non".

decompose_fusion([],[],[]):- !. % Cas d'arrêt, lorsque les deux listes sont vides l'équation est nulle 

decompose_fusion([TeteGauche|QueueGauche], [TeteDroite|QueueDroite], R) :-
    % Récursion sur la queue des listes : on construit petit à petit la liste d'équations
    decompose_fusion(QueueGauche, QueueDroite, Rsuivant),
    append(Rsuivant, [TeteGauche?=TeteDroite], R).

remplace(X, T, Y, T) :-
    var(Y),
    X == Y, !.

remplace(X, T, [Y], R) :-
    remplace(X, T, Y, R).

remplace(X, T, [AG|LAD], Remplacee) :-
    remplace(X, T, AG, NA),
    remplace(X, T, LAD, NLA),
    %Remplacee =.. [NLA].
    append(NA, NLA, Remplacee), !.

remplace(X, T, Entree, Remplacee) :-
    \+ is_list(Entree),
    Entree =.. [F|LA],
    remplace(X, T, LA, NLA),
    Remplacee =.. [F,NLA], !.

%% Unifie %%

% Cas d'arrêt, liste vide
unifie([]) :- !.

% Étape "rename" l'unification
unifie([HeadP|TailP]) :-
    regle(HeadP, rename),
    echo('system: '),
    echo([HeadP|TailP]), nl,
    echo('rename: '),
    echo(HeadP), nl,
    reduit(rename, HeadP, TailP, Q),
    unifie(Q), !.

% Étape "simplify" l'unification
unifie([HeadP|TailP]) :-
    regle(HeadP, simplify),
    echo('system: '),
    echo([HeadP|TailP]), nl,
    echo('simplify: '),
    echo(HeadP), nl,
    reduit(simplify, HeadP, TailP, Q),
    unifie(Q), !.

% Étape "expand" l'unification
unifie([HeadP|TailP]) :-
    regle(HeadP, expand),
    echo('system: '),
    echo([HeadP|TailP]), nl,
    echo('expand: '),
    echo(HeadP), nl,
    reduit(expand, HeadP, TailP, Q),
    unifie(Q), !.

% Étape "check" l'unification
unifie([HeadP|TailP]) :-
    regle(HeadP, check),
    echo('system: '),
    echo([HeadP|TailP]), nl,
    echo('check: '),
    echo(HeadP), nl,
    fail, !.

% Étape "orient" l'unification
unifie([HeadP|TailP]) :-
    regle(HeadP, orient),
    echo('system: '),
    echo([HeadP|TailP]), nl,
    echo('orient: '),
    echo(HeadP), nl,
    reduit(orient, HeadP, TailP, Q),
    unifie(Q), !.

% Étape "decompose" l'unification
unifie([HeadP|TailP]) :-
    regle(HeadP, decompose),
    echo('system: '),
    echo([HeadP|TailP]), nl,
    echo('decompose: '),
    echo(HeadP), nl,
    reduit(decompose, HeadP, TailP, Q),
    unifie(Q), !.

% Étape "clash" l'unification
unifie([HeadP|TailP]) :-
    regle(HeadP, clash),
    echo('system: '),
    echo([HeadP|TailP]), nl,
    echo('clash: '),
    echo(HeadP), nl,
    fail, !.
