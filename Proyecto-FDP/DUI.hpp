#include <iostream>
#include <fstream>
#include <vector>
#include <string>
#include <iomanip>

using namespace std;

// Estructura para manejar votantes
struct Votantes
{
    int idDepartamento;
    string DUI;
    string nombreDepartamento;
    int estadoVotacion;
};

vector<Votantes> votantes;

// Estructura para manejar departamentos
struct Departamentos
{
    int idDep;
    string nombreD;
} departamentos[14];

// definicion de funciones

string IngresoDUI();
int GuardarEnVotantes();
void GuardarEnDepartamentos();
bool ValidarDUI(string DUI);
bool ValidarRegistroDUI(string DUI);
bool ValidarVotacion(string DUI);
int BuscarIdDepartamento(string DUI);
string BuscarNombreDepartamento(string DUI);
void RegistrarVotantes();
void RegistrarVoto(string DUI);
void MostrarInformacionDUI(string DUI);
void ActualizarEstadoVoto(string DUI);
void GenerarArchivoDeVotantes();
void VaciarDatosVotantes();

// funcion de ingreso de DUI retorna un string con el DUI ingresado
string IngresoDUI()
{
    string DUI;
    cout << "Ingrese su numero de DUI 8 digitos: ";
    cin >> DUI;
    // Esto valida si el DUI ya voto y si el DUI es valido en el pais
    if (ValidarDUI(DUI) == true && ValidarRegistroDUI(DUI) == true)
    {
        return DUI;
    }
    else if (ValidarRegistroDUI(DUI) != true)
    {
        cout << "Usted ya ha votado anteriormente" << endl;
    }
    else
    {
        cout << "DUI no valido deben ser 8 digitos y los ultimos dos tienen que ser menores que 14" << endl;
    }

    return "";
}

// Funcion que guarda los datos del archivo en la estructura Votantes
int GuardarEnVotantes()
{
    int idDepartamento;
    string DUI;
    string nombreDepartamento;
    int estadoVotacion;

    Votantes votante;

    fstream votantes_registro;
    votantes_registro.open("votantes.txt", ios::in);
    while (votantes_registro >>
               votante.idDepartamento >>
               votante.DUI >>
               votante.estadoVotacion &&
           getline(votantes_registro, votante.nombreDepartamento))
    {
        votantes.push_back(votante);
    }
    return 0;
}

// funcion que guarda datos en estructura de departamentos
void GuardarEnDepartamentos()
{
    fstream DepartamentosF("Dep.txt", ios::in);

    for (int i = 0; i < 14; i++)
    {
        DepartamentosF >> departamentos[i].idDep;
        getline(DepartamentosF, departamentos[i].nombreD);
    }
}

// Funcion que valida si un DUI esta escrito correctamente
bool ValidarDUI(string DUI)
{
    string ultimosDigitos;
    // agarra los ultimos numeros para compararlos
    int size = DUI.size();
    ultimosDigitos.push_back(DUI[size - 2]);
    ultimosDigitos.push_back(DUI[size - 1]);

    // Se convierte a int con la funcion stoi || string to int
    if (DUI.size() == 8 && stoi(ultimosDigitos) <= 14)
    {
        return true;
    }
    return false;
}

// Funcion que valida si un DUI ha sido registrado por el administrador
bool ValidarRegistroDUI(string DUI)
{
    GuardarEnVotantes();
    int size = votantes.size();
    for (int i = 0; i < size; i++)
    {
        if (votantes[i].DUI == DUI)
        {
            return true;
        }
    }
    return false;
}

// Funcion que valida si un DUI ha votado
bool ValidarVotacion(string DUI)
{
    GuardarEnVotantes();
    int size = votantes.size();
    for (int i = 0; i < size; i++)
    {
        if (votantes[i].DUI == DUI && votantes[i].estadoVotacion == 0)
        {
            return true;
        }
    }
    return false;
}

// Funcion que devuelve el Id de un departamento de un DUI especifico
int BuscarIdDepartamento(string DUI)
{
    GuardarEnDepartamentos();
    string ultimosDigitos;
    // agarra los ultimos numeros para compararlos
    ultimosDigitos.push_back(DUI[6]);
    ultimosDigitos.push_back(DUI[7]);
    // cout << numeroDepartamento;
    for (int i = 0; i < 14; i++)
    {
        if (departamentos[i].idDep == stoi(ultimosDigitos))
        {
            return departamentos[i].idDep;
        }
    }
    return 1;
}

// Funcion que retorna el nombre del departamento de un DUI que se especifique
string BuscarNombreDepartamento(string DUI)
{
    GuardarEnDepartamentos();
    string ultimosDigitos;
    // agarra los ultimos numeros para compararlos
    ultimosDigitos.push_back(DUI[6]);
    ultimosDigitos.push_back(DUI[7]);
    // cout << numeroDepartamento;
    for (int i = 0; i < 14; i++)
    {
        if (departamentos[i].idDep == stoi(ultimosDigitos))
        {
            return departamentos[i].nombreD;
        }
    }
    return "";
}

// Funcion para que el administrador registre votantes
void RegistrarVotantes()
{
    GuardarEnVotantes();
    Votantes votante;

    fstream RegistroVotantes("votantes.txt", ios::app);

    string DUI;
    cout << "ingrese numero de DUI del votante: ";
    cin >> DUI;

    if (ValidarDUI(DUI) == true && ValidarRegistroDUI(DUI) != true)
    {
        RegistroVotantes << BuscarIdDepartamento(DUI)
                         << " "
                         << DUI
                         << " "
                         << 0
                         << ""
                         << BuscarNombreDepartamento(DUI)
                         << endl;
        GuardarEnVotantes();
    }
    else
    {
        cout << "El DUI que ingreso no es valido o ya esta registrado " << endl;
    }
}

// Registra el estado de votacion del DUI que se especifica
void RegistrarVoto(string DUI)
{
    VaciarDatosVotantes();
    ActualizarEstadoVoto(DUI);
    int size = votantes.size();
    fstream RegistroDUI("votantes.txt", ios::out);
    for (int i = 0; i < size; i++)
    {
        RegistroDUI << votantes[i].idDepartamento
                    << " "
                    << votantes[i].DUI
                    << " "
                    << votantes[i].estadoVotacion
                    << ""
                    << votantes[i].nombreDepartamento << endl;
    }
}

void MostrarInformacionDUI(string DUI)
{
    GuardarEnVotantes();
    int size = votantes.size();
    cout << size << endl;
    Votantes votante;

    cout << setfill(':')
         << "|"
         << setw(82) << left << "Informacion del DUI"
         << "|"
         << setfill(' ')
         << endl
         << "|"
         << setw(20) << left << "DUI"
         << "|"
         << setw(30) << left << "Departamento"
         << "|"
         << setw(30) << left << "Votacion"
         << "|"
         << endl;
    string votacion;
    if (ValidarRegistroDUI(DUI) == true)
    {
        for (int i = 0; i < size; i++)
        {
            if (DUI == votantes[i].DUI)
            {
                votante.DUI = votantes[i].DUI;
                votante.idDepartamento = votantes[i].idDepartamento;
                votante.nombreDepartamento = votantes[i].nombreDepartamento;
                votante.estadoVotacion = votantes[i].estadoVotacion;
                if(votante.estadoVotacion == 1){
                    votacion = "Realizada";
                }else{
                    votacion = "No Realizada";
                }
            }
        }
        cout << setfill(' ')
             << "|"
             << setw(20) << left << votante.DUI
             << "|"
             << setw(30) << left << votante.nombreDepartamento
             << "|"
             << setw(30) << left << votacion
             << "|"
             << endl;
    }
    else
    {
        cout << setfill(' ')
             << "|"
             << setw(82) << left << "DUI no registrado"
             << "|"
             << endl;
    }
}

// Funcion que actualiza el estado de voto (Dentro de struct)
void ActualizarEstadoVoto(string DUI)
{
    GuardarEnVotantes();
    int Estado;
    for (int i = 0; i < votantes.size(); i++)
    {
        if (votantes[i].DUI == DUI && votantes[i].estadoVotacion == 0)
        {
            Estado = i;
            break;
        }
    }

    votantes[Estado].estadoVotacion = 1;
}

// Funcion que genera el archivo Registro_Votantes.txt
void GenerarArchivoDeVotantes()
{
    fstream registro_votantes;
    GuardarEnVotantes();
    int size = votantes.size();
    registro_votantes.open("Registro_Votantes.txt", ios::out);

    if (registro_votantes.is_open())
    {
        registro_votantes << setfill('-')
                          << "|"
                          << setw(10) << left << "DUI"
                          << "|"
                          << setw(40) << left << "Departamento"
                          << "|"
                          << setw(15) << left << "Votacion"
                          << "|"
                          << endl;
        for (int i = 0; i < size; i++)
        {
            registro_votantes << setfill('-')
                              << "|"
                              << setw(10) << left << votantes[i].DUI
                              << "|"
                              << setw(40) << left << votantes[i].nombreDepartamento
                              << "|";
            if (votantes[i].estadoVotacion == 1)
            {
                registro_votantes << setw(15) << left << "realizada"
                                  << "|" << endl;
            }
            else
            {
                registro_votantes << setw(15) << left << "no realizada"
                                  << "|" << endl;
            }
        }
    }
}

//Funcion que libera memoria del vector antes de hacer un nuevo cambio
void VaciarDatosVotantes(){
    while(!votantes.empty()){
        votantes.pop_back();
    }
}