package Casillas.Propiedades;

import Casillas.CasillaAdquirible;
import Jugadores.Jugador;
import Resources.GeneralMethods;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Servicios extends CasillaAdquirible {

    /**@param nombre
     * metodo constructor de la clase
     * */
    public Servicios(String nombre) {
        super(nombre, "Servicio de energia electrica para la comunidad", 150, null, 75);
    }

    /*
     * Metodo para mostrar la informacion de la casilla al usuario
     * */
    @Override
    public void mostrarInfo() throws IOException{

        System.out.println("------------------------");
        System.out.println("SERVICIO " + nombre);
        System.out.println("\tPROPIETARIO");
        System.out.println("\t\t" + ((propietario != null) ? propietario.getNombre(): "Sin propietario"));
        System.out.println("\tDESCRIPCION");
        System.out.println("\t\t" + descripcion);
        System.out.println("\tPRECIO");
        System.out.println("\t\t$" + precio);
        System.out.println("\tHIPOTECA");
        System.out.println("\t\t$" + hipoteca);
        System.out.println("------------------------");

        GeneralMethods.systemPause(); GeneralMethods.systemCls();
    }

    /**@param player recibe un jugador, el cual es el jugador que cayo en la casilla y para el cual
     * la casilla va a ejecutar su accion
     * El metodo se encarga de verificar primeramente si el servicio tiene propietario
     * Si posee entonces solo muestra el mensaje de que puede continuar y destruye el metodo
     * con la instruccion return
     * Luego verifica si el propietario es nulo si es que la primera verificacion fallo
     * Si es nulo entonces le da la opcion al jugador para comprar el servicio implementando
     * toda la logica para realizar dicha accion
     * Si no es nulo realiza una verificacion dentro del bloque else
     * realiza una verificacion interna a ver si el servicio
     * se encuentra hipotecado, si esto no se cumple entonces le cobra la renta normalmente
     * implementando la logica para que el jugador que esta ahi le pague al jugador propietario
     * En este caso la logica se implementa de tal manera que el jugador le pague al propietario
     * 10 veces lo que tira el dado si posee los dos servicios y si posee solo uno, que le pague
     * 5 veces lo que el dado marque
     * Si la propiedad esta hipotecada se implementa la logica para que el jugador le pague al banco
     * En este caso, pagandole 10 veces el valor de los dados*/
    @Override
    public void realizarAccion(Jugador player) throws IOException{

        Scanner input = new Scanner(System.in);
        int aceptar = 0;

        mostrarInfo();

        if(propietario == player) {

            System.out.println("Bienvenido, " + player.getNombre() + ". Disfrute el paso por su propiedad");

            GeneralMethods.systemPause(); GeneralMethods.systemCls();

            return;
        }

        if(propietario == null) {
            System.out.println("¿Desea comprar el servicio " + nombre + "?\n[1] Si\n[2] No");

            try {
                aceptar = input.nextInt();
                input.nextLine();
            } catch(InputMismatchException e) {
                System.out.println("Ingrese una opcion valida");
            }

            if(aceptar == 1) {

                propietario = player;
                player.pagar(precio);
                player.adquirir_propiedad(this);

                System.out.println(player.getNombre() + " ha comprado el servicio " + nombre);


            } else System.out.println(player.getNombre() + " puede continuar su camino . . .");

        } else {

            int cantidad = 0;
            boolean temp = false;
            int tempDados;

            if(player.isJuegaDeNuevo()) {
                temp = true;
            }

            if(!isHipotecado()) {

                for(CasillaAdquirible propiedad : propietario.getPropiedades()) {
                    if(propiedad instanceof Servicios) {
                        cantidad++;
                    }
                }

                if(cantidad == 2){
                    System.out.println("El propietario posee todos los servicios, se pagara 10 veces el valor que indiquen los dados");
                    tempDados = player.tirarDados();
                    System.out.println(player.getNombre() + " paga $" + (10*tempDados) + " al propietario del servicio " + nombre);
                    player.pagar(10* tempDados);
                    this.getPropietario().cobrar(10*tempDados);
                }
                else {
                    System.out.println("El propietario un solo servicio, se pagara 5 veces el valor que indiquen los dados");
                    tempDados = player.tirarDados();
                    System.out.println(player.getNombre() + " paga $" + (5*tempDados) + " al propietario del servicio " + nombre);
                    player.pagar(5* tempDados);
                    this.getPropietario().cobrar(5*tempDados);
                }

                player.setJuegaDeNuevo(temp);

            } else {

                System.out.println("El servicio " + nombre + " esta hipotecado\n");
                System.out.println("Le pagara 10 veces el valor que indiquen los dados al banco");
                tempDados = player.tirarDados();
                System.out.println("Deberá pagar $" + (10*tempDados) + " al banco");
                player.pagar(10*tempDados);
                GeneralMethods.systemPause(); GeneralMethods.systemCls();

                player.pagar(renta.getFirst());
            }
        }
    }
}
