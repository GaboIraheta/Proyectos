#include "ManejoDeArchivos.hpp"

using namespace std;

bool autenticar(string, string);
void Logo();
int MenuLogin();
void MenuOptions(bool);
void MenuUserManagement(bool);

string username;
string password;

bool autenticar(string _username, string _password) //Funcion que verifica si las credenciales de un 
//usuario son correctas o existen para darle entrada o no al programa
{
    datosArchivoAusuarios("Usuarios", usuario); //Se extraen los datos del archivo de usuarios a la lista
    //de usuarios
    NodoUsuarios *temp = listaUsuarios;

    while (temp != nullptr)
    {
        if (temp->usuario.user == _username && temp->usuario.password == _password)
        {
            return true;
        }

        temp = temp->siguiente;
    }

    return false;
}

//=====================================================================================================================

void Logo() //Funcion que genera el logo del sistema
/*
Se supone imprimirá esto, pero con colores para que se vea mejor
                          _|_|_|  _|_|_|     _|_|_|
                        _|        _|    _|  _|
                          |_|     _|_|_|    _|
                              _|  _|    _|  _|
                        _|_|_|    _|    _|   _|_|_|
*/
{
    system("cls");

    std::cout << "\t\t\t";
    std::cout << "  " << bold << LightBack(1) << "_|_|_|" << reset;
    std::cout << "  " << bold << LightBack(1) << "_|_|_|" << reset;
    std::cout << "     " << bold << LightBack(1) << "_|_|_|" << reset << std::endl;

    std::cout << "\t\t\t";
    std::cout << bold << LightBack(1) << "_|" << reset;
    std::cout << "        " << bold << LightBack(1) << "_|" << reset;
    std::cout << "    " << bold << LightBack(1) << "_|" << reset;
    std::cout << "  " << bold << LightBack(1) << "_|" << reset << std::endl;

    std::cout << "\t\t\t";
    std::cout << "  " << bold << LightBack(1) << "|_|" << reset;
    std::cout << "     " << bold << LightBack(1) << "_|_|_|" << reset;
    std::cout << "    " << bold << LightBack(1) << "_|" << reset << std::endl;

    std::cout << "\t\t\t";
    std::cout << "      " << bold << LightBack(1) << "_|" << reset;
    std::cout << "  " << bold << LightBack(1) << "_|" << reset;
    std::cout << "    " << bold << LightBack(1) << "_|" << reset;
    std::cout << "  " << bold << LightBack(1) << "_|" << reset << std::endl;

    std::cout << "\t\t\t";
    std::cout << bold << LightBack(1) << "_|_|_|" << reset;
    std::cout << "    " << bold << LightBack(1) << "_|" << reset;
    std::cout << "    " << bold << LightBack(1) << "_|" << reset;
    std::cout << "   " << bold << LightBack(1) << "_|_|_|" << reset << std::endl;

    std::cout << std::endl;

    std::cout << std::setw(26) << bold << Color(5) << "Sistema de Reservas Centralizado" << reset;
}

//=====================================================================================================================

int MenuLogin() //Funcion que solicita las credenciales de usuario y da entrada al programa
//si el valor de verdad es true
{
    for (int i = 0; i < 3; i++)
    {
        system("cls");
        Logo();
        std::cout << "\n\n\n";
        std::cout << bold << Color(4) << "Para continuar, ingrese su usuario y clave." << reset << "\n\n";

        std::cout << bold << Color(4) << std::setw(40) << std::left;
        std::cout << "Usuario: " << reset << " " << std::right << italic << Back(4);
        std::cin >> username;
        std::cout << reset;

        std::cout << bold << Color(4) << std::setw(40) << std::left;
        std::cout << "Contrasena: " << reset << " " << std::right << blocked << Back(4);
        std::cin >> password;
        std::cout << reset;

        if (autenticar(username, password))
        {
            datosArchivoAReserva(username, reserva, isAdmin, false); //Se extraen los datos del archivo
            //de reservas a lista de reservas
            datosArchivoAReserva("General", reserva, isAdmin, true); //Y del archivo general de reservas
            //a la lista general de reservas
            system("cls");
            Logo();
            std::cout << "\n\n\n";
            std::cout << bold << Color(2) << "\nBienvenido/a de nuevo, " << username << "!\n"
                      << reset;
            system("pause");
            MenuOptions(isAdmin(username));
            break;
        }
        else
        {
            std::cout << bold << Color(1) << "\nUsuario y/o contrasena incorrectos. Intento " << i + 1 << "/3\n\n"
                      << reset;
            system("pause");
        }
    }

    if(isAdmin(username)) {
        std::cout << bold << Color(0) << "Cesion de " << username << " finalizada" << endl
                  << "Archivo de usuarios y archivo de reservas actualizado\n" << reset;
    } else {
        std::cout << bold << Color(0) << "Cesion de " << username << " finalizada" << endl
                  << "Archivo de reservas actualizado\n" << reset;
    }

    return 0;
}

//=====================================================================================================================

void MenuOptions(bool isAdmin) //Funcion que contiene las opciones de menu de reservas
//verificando si el usuario es un administrador o un usuario normal
{
    int numOption = 0;

    system("cls");
    Logo();
    std::cout << "\n\n\n";

    if (isAdmin)
    {
        std::cout << bold << Color(2);
        std::cout << std::setw(80) << std::left << "Menu de administrador" << reset << std::endl;
        std::cout << std::endl;
    }
    else
    {
        std::cout << bold << Color(2);
        std::cout << std::setw(80) << std::left << "Opciones de reserva de mesas" << reset << std::endl;
        std::cout << std::endl;
    }
    std::cout << italic << Color(6);
    std::cout << std::setw(40) << std::left << "Buscar reserva";
    std::cout << std::setw(40) << std::right << "[" << ++numOption << "]";
    std::cout << reset << std::endl;

    std::cout << italic << Color(6);
    std::cout << std::setw(40) << std::left << "Crear reserva";
    std::cout << std::setw(40) << std::right << "[" << ++numOption << "]";
    std::cout << reset << std::endl;

    std::cout << italic << Color(6);
    std::cout << std::setw(40) << std::left << "Editar reserva";
    std::cout << std::setw(40) << std::right << "[" << ++numOption << "]";
    std::cout << reset << std::endl;

    std::cout << italic << Color(6);
    std::cout << std::setw(40) << std::left << "Eliminar reserva";
    std::cout << std::setw(40) << std::right << "[" << ++numOption << "]";
    std::cout << reset << std::endl;

    std::cout << italic << Color(6); // Se agrego nueva opcion de menu para poder ver todas las reservas
    std::cout << std::setw(40) << std::left << "Mostrar reservas";
    std::cout << std::setw(40) << std::right << "[" << ++numOption << "]";
    std::cout << reset << std::endl;

    if (isAdmin)
    {
        std::cout << italic << Color(6);
        std::cout << std::setw(40) << std::left << "Todas las reservas";
        std::cout << std::setw(40) << std::right << "[" << ++numOption << "]"; // 5 si es admin
        std::cout << reset << std::endl;

        std::cout << italic << Color(6);
        std::cout << std::setw(40) << std::left << "Administrar usuarios";
        std::cout << std::setw(40) << std::right << "[" << ++numOption << "]"; // 5 si es admin
        std::cout << reset << std::endl;
    }

    std::cout << italic << Color(3);
    std::cout << std::setw(40) << std::left << "Cerrar sesion";
    std::cout << std::setw(40) << std::right << "[" << ++numOption << "]"; // 6 si es admin, 5 si es usuario normal
    std::cout << reset << std::endl;

    int opcion = 0;
    bool isUser = true; //Para manejar los metodos de las listas de usuarios y reservas adecuadamente
    std::string nombreReserva = "";
    bool cambio = true;

    std::cout << std::endl;
    std::cout << bold << Color(4) << "Opcion: " << reset;
    std::cin >> opcion;

    /*
    Switch case que maneja el comportamiento del menú.
    De momento, solo la opción 5 y 6 hacen algo. El resto va ha ser completado eventualmente, conforme
    avance el proyecto. Además, quería probar recursión dentro del menú para no tener que usar un bucle while
    */

    switch (opcion)
    {
    case 1: //Opcion para buscar informacion de una reservas en especifico
        system("cls");
        // then again, it does not exist yet
        std::cout << bold << Color(4) << "Buscando detalles de reserva" << reset << std::endl;
        std::cout << endl;
        std::cout << bold << Color(0) << "Ingrese el nombre del cliente de la reservacion: ";
        std::cin >> nombreReserva;
        std::cout << "--------------------------------------\n" << reset;
        cout << endl;
        buscar(!isUser, isAdmin, nombreReserva);
        cout << endl;

        system("pause");
        system("cls");

        nombreReserva = "";

        MenuOptions(isAdmin);
        break;

    case 2: //Opcion para crear una nueva reserva
        system("cls");
        cout << bold << Color(4) << "Creando nueva reserva\n" << reset;
        cout << endl;
        // same as above
        cout << bold << Color(7) << "Ingrese el nombre de la reservacion: ";
        cin >> reserva.nombre;
        cout << "--------------------------------------\n" << reset;

        cout << bold << Color(7) << "Ingrese el numero de mesa: ";
        cin >> reserva.numero;
        cout << "--------------------------------------\n" << reset;

        cout << bold << Color(7) << "Ingrese el tipo de mesa: ";
        cin >> reserva.tipo;
        cout << "--------------------------------------\n" << reset;

        cout << bold << Color(7) << "Ingrese la ubicacion de la mesa: ";
        cin >> reserva.ubicacion;
        cout << "--------------------------------------\n" << reset;

        cout << bold << Color(7) << "Ingrese la capacidad de la mesa: ";
        cin >> reserva.capacidad;
        cout << "--------------------------------------\n" << reset;
        cout << endl;

        if (evaluarNumero(reserva.numero)) //Evalua si la mesa ingresada esta ocupada o disponible
        {
            cout << bold << Color(1) << "La mesa " << reserva.numero << " ya ha sido reservada\n" << reset
                 << bold << Color(1) << "Accion de reservacion incompleta\n" << reset;
        }
        else //Si esta disponible inserta en las listas de reservas adecuadamente
        {
            insertarGeneral();
            insertar(!isUser, isAdmin);
            cout << bold << Color(6) << "Reserva agregada exitosamente" << reset << endl;
        }
        
        system("pause");
        system("cls");

        MenuOptions(isAdmin);
        break;

    case 3: //Opccion para editar los datos de una reserva en especifico
        system("cls");
        cout << bold << Color(4) << "Editando informacion de reserva\n" << reset;
        cout << endl;
        cout << bold << Color(0) << "Ingrese el nombre de la reservacion que desea editar: ";
        cin >> nombreReserva; // Se intuye qué es. Hint: no
        cout << "--------------------------------------\n" << reset;
        cout << endl;
        editar(!isUser, isAdmin, nombreReserva, cambio);
        cout << endl;

        system("pause");
        system("cls");

        nombreReserva = "";

        MenuOptions(isAdmin);
        break;

    case 4: //Opcion para eliminar una reserva en especifico
        cout << bold << Color(4) << "Eliminando reserva" << reset << endl;
        cout << endl;
        system("cls");
        cout << bold << Color(0) << "Ingrese el nombre de reserva que desea eliminar: ";
        cin >> nombreReserva;
        cout << "--------------------------------------\n" << reset;
        cout << endl;

        eliminar(!isUser, isAdmin, nombreReserva); // ese entero será nombreReserva en el futuro

        system("pause");
        system("cls");

        nombreReserva = "";

        MenuOptions(isAdmin);
        break;

    case 5: //Opcion para mostrar todas las reservaciones realizadas
        system("cls");
        imprimir(!isUser, isAdmin);

        system("pause");
        system("cls");

        MenuOptions(isAdmin);
        break;

    default:
        if (isAdmin)
        {
            switch (opcion)
            {
            case 6: // Opción para el administrador de ver TODAS las reservas
                system("cls");
                imprimirGeneral();

                system("pause");
                system("cls");

                MenuOptions(isAdmin);
                break;

            case 7: // Opción para ver usuarios
                MenuUserManagement(isAdmin);
                break;

            default: // Cerrar el programa (cualquier otro entero)
                archivoUsuarios("Usuarios", listaUsuarios);
                archivoReservas(username, listaReservas);
                archivoReservas("General", listaGeneral);
                return;
                break;
            }
        }
        else
        {
            switch (opcion)
            {
            default: // Cerrar el programa (cualquier otro entero)
                archivoReservas(username, listaReservas);
                archivoReservas("General", listaGeneral);
                return;
                break;
            }
        }
        break;
    }
    return;
}

//=====================================================================================================================

void MenuUserManagement(bool isAdmin) //Funcion que contiene el menu de usuarios para administradores
{
    system("cls");
    Logo();
    std::cout << "\n\n\n";

    std::cout << bold << Color(6);
    std::cout << std::setw(80) << std::left << "Adminstracion de usuarios" << reset << std::endl;
    std::cout << std::endl;

    std::cout << italic << Color(2);
    std::cout << std::setw(40) << std::left << "Buscar informacion usuario";
    std::cout << std::setw(40) << std::right << "[1]";
    std::cout << reset << std::endl;

    std::cout << italic << Color(2);
    std::cout << std::setw(40) << std::left << "Nuevo usuario";
    std::cout << std::setw(40) << std::right << "[2]";
    std::cout << reset << std::endl;

    std::cout << italic << Color(2);
    std::cout << std::setw(40) << std::left << "Cambiar credenciales usuario";
    std::cout << std::setw(40) << std::right << "[3]";
    std::cout << reset << std::endl;

    std::cout << italic << Color(2);
    std::cout << std::setw(40) << std::left << "Borrar usuario";
    std::cout << std::setw(40) << std::right << "[4]";
    std::cout << reset << std::endl;

    std::cout << italic << Color(2); // Se agrego nueva opcion de menu para mostrar los usuarios
    std::cout << std::setw(40) << std::left << "Mostrar usuarios";
    std::cout << std::setw(40) << std::right << "[5]";
    std::cout << reset << std::endl;

    std::cout << italic << Color(4);
    std::cout << std::setw(40) << std::left << "Regresar al menu principal";
    std::cout << std::setw(40) << std::right << "[6]";
    std::cout << reset << std::endl;

    int opcion = 0;
    string name;
    string aux;
    bool isUser = true;
    bool cambio = true;

    std::cout << std::endl;
    std::cout << bold << Color(4) << "Opcion: " << reset;
    std::cin >> opcion;

    /*
    Switch case que maneja el comportamiento del menú.
    De momento, solo la opción 5 y 6 hacen algo. El resto va ha ser completado eventualmente, conforme
    avance el proyecto. Además, quería probar recursión dentro del menú para no tener que usar un bucle while
    */

    switch (opcion)
    {
    case 1: //Opcion para buscar un usuario en especifico y ver su informacion
        system("cls");
        std::cout << bold << Color(4) << "Buscando informacion de usuario" << reset << std::endl;
        cout << endl;
        cout << bold << Color(0) << "Ingrese el usuario que desea buscar: ";
        cin >> name;
        cout << "--------------------------------------\n" << reset;
        cout << endl;

        buscar(isUser, isAdmin, name);
        cout << endl;

        name = "";

        system("pause");
        system("cls");

        MenuUserManagement(isAdmin);
        break;

    case 2: //Opcion para crear un nuevo usuario
        system("cls");
        std::cout << bold << Color(4) << "Creando nuevo usuario" << reset << std::endl;
        cout << endl;
        cout << bold << Color(7) << "Ingrese un nombre de usuario: ";
        cin >> usuario.user;
        cout << "--------------------------------------\n";
        cout << "Ingrese una contrasena: ";
        cin >> usuario.password;
        cout << "--------------------------------------\n";
        cout << "Tipo de usuario: Administrador [1] | Normal [0] ";
        cin >> usuario.isAdmin;
        cout << "--------------------------------------\n" << reset;
        cout << endl;

        insertar(isUser, isAdmin);
        crearArchivo(usuario.user, listaReservas); //Crea el archivo de reservas del usuario nuevo

        cout << bold << Color(6) << "Nuevo usuario creado exitosamente\n"
             << reset;
        system("pause");
        system("cls");

        MenuUserManagement(isAdmin);
        break;

    case 3: //Opcion para editar las credenciales de un usuario en especifico
        system("cls");
        cout << bold << Color(4) << "Editando credenciales de usuario\n" << reset;
        cout << endl;
        cout << bold << Color(0) << "Ingrese el nombre de usuario: ";
        cin >> name;
        cout << "--------------------------------------\n" << reset;
        cout << endl;

        aux = name;
        editar(isUser, isAdmin, name, cambio);

        if (name == aux && cambio) //Evalua si el nombre de usuario fue cambiado para crear un nuevo archivo de reservas
        //con el nuevo nombre de usuario o dejar el mismo
        {
            cout << bold << Color(6) << "Credenciales de usuario editados exitosamente\n" << reset;
        }
        else if(cambio)
        {
            // Se guardan los archivos del usuario en uso del programa, por eso el username
            archivoReservas(username, listaReservas);
            // Se vacia la lista de reservas para poder traer los datos del archivo viejo
            vaciarListaReservas(listaReservas);
            // Se traen los datos del archivo viejo del usuario que se ha editado
            datosArchivoAReserva(aux, reserva, isAdmin, false);
            // Se crea el nuevo archivo del usuario editado con lo que ya se trajo a la lista desde el archivo viejo
            archivoReservas(name, listaReservas);
            // Se vuelve a vaciar la lista de reservas para poder dejar libre para recuperar los datos del usuario en uso
            vaciarListaReservas(listaReservas);
            // Se guarda de nuevo la lista en el archivo viejo del usuario editado para que este archivo este vacio
            archivoReservas(aux, listaReservas);
            // Se borra el archivo viejo del usuario editado
            eliminarArchivoReservas(aux);
            // Se vuelven a traer los datos del archivo de reservas del usuario en uso del programa
            datosArchivoAReserva(username, reserva, isAdmin, false);
            // Se continua manipulando las listas normalmente
            cout << bold << Color(6) << "Credenciales de usuario editados exitosamente\n"
                 << "Archivo de reservas de usuario actualizado\n" << reset;
        } else {
            cout << bold << Color(1) << "Cambios de credenciales no posibles\n" << reset << endl;
        }

        system("pause");
        system("cls");

        name = "";
        aux = "";

        MenuUserManagement(isAdmin);
        break;

    case 4: //Opcion para eliminar un usuario
        system("cls");
        std::cout << bold << Color(4) << "Eliminando usuario" << reset << std::endl;
        cout << endl;
        cout << bold << Color(0) << "Ingrese el usuario que desea eliminar: ";
        cin >> name;
        cout << "--------------------------------------\n" << reset;
        cout << endl;

        eliminarEnGeneral(name, username); //Elimina las reservas realizadas por el usuario eliminado
        //del archivo general de reservas
        eliminar(isUser, isAdmin, name);
        eliminarArchivoReservas(name); //Elimina el archivo de reservas del usuario eliminado

        system("pause");
        system("cls");

        name = "";

        MenuUserManagement(isAdmin);
        break;

    case 5: //Opcion para ver los usuarios existentes
        system("cls");
        imprimir(isUser, isAdmin);

        system("pause");
        system("cls");

        MenuUserManagement(isAdmin);
        break;

    default:               // Regresar al menú principal
        MenuOptions(true); // Si está en este menú, debe ser admin en primer lugar, asi que queda como true :/
        break;
    }
}
