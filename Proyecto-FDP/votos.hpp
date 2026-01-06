#include <iostream>
#include <fstream>
#include <iomanip>

using namespace std;

// Crear funciones de votos acordadas
//  Estructura para manejar votos en memoria temporal

struct Candidato
{
    int departamento;
    int id;
    string nombre;
    string apellido;
    string partido;
    int votos;
} candidatos[56];

/*
    Definicion de funciones para funcionamiento general de votos
*/
// Lee los datos del archivo de Registro_Votos y los serializa en la struct candidatos
void GuardarEnCandidatos();
// Segun lee los datos del struct candidatos busca que candidato es por el que votaron
// y actualiza sus votos, incremento en 1
void ActualizarVotos(int idCandidato);
// Muestra los candidatos, si solo eso :/
void MostrarCandidatos(int departamento);
// Funcion que sirve para votar y ya dentro de ella tiene que estar llamada la funcion de
// ActualizarVotos
bool Votar(int departamentoId);

// Funcion que muestra los resultados al administrador
void MostrarResultados();
void ResultadosPorPartido();
int Resultados();

void LimpiarPantallaVotos();

void GuardarEnCandidatos()
{
    fstream Votos("Registro_Votos.txt", ios::in);
    if (Votos.is_open())
    {
        for (int i = 0; i < 56; i++)
        {
            Votos >> candidatos[i].departamento >> candidatos[i].id >> candidatos[i].nombre >> candidatos[i].apellido >> candidatos[i].votos >> candidatos[i].partido;
        }
        Votos.close();
    }
}

void ActualizarVotos(int idCandidato)
{
    idCandidato--;
    GuardarEnCandidatos();
    fstream Votos("Registro_Votos.txt", ios::out);
    if (Votos.is_open())
    {
        for (int i = 0; i < 56; i++)
        {
            if (i == idCandidato)
            {
                // Esta de esta forma porque no confio en que este el candidatos[i].votos++ :/
                candidatos[i].votos = candidatos[i].votos + 1;
            }
            Votos << candidatos[i].departamento
                  << " "
                  << candidatos[i].id
                  << " "
                  << candidatos[i].nombre
                  << " "
                  << candidatos[i].apellido
                  << " "
                  << candidatos[i].votos
                  << " "
                  << candidatos[i].partido
                  << endl;
        }
        Votos.close();
    }
}

void MostrarCandidatos(int departamento)
{
    GuardarEnCandidatos();
    cout << setfill(':')
         << "|"
         << setw(83) << left << "Mostrar Candidatos"
         << "|"
         << endl
         << setfill(' ')
         << "|"
         << setw(20) << left << "Id"
         << "|"
         << setw(20) << left << "Nombre"
         << "|"
         << setw(20) << left << "Apellido"
         << "|"
         << setw(20) << left << "Partido"
         << "|"
         << endl;
    for (int i = 0; i < 56; i++)
    {
        // Esto hace que se muestre unicamente los candidatos de un departamento
        if (candidatos[i].departamento == departamento)
        {
            cout << setfill(':')
                 << "|"
                 << setw(20) << left << candidatos[i].id
                 << "|"
                 << setw(20) << left << candidatos[i].nombre
                 << "|"
                 << setw(20) << left << candidatos[i].apellido
                 << "|"
                 << setw(20) << left << candidatos[i].partido
                 << "|"
                 << endl;
        }
    }
}

bool Votar(int departamentoId)
{
    // Esto solo hace que se muestre de que va cada columna de datos en mostrar
    // candidatos
    // esto llama a la funcion de candidatos
    MostrarCandidatos(departamentoId);
    int candidato = 0;
    cout << "ingrese el numero del candidato por el que quiere votar" << endl;
    cin >> candidato;
    ActualizarVotos(candidato);

    return true;
}

void ResultadosPorPartido()
{
    LimpiarPantallaVotos();
    GuardarEnCandidatos();
    int votosArena = 0, votosFmln = 0, votosGana = 0, votosNI = 0;
    for (int i = 0; i < 56; i++)
    {
        if (candidatos[i].partido == "Arena")
        {
            votosArena += candidatos[i].votos;
        }
        else if (candidatos[i].partido == "FMLN")
        {
            votosFmln += candidatos[i].votos;
        }
        else if (candidatos[i].partido == "GANA")
        {
            votosGana += candidatos[i].votos;
        }
        else if (candidatos[i].partido == "NI")
        {
            votosNI += candidatos[i].votos;
        }
    }

    cout << setfill('-')
         << "|"
         << setw(81) << left << "Resultados por partido"
         << "|"
         << endl
         << "|"
         << setw(40) << left << "Partido"
         << "|"
         << setw(40) << left << "Votos"
         << "|"
         << endl;

    cout << setfill('-')
         << "|"
         << setw(40) << left << "Arena"
         << "|"
         << setw(40) << left << votosArena
         << "|"
         << endl
         << "|"
         << setw(40) << left << "FMLN"
         << "|"
         << setw(40) << left << votosFmln
         << "|"
         << endl
         << "|"
         << setw(40) << left << "Gana"
         << "|"
         << setw(40) << left << votosGana
         << "|"
         << endl
         << "|"
         << setw(40) << left << "Nuevas Ideas"
         << "|"
         << setw(40) << left << votosNI
         << "|"
         << endl;
}

int Resultados()
{
    cout << setfill(':')
         << "|"
         << setw(80) << left << "Elija estilo de resultados"
         << "|"
         << endl
         << setfill(' ')
         << "|"
         << setw(40) << left << "Por candidatos"
         << setw(40) << right << "[1]"
         << "|"
         << endl
         << "|"
         << setw(40) << left << "Por partido"
         << setw(40) << right << "[2]"
         << "|"
         << endl;
    int option = 0;
    cin >> option;
    switch(option){
        case 1:
            MostrarResultados();
            break;
        case 2:
            ResultadosPorPartido();
            break;
        default:
            return 0;
            break;
    }
    return 0;
}

void MostrarResultados()
{
    LimpiarPantallaVotos();
    GuardarEnCandidatos();
    cout << setfill(':')
         << "|"
         << setw(83) << left << "Resultados por candidato"
         << "|"
         << endl
         << "|"
         << setw(20) << left << "Nombre"
         << "|"
         << setw(20) << left << "Apellido"
         << "|"
         << setw(20) << left << "Votos"
         << "|"
         << setw(20) << left << "Partido"
         << "|"
         << endl;
    for (int i = 0; i < 56; i++)
    {
        cout << setfill(' ')
             << "|"
             << setw(20) << left << candidatos[i].nombre
             << "|"
             << setw(20) << left << candidatos[i].apellido
             << "|"
             << setw(20) << left << candidatos[i].votos
             << "|"
             << setw(20) << left << candidatos[i].partido
             << "|"
             << endl;
    }
}

void LimpiarPantallaVotos()
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