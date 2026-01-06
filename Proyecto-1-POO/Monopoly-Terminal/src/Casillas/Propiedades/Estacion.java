package Casillas.Propiedades;

import Casillas.CasillaAdquirible;
import Jugadores.Jugador;
import Resources.GeneralMethods;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Estacion extends CasillaAdquirible {

    /**@param nombre
     * @param renta
     * Metodo constructor de la clase
     * */
    public Estacion(String nombre, ArrayList<Integer> renta) {
        super(nombre, "Estacion de servicio accesible a la comunidad", 200, renta, 100);
    }

    /*
    * Metodo para mostrar la informacion de la casilla al usuario
    * */
    @Override
    public void mostrarInfo() throws IOException{

        System.out.println("------------------------");
        System.out.println("ESTACION " + nombre );
        System.out.println("\tPROPIETARIO");
        System.out.println("\t\t" + ((propietario != null) ? propietario.getNombre() + "\n" : "Sin propietario"));
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
     * El metodo se encarga de verificar primeramente si la estacion tiene propietario
     * Si posee entonces solo muestra el mensaje de que puede continuar y destruye el metodo
     * con la instruccion return
     * Luego verifica si el propietario es nulo si es que la primera verificacion fallo
     * Si es nulo entonces le da la opcion al jugador para comprar la estacion implementando
     * toda la logica para realizar dicha accion
     * Si no es nulo realiza una verificacion dentro del bloque else
     * realizar una verificacion interna a ver si la estacion
     * se encuentra hipotecada, si esto no se cumple entonces le cobra la renta normalmente
     * implementando la logica para que el jugador que esta ahi le pague al jugador propietario
     * Si la estacion esta hipotecada se implementa la logica para que el jugador le pague al banco*/
    @Override
    public void realizarAccion(Jugador player) throws IOException{

        Scanner input = new Scanner(System.in);
        int aceptar;

        mostrarInfo();

        if(propietario == player) {

            System.out.println("Bienvenido, " + player.getNombre() + ". Disfrute el paso por su propiedad");

            GeneralMethods.systemPause(); GeneralMethods.systemCls();

            return;
        }

        if(propietario == null) {

            System.out.println("¿Desea comprar la estacion " + nombre + "?\n[1] Si\n[2] No");

            aceptar = input.nextInt();
            input.nextLine();

            GeneralMethods.systemCls();

            if(aceptar == 1) {

                propietario = player;
                player.pagar(precio);
                player.adquirir_propiedad(this);

                System.out.println(player.getNombre() + " ha comprado la estacion " + nombre);


            } else System.out.println(player.getNombre() + " puede continuar su camino . . ."); GeneralMethods.systemPause(); GeneralMethods.systemCls();

        } else {

            if(!isHipotecado()) {

                System.out.println(player.getNombre() + " debe pagar renta al propietario de la estacion " + nombre);

                GeneralMethods.systemPause(); GeneralMethods.systemCls();

                int Cantidad = 0;

                for(CasillaAdquirible propiedad : propietario.getPropiedades()) {

                    if(propiedad instanceof Estacion) {

                        Cantidad++;
                    }
                }
                System.out.println("Valor de la renta: $" + renta.getFirst()*Cantidad);

                player.pagar(renta.getFirst()*Cantidad);
                propietario.cobrar(renta.getFirst()*Cantidad);

            } else {

                System.out.println("La estacion " + nombre + " esta hipotecada\n");
                System.out.println("Le pagara $" + renta.getFirst() + " al banco\n");

                GeneralMethods.systemPause(); GeneralMethods.systemCls();

                player.pagar(renta.getFirst());
            }
        }
    }
}
