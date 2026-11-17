% integral de una constante numerica
s(V, K, K*V) :-
    var(V), number(K), !.

% integral de x
s(V, E, 1/2*V^2) :-
    var(V), E == V, !.

% integral de k*x
s(V, K*E, K/2*V^2) :-
    var(V), number(K), E == V, !.

% integral de x^n, n entero
s(V, E^N, R) :-
    var(V), E == V, integer(N), !,
    (   N =:= -1
    ->  R = log(abs(V))
    ;   C is N + 1,
        R = 1/C*V^C
    ).

% integral de a^u, con u = x  o  u = k*x
s(V, A^E, V) :-
    var(V), number(A), A =:= 1, E == V, !.
s(V, A^E, A^V/log(A)) :-
    var(V), number(A), A > 0, E == V, !.
s(V, A^(K*E), V) :-
    var(V), number(A), number(K), A =:= 1, E == V, !.
s(V, A^(K*E), A^(K*V)/(K*log(A))) :-
    var(V), number(A), number(K), A > 0, K =\= 0, E == V, !.

% integral de k*u, u funcion de x
s(V, K*U, K*R) :-
    var(V), number(K), !,
    s(V, U, R).

% integral de u+v
s(V, U+W, R1+R2) :-
    var(V), !,
    s(V, U, R1),
    s(V, W, R2).

% integral de u-v
s(V, U-W, R1-R2) :-
    var(V), !,
    s(V, U, R1),
    s(V, W, R2).

% funciones trigonometricas, hiperbolicas e inversas
s(V, sin(E), -cos(V)) :-
    var(V), E == V, !.

s(V, cos(E), sin(V)) :-
    var(V), E == V, !.

s(V, tan(E), -log(abs(cos(V)))) :-
    var(V), E == V, !.

s(V, asin(E), V*asin(V)+sqrt(1-V^2)) :-
    var(V), E == V, !.

s(V, acos(E), V*acos(V)-sqrt(1-V^2)) :-
    var(V), E == V, !.

s(V, atan(E), V*atan(V)-1/2*log(1+V^2)) :-
    var(V), E == V, !.

s(V, sinh(E), cosh(V)) :-
    var(V), E == V, !.

s(V, cosh(E), sinh(V)) :-
    var(V), E == V, !.

s(V, tanh(E), log(cosh(V))) :-
    var(V), E == V, !.

s(V, arcsinh(E), V*asinh(V)-sqrt(V^2+1)) :-
    var(V), E == V, !.

s(V, arccosh(E), V*acosh(V)-sqrt(V^2-1)) :-
    var(V), E == V, !.

s(V, arctanh(E), V*atanh(V)+1/2*log(1-V^2)) :-
    var(V), E == V, !.

% ======================================================================
%  Integrales definidas
% ======================================================================

area(_, R, Li, Ls, _) :-
    A is Li, B is Ls,
    (   A =< B -> Lo = A, Hi = B ; Lo = B, Hi = A ),
    restriccion(R, Lo, Hi),
    fail.

area(X, R, Li, Ls, Area) :-
    copy_term(X-R, Xs-Rs), is(Xs, Ls), is(ELs, eval(Rs)),
    copy_term(X-R, Xi-Ri), is(Xi, Li), is(ELi, eval(Ri)),
    Area is ELs - ELi.


%  Revision de dominio
% Los predicados de dominio son para marcar qué función se evaluó fuera del dominio.
% El predicado area/5 tiene una cláusula previa para encontrar el error, la cual nunca
% tendrá éxito: O lanza el error o falla para que se evalué la siguiente cláusula.
% Los predicados de restricción definen los valores fuera del rango de las funciones.

restriccion(T, _, _) :- var(T), !.
restriccion(T, _, _) :- atomic(T), !.

restriccion(asin(A), Lo, Hi) :-
    var(A), !,
    (   -1 =< Lo, Hi =< 1 -> true ; throw(dominio(asin)) ).
restriccion(acos(A), Lo, Hi) :-
    var(A), !,
    (   -1 =< Lo, Hi =< 1 -> true ; throw(dominio(acos)) ).
restriccion(acosh(A), Lo, _) :-
    var(A), !,
    (   1 =< Lo -> true ; throw(dominio(arccosh)) ).
restriccion(atanh(A), Lo, Hi) :-
    var(A), !,
    (   -1 < Lo, Hi < 1 -> true ; throw(dominio(arctanh)) ).
restriccion(log(abs(A)), Lo, Hi) :-
    var(A), !,
    (   Lo =< 0, 0 =< Hi -> throw(dominio(polo_en_cero)) ; true ).
restriccion(log(abs(cos(A))), Lo, Hi) :-
    var(A), !,
    P = 3.141592653589793,
    K1 is floor((Hi - P/2)/P),
    K2 is ceiling((Lo - P/2)/P),
    (   K1 >= K2 -> throw(dominio(tan)) ; true ).
restriccion(A^N, Lo, Hi) :-
    var(A), integer(N), N < 0, !,
    (   Lo =< 0, 0 =< Hi -> throw(dominio(polo_en_cero)) ; true ).

% Recorrido del resto de la expresion.
restriccion(A+B, Lo, Hi) :- !, restriccion(A, Lo, Hi), restriccion(B, Lo, Hi).
restriccion(A-B, Lo, Hi) :- !, restriccion(A, Lo, Hi), restriccion(B, Lo, Hi).
restriccion(A*B, Lo, Hi) :- !, restriccion(A, Lo, Hi), restriccion(B, Lo, Hi).
restriccion(A/B, Lo, Hi) :- !, restriccion(A, Lo, Hi), restriccion(B, Lo, Hi).
restriccion(A^B, Lo, Hi) :- !, restriccion(A, Lo, Hi), restriccion(B, Lo, Hi).
restriccion(-A, Lo, Hi) :- !, restriccion(A, Lo, Hi).
restriccion(log(A), Lo, Hi) :- !, restriccion(A, Lo, Hi).
restriccion(sqrt(A), Lo, Hi) :- !, restriccion(A, Lo, Hi).
restriccion(_, _, _).

