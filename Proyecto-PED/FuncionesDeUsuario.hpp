#include <iostream>
#include <iomanip>
#include "chalk.hpp"
using namespace std;

struct Usuarios
{
    string user;
    string password;
    bool isAdmin;
} usuario;

struct NodoUsuarios
{
    Usuarios usuario;
    NodoUsuarios *siguiente;
    NodoUsuarios *anterior;
};

NodoUsuarios *listaUsuarios = nullptr;

struct Reservas
{
    string nombre;
    int numero;
    string ubicacion;
    string tipo;
    int capacidad;
} reserva;

struct NodoReservas
{
    Reservas reserva;
    NodoReservas *siguiente;
    NodoReservas *anterior;
};

NodoReservas *listaReservas = nullptr;
NodoReservas *listaGeneral = nullptr;

bool isAdmin(string);
void insertar(bool, bool);
void eliminar(bool, bool, string);
bool busqueda(bool, bool, string);
void editar(bool, bool, string &, bool &);
NodoUsuarios *dato(NodoUsuarios *, string);
NodoReservas *dato(NodoReservas *, string);
void imprimir(bool, bool);
bool empty(bool);
void buscar(bool, bool, string);
void vaciarListaUsuarios(NodoUsuarios *&lista);
void vaciarListaReservas(NodoReservas *&lista);
void insertarGeneral();
bool busquedaGeneral(string);
bool emptyGeneral();
void imprimirGeneral();
bool evaluarNumero(int);

bool isAdmin(string name) // Funcion que verifica si el usuario que inicia sesion es un administrador
{
    NodoUsuarios *temp = listaUsuarios;

    while (temp != nullptr)
    {
        if (temp->usuario.user == name && temp->usuario.isAdmin)
        {
            return true; // Retorna verdadero cuando encuentra coincidencia en el nombre de usuario
            // y el valor de isAdmin es true
        }

        temp = temp->siguiente;
    }

    return false;
}

void insertar(bool isListaUsuarios, bool isAdmin) // Funcion para insertar datos a las listas
{
    if (isListaUsuarios && isAdmin) // Este if como en todos los metodos de listas maneja si
    // se va a insertar en lista de usuarios o en lista de reservas, y ademas verifica que si
    // es la lista de usuarios sea un administrador, si no es administrador no dara acceso
    {
        NodoUsuarios *newNodo = new NodoUsuarios();
        newNodo->usuario.user = usuario.user;
        newNodo->usuario.password = usuario.password;
        newNodo->usuario.isAdmin = usuario.isAdmin;
        newNodo->siguiente = nullptr;
        newNodo->anterior = nullptr;
        if (empty(isListaUsuarios))
        {
            listaUsuarios = newNodo;
        }
        else
        {
            newNodo->siguiente = listaUsuarios;
            listaUsuarios->anterior = newNodo;
            listaUsuarios = newNodo;
        }
    }
    else if (!isListaUsuarios) // Aqui se inserta en las listas de reservas propias de un usuario normal
    // o de un administrador, y se agregan ademas, siempre, a la lista general cada reserva sin importar
    // el tipo de usuario, con la funcion de insertarGeneral (abajo)
    {
        NodoReservas *newNodo = new NodoReservas();
        newNodo->reserva.nombre = reserva.nombre;
        newNodo->reserva.numero = reserva.numero;
        newNodo->reserva.tipo = reserva.tipo;
        newNodo->reserva.ubicacion = reserva.ubicacion;
        newNodo->reserva.capacidad = reserva.capacidad;
        newNodo->siguiente = nullptr;
        newNodo->anterior = nullptr;
        if (empty(isListaUsuarios))
        {
            listaReservas = newNodo;
        }
        else
        {
            newNodo->siguiente = listaReservas;
            listaReservas->anterior = newNodo;
            listaReservas = newNodo;
        }
    }
    else
    {
        cout << bold << Color(1) << "Este usuario no tiene acceso a los registros de usuario \n"
             << reset;
        return;
    }
}

void insertarGeneral() // Funcion que inserta los datos de reserva en una lista general de reservas
// a la cual solo tienen acceso los administradores (para verla nada mas, no puede ser manipulada directamente)
{
    NodoReservas *newNodo = new NodoReservas();

    newNodo->reserva.nombre = reserva.nombre;
    newNodo->reserva.numero = reserva.numero;
    newNodo->reserva.tipo = reserva.tipo;
    newNodo->reserva.ubicacion = reserva.ubicacion;
    newNodo->reserva.capacidad = reserva.capacidad;
    newNodo->siguiente = nullptr;
    newNodo->anterior = nullptr;

    if (emptyGeneral())
    {
        listaGeneral = newNodo;
    }
    else
    {
        newNodo->siguiente = listaGeneral;
        listaGeneral->anterior = newNodo;
        listaGeneral = newNodo;
    }
}

void eliminar(bool isListaUsuarios, bool isAdmin, string nombre) // Funcion que elimina usuarios
// o reservas de la lista respectiva al hacer la verificacion en el if
{
    if (isListaUsuarios && isAdmin)
    {
        if (!empty(isListaUsuarios))
        {
            NodoUsuarios *temp = listaUsuarios;
            while (temp != nullptr && temp->usuario.user != nombre)
            {
                temp = temp->siguiente;
            }

            if (temp != nullptr)
            {
                if (temp->anterior != nullptr)
                {
                    temp->anterior->siguiente = temp->siguiente;
                }
                else
                {
                    listaUsuarios = temp->siguiente;
                }

                if (temp->siguiente != nullptr)
                {
                    temp->siguiente->anterior = temp->anterior;
                }

                delete temp;
                temp = nullptr;

                cout << bold << Color(6) << "Usuario " << nombre << " eliminado correctamente\n"
                     << "Archivo de reservas de usuario " << nombre << " eliminado\n"
                     << reset;
            }
            else
            {
                cout << bold << Color(1) << "No se econtro el usuario\n"
                     << reset;
                return;
            }
        }
        else
        {
            cout << bold << Color(1) << "La lista de usuarios esta vacia \n"
                 << reset;
            return;
        }
    }
    else if (!isListaUsuarios) // Aqui en eliminar ha sido agregada la lógoca para eliminar reservas
    // de la lista de reservas del propio usuario en uso del programa y de la lista general, modificando ambas
    {
        if (!empty(isListaUsuarios))
        {
            NodoReservas *temp = listaReservas;
            NodoReservas *temp2 = listaGeneral;

            // Encuentra el nodo a eliminar en lista de reservas del usuario en uso
            while (temp != nullptr && temp->reserva.nombre != nombre)
            {
                temp = temp->siguiente;
            }

            // Encuentra el nodo a eliminar en la lista general de reservas
            while (temp2 != nullptr && temp2->reserva.nombre != nombre)
            {
                temp2 = temp2->siguiente;
            }

            if (temp != nullptr && temp2 != nullptr)
            {
                // Elimina el nodo en lista de reservas del usuario en uso
                if (temp->anterior != nullptr)
                {
                    temp->anterior->siguiente = temp->siguiente;
                }
                else
                {
                    listaReservas = temp->siguiente;
                }

                if (temp->siguiente != nullptr)
                {
                    temp->siguiente->anterior = temp->anterior;
                }

                delete temp;
                temp = nullptr;

                // Elimina el nodo en lista general de reservas
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
                temp2 = nullptr;

                cout << bold << Color(6) << "Reserva eliminada correctamente\n"
                     << reset;
            }
            else
            {
                cout << bold << Color(1) << "No se encontro la reserva\n"
                     << reset;
                return;
            }
        }
        else
        {
            cout << bold << Color(1) << "Lista de reservas vacia\n"
                 << reset;
            return;
        }
    }
    else
    {
        cout << bold << Color(1) << "Este usuario no tiene acceso al registro de usuarios\n"
             << reset;
        return;
    }
    return;
}

bool busqueda(bool isListaUsuarios, bool isAdmin, string nombre) // Funcion que realiza una busqueda
// de un usuario o reserva en especifico para verificar que este exista dentro de la lista
{
    if (isListaUsuarios && isAdmin)
    {

        if (!empty(isListaUsuarios))
        {
            NodoUsuarios *nodo = listaUsuarios;
            while (nodo != nullptr)
            {
                if (nodo->usuario.user == nombre)
                {
                    return true;
                }
                nodo = nodo->siguiente;
            }
            return false;
        }
        else
        {
            cout << bold << Color(1) << "Lista de usuarios vacia \n"
                 << reset;
            return false;
        }
    }
    else if (!isListaUsuarios)
    {
        if (!empty(isListaUsuarios))
        {
            NodoReservas *nodo = listaReservas;
            while (nodo != nullptr)
            {
                if (nodo->reserva.nombre == nombre)
                {
                    return true;
                }
                nodo = nodo->siguiente;
            }
            return false;
        }
        else
        {
            cout << bold << Color(1) << "Lista de reservas vacia \n"
                 << reset;
            return false;
        }
    }
    else
    {
        cout << bold << Color(1) << "Este usuario no tiene acceso al registro de usuarios \n"
             << reset;
        return false;
    }
    return false;
}

bool busquedaGeneral(string nombre) // Misma funcion que la anterior pero para la lista general
// asegurando un buen funcionamiento de los metodos que requieren verificacion de que exista un nodo
{
    if (!emptyGeneral())
    {
        NodoReservas *nodo = listaGeneral;
        while (nodo != nullptr)
        {
            if (nodo->reserva.nombre == nombre)
            {
                return true;
            }
            nodo = nodo->siguiente;
        }
        return false;
    }

    return false;
}

NodoUsuarios *dato(NodoUsuarios *nodo, string nombre) // Funcion que se encarga de llegar al nodo
// el cual quiere ser editado, retornando el nodo para poder realizar los cambios en los valores
// que el usuario en uso requiera
{
    while (nodo != nullptr)
    {
        if (nodo->usuario.user == nombre)
        {
            return nodo; // Retorna el nodo solicitado para poder hacerle cambios a sus campos
            // al encontrar una coincidencia
        }
        nodo = nodo->siguiente;
    }
    return nullptr;
}

NodoReservas *dato(NodoReservas *nodo, string nombre) // Misma funcion que la anterior
{
    while (nodo != nullptr)
    {
        if (nodo->reserva.nombre == nombre)
        {
            return nodo; // Retorna el nodo solicitado para poder hacerle cambios a sus campos
            // al encontrar una coincidencia
        }
        nodo = nodo->siguiente;
    }
    return nullptr;
}

void editar(bool isListaUsuarios, bool isAdmin, string &nombre, bool &cambio) // Funcion para editar los campos de un
// nodo en especifico de la lista de usuarios o de la lista de reservas
{
    if (isListaUsuarios && isAdmin)
    {
        if (!empty(isListaUsuarios))
        {
            if (busqueda(true, isAdmin, nombre))
            {
                cout << bold << Color(4) << "Ingrese nuevos datos de usuario\n"
                     << reset;
                cout << endl;
                NodoUsuarios *nodo = listaUsuarios;
                nodo = dato(nodo, nombre); // El nodo temporal se igual a la funcion anterior de dato
                cout << bold << Color(7) << "Ingrese el nuevo nombre de usuario: ";
                cin >> nodo->usuario.user;
                cout << "--------------------------------------\n";
                cout << "Ingrese la nueva contrasena del usuario: ";
                cin >> nodo->usuario.password;
                cout << "--------------------------------------\n";
                cout << "Administrador [1] o Usuario [0] ";
                cin >> nodo->usuario.isAdmin;
                cout << "--------------------------------------\n"
                     << reset;
                cout << endl;
                nombre = nodo->usuario.user;
                cambio = true;
            }
            else
            {
                cout << bold << Color(1) << "No se encontro el usuario \n"
                     << reset;
                cambio = false;
            }
        }
        else
        {
            cout << bold << Color(1) << "Lista de usuarios vacia \n"
                 << reset;
            cambio = false;
            return;
        }
    }
    else if (!isListaUsuarios)
    {
        string name, tipo, ubicacion;
        int numero, capacidad;
        if (!empty(isListaUsuarios))
        {
            if (busqueda(false, isAdmin, nombre))
            {
                cout << bold << Color(4) << "Ingrese nuevos datos de reserva\n"
                     << reset;
                cout << endl;
                NodoReservas *nodo = listaReservas;
                nodo = dato(nodo, nombre); // El nodo temporal se iguala la funcion anterior de dato

                NodoReservas *nodo2 = listaGeneral;
                nodo2 = dato(nodo2, nombre);

                cout << bold << Color(7) << "Ingrese el nombre: ";
                cin >> name;
                cout << "--------------------------------------\n";

                cout << "Ingrese el numero de mesa: ";
                cin >> numero;
                cout << "--------------------------------------\n";

                cout << "Ingrese el tipo: ";
                cin >> tipo;
                cout << "--------------------------------------\n";

                cout << "Ingrese la ubicacion: ";
                cin >> ubicacion;
                cout << "--------------------------------------\n";

                cout << "Ingrese la capacidad: ";
                cin >> capacidad;
                cout << "--------------------------------------\n"
                     << reset;

                if (evaluarNumero(numero))
                {
                    cout << endl;
                    cout << bold << Color(1) << "La mesa " << numero << " ya ha sido reservada\n"
                         << "No se pudo completar los cambios en la reserva\n" << reset;
                    return;
                }
                else
                {
                    nodo->reserva.nombre = name;
                    nodo2->reserva.nombre = name;
                    nodo->reserva.numero = numero;
                    nodo2->reserva.numero = numero;
                    nodo->reserva.tipo = tipo;
                    nodo2->reserva.tipo = tipo;
                    nodo->reserva.ubicacion = ubicacion;
                    nodo2->reserva.ubicacion = ubicacion;
                    nodo->reserva.capacidad = capacidad;
                    nodo2->reserva.capacidad = capacidad;
                    cout << endl;
                    cout << bold << Color(6) << "Informacion de reserva editada exitosamente\n"
                         << reset;
                }
            }
            else
            {
                cout << bold << Color(1) << "No se encontro la reserva \n"
                     << reset;
                return;
            }
        }
        else
        {
            cout << bold << Color(1) << "Lista de reservas vacia \n"
                 << reset;
            return;
        }
    }
    else
    {
        cout << bold << Color(1) << "Este usuario no tiene acceso al registro de usuarios \n"
             << reset;
        return;
    }
}

void imprimir(bool isListaUsuarios, bool isAdmin) // Funcion para recorrer las listas de usuarios
// y reservas imprimiendo los campos de cada uno de los nodos
{
    if (isListaUsuarios && isAdmin)
    {
        if (!empty(isListaUsuarios))
        {
            NodoUsuarios *nodo = listaUsuarios;
            cout << bold << Color(4) << "Mostrando informacion de usarios\n"
                 << reset;
            cout << endl;

            cout << bold << Color(3); // Los datos se muestran en formato de tabla
            cout << setw(25) << left << "Nombre de usuario"
                 << setw(15) << left << "Contrasena"
                 << setw(20) << left << "Tipo de usuario" << endl
                 << reset << endl;

            while (nodo != nullptr)
            {
                cout << bold << Color(7); // Los datos se muestran en formato de tabla
                cout << setw(25) << left << nodo->usuario.user
                     << setw(15) << left << nodo->usuario.password;

                if (nodo->usuario.isAdmin) // If para verificar si es un administrador o usuario normal
                // segun su valor de verdad
                {
                    cout << setw(20) << left << "Administrador" << reset << endl;
                }
                else
                {
                    cout << setw(20) << left << "Normal" << reset << endl;
                }
                cout << endl;

                nodo = nodo->siguiente;
            }
            cout << "\n\n";
        }
        else
        {
            cout << bold << Color(1) << "Lista de usuarios vacia \n"
                 << reset;
            return;
        }
    }
    else if (!isListaUsuarios)
    {
        if (!empty(isListaUsuarios))
        {
            NodoReservas *nodo = listaReservas;
            cout << bold << Color(4) << "Mostrando informacion de reservas\n"
                 << reset;
            cout << endl;

            cout << bold << Color(3);
            cout << setw(25) << left << "Nombre de reserva"
                 << setw(20) << left << "Numero de mesa"
                 << setw(15) << left << "Tipo de mesa"
                 << setw(20) << left << "Ubicacion"
                 << setw(12) << left << "Capacidad"
                 << reset << endl;

            while (nodo != nullptr)
            {
                cout << bold << Color(7);
                cout << setw(25) << left << nodo->reserva.nombre
                     << setw(20) << left << nodo->reserva.numero
                     << setw(15) << left << nodo->reserva.tipo
                     << setw(20) << left << nodo->reserva.ubicacion
                     << setw(12) << left << nodo->reserva.capacidad
                     << reset << endl;

                nodo = nodo->siguiente;
            }
            cout << "\n\n";
        }
        else
        {
            cout << bold << Color(1) << "Lista de reservas vacia \n"
                 << reset;
            return;
        }
    }
    else
    {
        cout << bold << Color(1) << "Este usuario no tiene acceso al registro de usuarios \n"
             << reset;
        return;
    }
}

void imprimirGeneral() // Misma función que la anterior pero para mostrar la lista general de reservas
{
    if (!emptyGeneral())
    {
        NodoReservas *nodo = listaGeneral;
        cout << bold << Color(4) << "Mostrando informacion general de reservas\n"
             << reset;
        cout << endl;

        cout << bold << Color(3); // Los datos se muestran en formato de tabla
        cout << setw(25) << left << "Nombre de reserva"
             << setw(20) << left << "Numero de mesa"
             << setw(15) << left << "Tipo de mesa"
             << setw(20) << left << "Ubicacion"
             << setw(12) << left << "Capacidad"
             << reset << endl;

        while (nodo != nullptr)
        {
            cout << bold << Color(7);
            cout << setw(25) << left << nodo->reserva.nombre
                 << setw(20) << left << nodo->reserva.numero
                 << setw(15) << left << nodo->reserva.tipo
                 << setw(20) << left << nodo->reserva.ubicacion
                 << setw(12) << left << nodo->reserva.capacidad
                 << reset << endl;

            nodo = nodo->siguiente;
        }
        cout << "\n\n";
    }
    else
    {
        cout << bold << Color(1) << "Lista de reservas vacia \n"
             << reset;
        return;
    }
}

bool empty(bool isListaUsuarios) // Funcion que verifica si la lista de usuario o de reservas se encuentra vacia
{
    if (isListaUsuarios)
    {
        if (listaUsuarios == nullptr)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    else if (!isListaUsuarios)
    {
        if (listaReservas == nullptr)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}

bool emptyGeneral() // Misma funcion pero para lista general de reservas
{
    if (listaGeneral == nullptr)
    {
        return true;
    }
    else
    {
        return false;
    }
}

void buscar(bool isListaUsuarios, bool isAdmin, string nombre) // Funcion que realiza una busqueda de un nodo de
// usuario o reserva especifico y si lo encuentra muestra su informacion completa
{
    if (isListaUsuarios && isAdmin)
    {
        if (!empty(isListaUsuarios))
        {
            if (busqueda(true, isAdmin, nombre)) // Verificacion de existencia del nodo de usuario solicitado
            {
                NodoUsuarios *temp = listaUsuarios;
                while (temp != nullptr)
                {
                    if (temp->usuario.user == nombre)
                    {
                        break;
                    }
                    temp = temp->siguiente;
                }
                cout << bold << Color(4) << "Informacion de usuario \n"
                     << reset
                     << endl
                     << bold << Color(7) << setw(30) << left << "Usuario: " << right << temp->usuario.user << endl
                     << setw(30) << left << "Contraseña: " << right << temp->usuario.password << endl;
                if (temp->usuario.isAdmin)
                {
                    cout << setw(30) << left << "Tipo de usuario: " << right << "Administrador" << endl;
                }
                else
                {
                    cout << setw(30) << left << "Tipo de usuario: " << right << "Normal" << endl;
                }
                cout << "--------------------------------------\n"
                     << reset;
            }
            else
            {
                cout << bold << Color(1) << "El usuario no ha sido encontrado\n"
                     << reset;
                return;
            }
        }
        else
        {
            cout << bold << Color(1) << "Lista de usuarios vacia\n"
                 << reset;
            return;
        }
    }
    else if (!isListaUsuarios)
    {
        if (!empty(isListaUsuarios))
        {
            if (busqueda(false, isAdmin, nombre)) // verificacion de existencia del nodo de reserva solicitado
            {
                NodoReservas *temp = listaReservas;
                while (temp != nullptr)
                {
                    if (temp->reserva.nombre == nombre)
                    {
                        break;
                    }
                    temp = temp->siguiente;
                }
                cout << bold << Color(4) << "Informacion de reserva\n"
                     << reset
                     << endl
                     << bold << Color(7) << setw(30) << left << "Nombre del cliente: " << right << temp->reserva.nombre << endl
                     << setw(30) << left << "Numero de mesa: " << right << temp->reserva.numero << endl
                     << setw(30) << left << "Tipo de mesa: " << right << temp->reserva.tipo << endl
                     << setw(30) << left << "Ubicacion: " << right << temp->reserva.ubicacion << endl
                     << setw(30) << left << "Capacidad: " << right << temp->reserva.capacidad << endl
                     << "--------------------------------------\n"
                     << reset;
            }
            else
            {
                cout << bold << Color(1) << "La reserva no ha sido encontrada\n"
                     << reset;
                return;
            }
        }
        else
        {
            cout << bold << Color(1) << "Lista de reservas vacia\n"
                 << reset;
            return;
        }
    }
    else
    {
        cout << bold << Color(1) << "Este usuario no tiene acceso al registro de usuarios\n"
             << reset;
        return;
    }
}

// Esta funcion sirve para vaciar la lista de usuarios
void vaciarListaUsuarios(NodoUsuarios *&lista) // Funcion para vaciar completamente la lista de usuarios
{
    while (lista != nullptr)
    {
        NodoUsuarios *nodoAEliminar = lista;
        lista = lista->siguiente;
        delete nodoAEliminar;
    }
}

// Esta funcion sirve para vaciar la lista de reservas
void vaciarListaReservas(NodoReservas *&lista) // Misma funcion pero para reservas
{
    while (lista != nullptr)
    {
        NodoReservas *nodoAEliminar = lista;
        lista = lista->siguiente;
        delete nodoAEliminar;
    }
}

bool evaluarNumero(int numero) // Funcion que evalua si un numero de mesa ingresado para una reserva
// esta dentro del rango y si esta disponible o ocupada
{
    NodoReservas *temp = listaGeneral;

    if (numero > 0 && numero <= 20)
    {
        while (temp != nullptr)
        {
            if (temp->reserva.numero == numero)
            {
                return true;
            }

            temp = temp->siguiente;
        }
    }
    else
    {
        cout << bold << Color(1) << "El numero de mesa ingresado no existe\n"
             << "Mesas numeradas del 1 - 20\n"
             << reset;
        system("pause");
    }

    return false;
}
