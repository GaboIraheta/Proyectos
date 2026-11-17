# Requisitos

La aplicación usa SWI-Prolog a través de JPL, así que ambos deben estar instalados en el sistema.

1. **Java 25** (`java -version`).
2. **SWI-Prolog** con `swipl` en el PATH (`swipl --version`).
3. **JPL** (el puente Java–Prolog de SWI-Prolog). Debe existir el `jpl.jar` y la biblioteca nativa `libjpl.so` (Linux) o `jpl.dll` (Windows).

| Sistema | Instalación |
|---|---|
| Fedora | `sudo dnf install swipl swipl-jpl` |
| Debian/Ubuntu | `sudo apt install swi-prolog swi-prolog-java` |
| Windows | Instalador de SWI-Prolog 64 bits (swi-prolog.org); marcar "Add swipl to the PATH". JPL viene incluido. |

Verificación en Linux: `find / -name libjpl.so 2>/dev/null` debe devolver una ruta.

# Ejecución

- Linux: `./run.sh`
- Windows: `run.bat`

Los scripts localizan `libjpl.so` / `jpl.dll` y lo pasan a Java con `-Djava.library.path`. Equivale a:

    java --enable-native-access=ALL-UNNAMED -Djava.library.path=<carpeta con libjpl.so> -cp <jpl.jar>:DefiniteIntegrals.jar org.example.Main

`java -jar DefiniteIntegrals.jar` también puede funcionar si JPL se encuentra solo, pero no está garantizado y muestra un aviso de acceso nativo. Si falla, la aplicación muestra "No se pudo iniciar SWI-Prolog a través de JPL"; en ese caso usa `run.sh`.

El `jpl.jar` debe corresponder a la misma versión de SWI-Prolog que `libjpl.so`; por eso los scripts prefieren el del sistema. El de `lib/` es solo un respaldo.
