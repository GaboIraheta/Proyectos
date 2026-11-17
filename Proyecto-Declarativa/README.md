# Entrada por tipo de integral

En el campo de la función se escribe solo el integrando, en sintaxis Prolog y con `X` como variable. El sistema arma internamente `s(X, integrando, R)` y muestra la antiderivada. Si además se llenan los límites `a` y `b`, se muestra el valor de la integral definida.

| Inciso | Integral | Función (entrada) | a | b |
|---|---|---|---|---|
| a) | ∫k dx, k = 5 | `5` | 0 | 4 |
| b) | ∫x dx | `X` | 0 | 2 |
| c) | ∫k·x dx, k = 3 | `3*X` | 0 | 2 |
| d) | ∫xⁿ dx, n = 2 | `X^2` | 0 | 3 |
| f) | ∫k·u dx, k = 4, u = x² | `4*X^2` | 1 | 2 |
| g) | ∫(u + v) dx, u = x², v = x | `X^2+X` | 0 | 1 |
| h) | ∫(u − v) dx, u = x², v = x | `X^2-X` | 0 | 1 |
| i) | ∫a dx, a = 5 | `5` | 0 | 4 |
| j) | ∫aᵘ dx, a = 2, u = x | `2^X` | 0 | 3 |
| j) | ∫aᵘ dx, a = 2, u = 3x | `2^(3*X)` | 0 | 1 |
| k) | ∫sin(x) dx | `sin(X)` | 0 | 1 |
| l) | ∫cos(x) dx | `cos(X)` | 0 | 1 |
| m) | ∫tan(x) dx | `tan(X)` | 0 | 1 |
| n) | ∫asin(x) dx | `asin(X)` | 0 | 1 |
| o) | ∫acos(x) dx | `acos(X)` | 0 | 1 |
| p) | ∫atan(x) dx | `atan(X)` | 0 | 1 |
| q) | ∫sinh(x) dx | `sinh(X)` | 0 | 1 |
| r) | ∫cosh(x) dx | `cosh(X)` | 0 | 1 |
| s) | ∫tanh(x) dx | `tanh(X)` | 0 | 1 |
| t) | ∫arcsinh(x) dx | `arcsinh(X)` | 0 | 1 |
| u) | ∫arccosh(x) dx | `arccosh(X)` | 1 | 2 |
| v) | ∫arctanh(x) dx | `arctanh(X)` | 0 | 0.5 |

Para ejecutar: `./run.sh` (ver `INSTALACION.md` para los requisitos).
