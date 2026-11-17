@echo off
rem Lanza la aplicacion con JPL. Requiere SWI-Prolog 64 bits instalado (ver INSTALACION.md).
cd /d "%~dp0"
for /f "usebackq delims=" %%i in (`swipl -g "current_prolog_flag(home,H),write(H),nl" -t halt`) do set PLHOME=%%i
java --enable-native-access=ALL-UNNAMED -Djava.library.path="%PLHOME%\bin" -cp "%PLHOME%\lib\jpl.jar;DefiniteIntegrals.jar" org.example.Main
