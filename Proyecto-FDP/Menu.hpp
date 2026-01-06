#include <iostream>
#include <iomanip>
#include "DUI.hpp"
#include "votos.hpp"
using namespace std;

void LimpiarPantalla();
void Logo();
int MenuPrincipal();
int MenuAdministrador();
int MenuUsuario();

int MenuPrincipal()
{
    LimpiarPantalla();
    Logo();
    cout << setfill(':')
         << "|"
         << setw(80) << left << "Tribunal Supremo Electoral"
         << "|"
         << endl
         << endl
         << "|"
         << setw(80) << left << "Contando uno a uno el futuro"
         << "|"
         << endl
         << endl;

    cout << setfill(' ')
         << "|"
         << setw(40) << left << "Opciones Administrador"
         << setw(40) << right << "[1]"
         << "|"
         << endl
         << "|"
         << setw(40) << left << "Opciones Generales"
         << setw(40) << right << "[2]"
         << "|"
         << endl
         << setfill(':')
         << "|"
         << setw(40) << left << "Salir"
         << setw(40) << right << "[3]"
         << "|"
         << endl;
    int opcion = 0;
    cin >> opcion;

    switch (opcion)
    {
    case 1:
        MenuAdministrador();
        break;
    case 2:
        MenuUsuario();
        break;
    case 3:
        return 1;
        break;
    default:
        return 1;
        break;
    }

    return 1;
}

int MenuAdministrador()
{
    VaciarDatosVotantes();
    LimpiarPantalla();
    Logo();
    cout << setfill(':')
         << "|"
         << setw(80) << left << "Opciones Administrador"
         << "|"
         << endl
         << "|"
         << setfill(' ')
         << setw(40) << left << "Registrar Votantes"
         << setw(40) << right << "[1]"
         << "|"
         << endl
         << "|"
         << setw(40) << left << "Mostrar Resultados"
         << setw(40) << right << "[2]"
         << "|"
         << endl
         << "|"
         << setw(40) << left << "Mostrar Informacion de DUI"
         << setw(40) << right << "[3]"
         << "|"
         << endl
         << "|"
         << setw(40) << left << "Generar Registro de DUI"
         << setw(40) << right << "[4]"
         << "|"
         << endl
         << setfill(':')
         << "|"
         << setw(40) << left << "Volver a menu principal"
         << setw(40) << right << "{5}"
         << "|"
         << endl;

    int opcion = 0;
    cin >> opcion;
    string salir = "";
    string DUI = "";

    switch (opcion)
    {
    case 1:
        LimpiarPantalla();
        RegistrarVotantes();
        cout << "Registrado! escriba [ok] para continuar..." << endl;
        cin >> salir;
        MenuPrincipal();
        break;
    case 2:
        LimpiarPantalla();
        Resultados();
        cout << "Escriba [ok] para continuar..." << endl;
        cin >> salir;
        MenuPrincipal();
        break;
    case 3:
        LimpiarPantalla();
        cout << "Ingrese su numero de DUI: ";
        cin >> DUI;
        LimpiarPantalla();
        MostrarInformacionDUI(DUI);
        cout << "Escriba [ok] para continuar..." << endl;
        cin >> salir;
        MenuPrincipal();
        break;
    case 4:
        LimpiarPantalla();
        GenerarArchivoDeVotantes();
        cout << "Archivo generado, Escriba [ok] para continuar..." << endl;
        cin >> salir;
        MenuPrincipal();
        break;
    case 5:
        LimpiarPantalla();
        MenuPrincipal();
        break;
    }
    return 0;
}

int MenuUsuario()
{
    LimpiarPantalla();
    Logo();
    cout << setfill(':')
         << "|"
         << setw(80) << left << "Opciones Generales"
         << "|"
         << endl
         << setfill(' ')
         << "|"
         << setw(40) << left << "Buscar Departamento"
         << setw(40) << right << "[1]"
         << "|"
         << endl
         << "|"
         << setw(40) << left << "Mostrar Candidatos"
         << setw(40) << right << "[2]"
         << "|"
         << endl
         << "|"
         << setw(40) << left << "Votar"
         << setw(40) << right << "[3]"
         << "|"
         << endl
         << setfill(':')
         << "|"
         << setw(40) << left << "Volver al principal"
         << setw(40) << right << "[4]"
         << "|"
         << endl
         << endl;
    int opcion = 0;
    cin >> opcion;
    string DUI = "";
    string salir = "";
    VaciarDatosVotantes();
    switch (opcion)
    {
    case 1:
        LimpiarPantalla();
        cout << "Ingrese su numero de DUI [8 digitos]: ";
        cin >> DUI;
        if (ValidarRegistroDUI(DUI) == true)
        {
            cout << BuscarNombreDepartamento(DUI) << endl;
            cout << "Ingrese [ok] para volver al menu principal..." << endl;
            cin >> salir;
            MenuPrincipal();
        }
        else
        {
            cout << "El DUI ingresado no ha sido registrado, escriba [ok] para continuar..." << endl;
            cin >> salir;
            MenuPrincipal();
        }
        break;
    case 2:
        LimpiarPantalla();
        cout << "ingrese su numero de DUI [8 digitos]: ";
        cin >> DUI;
        if (ValidarRegistroDUI(DUI) == true)
        {
            MostrarCandidatos(BuscarIdDepartamento(DUI));
            cout << "Ingrese [ok] para volver al menu principal..." << endl;
            cin >> salir;
            MenuPrincipal();
        }else{
            cout << "Su DUI no esta registrado, ingrese [ok] para volver al menu principal..." << endl;
            cin >> salir;
            MenuPrincipal();
        }

        break;
    case 3:
        LimpiarPantalla();
        cout << "Ingrese su numero de DUI [8 digitos]: ";
        cin >> DUI;
        if (ValidarVotacion(DUI) == true)
        {
            Votar(BuscarIdDepartamento(DUI));
            RegistrarVoto(DUI);
            cout << "Voto Realizado Exitosamente, escriba [ok] para volver al menu principal..." << endl;
            cin >> salir;
            MenuPrincipal();
        }
        else
        {
            cout << "Usted ya ha votado anteriormente o su DUI no esta registrado, Escriba [ok] para volver al menu principal..." << endl;
            cin >> salir;
            MenuPrincipal();
        }
        break;
    case 4:
        LimpiarPantalla();
        MenuPrincipal();
        break;
    }
    return 0;
}

void LimpiarPantalla()
{
#ifdef _WIN32
    // Limpiar pantalla para windows
    system("cls");
#elif __linux__
    // Limpiar pantalla para linux
    system("clear");
#else
    system("cls");
#endif
}

void Logo()
{
    cout << "\t\t\t:::::::::::  ::::::::  ::::::::::\n"
            "\t\t\t    :::     :::    ::: :::       \n"
            "\t\t\t    :::     :::        :::       \n"
            "\t\t\t    :::     :::::::::: ::::::::  \n"
            "\t\t\t    :::            ::: :::       \n"
            "\t\t\t    :::     :::    ::: :::       \n"
            "\t\t\t    :::      ::::::::  ::::::::::\n"
         << endl
         << endl;
}