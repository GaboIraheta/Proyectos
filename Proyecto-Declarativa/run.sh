#!/bin/sh
# Lanza la aplicación con JPL. Requiere SWI-Prolog con JPL instalado (ver INSTALACION.md).
cd "$(dirname "$0")" || exit 1

command -v swipl >/dev/null 2>&1 || { echo "No se encontró 'swipl' en el PATH. Ver INSTALACION.md."; exit 1; }
command -v java  >/dev/null 2>&1 || { echo "No se encontró 'java' en el PATH (se requiere Java 25)."; exit 1; }

PLBASE=$(swipl --dump-runtime-variables | sed -n 's/^PLBASE="\(.*\)";$/\1/p')
LIBJPL=$(find "$PLBASE" /usr/lib /usr/lib64 /usr/local/lib /opt -name libjpl.so 2>/dev/null | head -1)
[ -n "$LIBJPL" ] || { echo "No se encontró libjpl.so: falta instalar JPL. Ver INSTALACION.md."; exit 1; }

# Se prefiere el jpl.jar del SWI-Prolog instalado, para que coincida con libjpl.so.
JPLJAR=$(find "$PLBASE" "$(dirname "$LIBJPL")" /usr/lib/java /usr/share/java -name 'jpl*.jar' 2>/dev/null | head -1)
[ -n "$JPLJAR" ] || JPLJAR=lib/jpl.jar

exec java --enable-native-access=ALL-UNNAMED \
    -Djava.library.path="$(dirname "$LIBJPL")" \
    -cp "$JPLJAR:DefiniteIntegrals.jar" org.example.Main
