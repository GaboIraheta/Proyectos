#include "FuncionesDeUsuario.hpp"
#include <fstream>
#include <sstream>
#include <windows.h>

using namespace std;

void archivoUsuarios(string, NodoUsuarios *);
void leerArchivoUsuarios(string);
void datosArchivoAusuarios(string fileName, Usuarios &usuario);
void archivoReservas(string, NodoReservas *);
void leerArchivoReservas(string);
void datosArchivoAReserva(string, Reservas &reserva, bool isAdmin, bool isListaGeneral);
void crearArchivo(string, NodoReservas *);
void eliminarArchivoReservas(string);
void eliminarEnGeneral(string, string);
// void crearCarpetaSiNoExiste(const string&);

void archivoUsuarios(string fileName, NodoUsuarios *lista) // Funcion que genera y reescribe el archivo
// de usuarios y guarda los datos de cada usuario en dicho archivo txt
{
    ofstream file(fileName, ios::out); // Se abre el archivo en modo de escritura
    if (!file.is_open())
    {
        cout << "Error en la apertura del archivo \n" /*Manejo de error en la apertura del archivo
        que finaliza la funcion*/
             << "Presione Enter para continuar . . .\n";
        cin.ignore();
        cin.get();
        return;
    }
    while (lista != nullptr)
    { // Se recorre la lista y se guardan los datos almacenados en un archivo txt de usuarios
        file << lista->usuario.user << "-"
             << lista->usuario.password << "-";
        if (lista->usuario.isAdmin)
        {
            file << "Administrador" << endl;
        }
        else
        {
            file << "Normal" << endl;
        }
        lista = lista->siguiente;
    }
    file.close();
}

void leerArchivoUsuarios(string fileName) // Funcion que lee los datos del archivo de usuarios
{
    ifstream file(fileName, ios::in); // Se crea el objeto para el manejo del archivo
    string line;                      // Se declara la variable linea para imprimir los datos almacenados en el archivo
    if (!file.is_open())
    {
        cout << "Error en la apertura del archivo \n"
             << "Presione Enter para continuar . . .\n";
        cin.ignore();
        cin.get();
        return;
    }
    while (getline(file, line))
    {
        // Bucle que persiste mientras existan caracteres en las lineas del archivo txt de usuarios
        cout << line << endl;
    }
    file.close();
}

void datosArchivoAusuarios(string fileName, Usuarios &usuario) // Funcion que extrae los datos guardados
// en el archivo txt de usuarios y los inserta en la lista de usuarios
{
    ifstream file(fileName, ios::in); // Se declara el objeto para el manejo del archivo
    string line;                      /*Se declara una variable linea para recorrer el archivo y pase los datos del txt
                       hacia la lista nuevamente*/
    if (!file.is_open())
    {
        cout << "Error en la apertura del archivo \n"
             << "Presione Enter para continuar . . .\n";
        cin.ignore();
        cin.get();
        return;
    }
    while (getline(file, line)) // Recorre cada linea del archivo
    {
        stringstream informacion(line); // Creacion de objeto que guarda la linea que se esta recorriendo
        string user, password, isAdmin;
        const char SEPARADOR = '-';
        if (getline(informacion, user, SEPARADOR) && // Se guarda en su respectiva variable la cadena encontrada
                                                     // hasta antes del separador
            getline(informacion, password, SEPARADOR) &&
            getline(informacion, isAdmin, SEPARADOR))
        {
            usuario.user = user; // Se asignan las cadenas encontradas a cada campo del struct de usuarios
            // segun la informacion que debe guardar
            usuario.password = password;
            if (isAdmin == "Administrador") // Si la cadena es administrador guarda un true y si es normal guarda un false
            {
                usuario.isAdmin = true;
            }
            else
            {
                usuario.isAdmin = false;
            }
            user = "";
            password = "";
            isAdmin = "";
        }
        insertar(true, true); /*Se insertan los datos nuevamente en la lista de usuarios,
        eso por cada iteracion, por cada linea existente en el archivo txt de usuarios*/
    }
    file.close();
}

void crearArchivo(string filename, NodoReservas *lista) // Simplementa crea un archivo con el nombre del usuario
// creado, para que no hayan errores en el primer inicio de sesion de un usuario nuevo
{
    string foldername = "Reservas";
    string linea = " ";
    ofstream file(foldername + "/" + filename, ios::out);
    if (!file.is_open())
    {
        cout << "Error en la apertura del archivo \n"
             << "Presione Enter para continuar . . .\n";
        cin.ignore();
        cin.get();
        return;
    }
    file.close();
}

void archivoReservas(string fileName, NodoReservas *lista) // Funcion que genera el archivo de reservas de
// un usuario especifico y guarda los datos de sus reservas realizadas, y crea el general tambien
//  El parametro folderName recibiria el nombre del usuario que esta haciendo uso del sistema en ese momento
{
    string foldername = "Reservas";
    // string comando = "mkdir " + foldername; // Se crea el comando para crear la carpeta
    // system(comando.c_str());                // Crea carpetas si no existen
    //  crearCarpetaSiNoExiste(folderName);
    ofstream file(foldername + "/" + fileName, ios::out); // El nombre de la carpeta será el nombre del usuario
    if (!file.is_open())
    { // Manejo de error en la apertura del archivo
        cout << "Error en la apertura del archivo \n"
             << "Presione Enter para continuar . . .\n";
        cin.ignore();
        cin.get();
        return;
    }
    while (lista != nullptr)
    { // Se guardan los datos de la lista de reservas en un archivo txt dentro de una carpeta con
        // el nombre del usuario
        file << lista->reserva.nombre << "-"
             << lista->reserva.numero << "-"
             << lista->reserva.tipo << "-"
             << lista->reserva.ubicacion << "-"
             << lista->reserva.capacidad << endl;
        lista = lista->siguiente;
    }
    file.close();
}

void leerArchivoReservas(string fileName)
{
    string foldername = "Reservas";
    ifstream file(foldername + "/" + fileName, ios::in);
    string line;
    if (!file.is_open())
    { // Manejo del error en la apertura del archivo
        cout << "Error en la apertura del archivo \n"
             << "Presione Enter para continuar . . .\n";
        cin.ignore();
        cin.get();
        return;
    }
    while (getline(file, line))
    {
        cout << line << endl;
    }
    file.close();
}

void datosArchivoAReserva(string fileName, Reservas &reserva, bool isAdmin, bool isListaGeneral) // Funcion que extrae
// los datos del archivo txt del usuario a su lista de reservas y a la general (se llama dos veces para recuperar ambas listas)
{
    string foldername = "Reservas";
    ifstream file(foldername + "/" + fileName, ios::in); // Abre la carpeta y el archivo txt
    // de reservas del usuario en uso
    string line;
    if (!file.is_open())
    { // Manejo de errores
        cout << "Error en la apertura del archivo \n"
             << "Presione Enter para continuar . . .\n";
        cin.ignore();
        cin.get();
        return;
    }
    const char SEPARADOR = '-'; // Se declara un separador que es igual al caracter que separa los
    // datos de las reservas en el archivo txt
    while (getline(file, line)) // Bucle que persiste mientras existen caracteres en las lineas del txt
    {
        stringstream informacion(line);                    // Se crea un objeto que guarda la linea completa
        string nombre, numero, tipo, ubicacion, capacidad; // Variables provisionales para guardar
        // los valores de los datos de las reservas
        if (getline(informacion, nombre, SEPARADOR) && // El condicional guarda los datos de las reservas
                                                       // en las variables provisionales, recorriendo la linea y guardando los caracteres de los espacios
                                                       // en la linea hasta llegar al separador
            getline(informacion, numero, SEPARADOR) &&
            getline(informacion, tipo, SEPARADOR) &&
            getline(informacion, ubicacion, SEPARADOR) &&
            getline(informacion, capacidad, SEPARADOR))
        { // Si todo sucede correctamente entonces los valores de las variables provisionales se almacenan
            // en el struct de reservas para insertar en la lista de reservas nuevamente
            reserva.nombre = nombre;
            reserva.numero = stoi(numero); // Se convierte la cadena al entero correspondiente
            reserva.tipo = tipo;
            reserva.ubicacion = ubicacion;
            reserva.capacidad = stoi(capacidad);
            if (isListaGeneral) // Se solicita un parametro que verifica si es la lista general para insertarlos
            // en la lista general o en la propia de reservas del usuario
            {
                insertarGeneral();
            }
            else
            {
                insertar(false, isAdmin);
            }
        }
        else
        {
            // Manejo de error por formato inesperado en la linea
            cout << "Error: Formato inesperado en la linea, no se pudieron recuperar los datos correctamente \n";
        }
    }
    file.close();
}

void eliminarArchivoReservas(string nombre) // Funcion que elimina un archivo de un usuario
// que ha sido editado su nombre de usuario o si el usuario ha sido eliminado
{
    string folderName = "Reservas";

    const char *path = (folderName + "/" + nombre).c_str();

    int verificador = remove(path);
    if (verificador != 0)
    {
        cout << bold << Color(1) << "Error al borrar el archivo" << reset << endl;
        return;
    }
}

void eliminarEnGeneral(string nombre, string _username) // Funcion que elimina los datos de reservas
// de un usuario eliminado del archivo general de reservas
{
    archivoReservas(_username, listaReservas);
    vaciarListaReservas(listaReservas);

    datosArchivoAReserva(nombre, reserva, true, false);

    NodoReservas *temp1 = listaReservas; // lista de reservas del usuario
    NodoReservas *temp2 = listaGeneral;  // lista de reservas general

    while (temp1 != nullptr) // por cada reserva en la lista del usuario
    {

        while (temp2 != nullptr && temp2->reserva.nombre != temp1->reserva.nombre) // recorrerá la lista general
        {
            temp2 = temp2->siguiente;
        }

        if (temp2 != nullptr)
        {
            if (temp2->anterior != nullptr)
            {
                temp2->anterior->siguiente = temp2->siguiente;
            }
            else
            {
                listaGeneral = temp2->siguiente;
            }

            if (temp2->siguiente != nullptr)
            {
                temp2->siguiente->anterior = temp2->anterior;
            }

            delete temp2;
            temp2 = listaGeneral; // lista de reservas general
        }
        temp1 = temp1->siguiente;
    }

    vaciarListaReservas(listaReservas);
    datosArchivoAReserva(_username, reserva, true, false);
}