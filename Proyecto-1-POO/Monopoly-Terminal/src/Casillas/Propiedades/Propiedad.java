package Casillas.Propiedades;
import java.io.IOException;
import java.util.ArrayList;

import Casillas.CasillaAdquirible;
import Jugadores.Jugador;
import Resources.GeneralMethods;


import java.util.InputMismatchException;
import java.util.Scanner;

public class Propiedad extends CasillaAdquirible {

    /*
    * Atributos que posee la clase propiedad
    * */
    private final ColorCasilla colorCasilla;
    private final int precioCasa;
    private int numeroCasas;

    /**@param nombre
     * @param descripcion
     * @param precio
     * @param renta
     * @param hipoteca
     * @param colorCasilla
     * @param precioCasa
     * metodo constructor de la clase
     * */
    public Propiedad(String nombre, String descripcion, int precio, ArrayList<Integer> renta,
                     int hipoteca, ColorCasilla colorCasilla, int precioCasa) {
        super(nombre, descripcion, precio, renta, hipoteca);
        this.colorCasilla = colorCasilla;
        this.precioCasa = precioCasa;
        this.numeroCasas = 0;
    }

    /*Metodos setter y getters de los atributos de la clase
    * propiedad, para poder acceder a ellos y actualizarlos
    * en caso de ser necesario
    * */
    public int getNumeroCasas() {
        return numeroCasas;
    }

    public void setNumeroCasas(int numeroCasas) {
        this.numeroCasas = numeroCasas;
    }

    public int getPrecioCasa() {
        return precioCasa;
    }

    public ColorCasilla getColorCasilla() {
        return colorCasilla;
    }

    /*
     * Metodo para mostrar la informacion de la casilla al usuario
     * */
    @Override
    public void mostrarInfo() throws IOException {

        System.out.println("--------------------------");
        System.out.println("PROPIEDAD: " + nombre);
        System.out.println("\tPROPIETARIO");
        System.out.println("\t\t" + ((propietario != null) ? propietario.getNombre() : "Sin propietario"));
        System.out.println("\tDESCRIPCION");
        System.out.println("\t\t" + descripcion);
        System.out.println("\tGRUPO DE COLOR");
        System.out.println("\t\t" + colorCasilla);
        System.out.println("\tPRECIO");
        System.out.println("\t\t$" + precio);
        System.out.println("\tRENTA");
        System.out.println("\t\t$" + renta.get(numeroCasas));
        System.out.println("\tPRECIO DE CASAS");
        System.out.println("\t\t$" + precioCasa);
        System.out.println("\tHIPOTECA");
        System.out.println("\t\t$" + hipoteca);
        System.out.println("--------------------------");

        GeneralMethods.systemPause(); GeneralMethods.systemCls();
    }

    /**@param player recibe un jugador, el cual es el jugador que cayo en la casilla y para el cual
     * la casilla va a ejecutar su accion
     * El metodo se encarga de verificar primeramente si la propiedad tiene propietario
     * Si posee entonces solo muestra el mensaje de que puede continuar y destruye el metodo
     * con la instruccion return
     * Luego verifica si el propietario es nulo si es que la primera verificacion fallo
     * Si es nulo entonces le da la opcion al jugador para comprar la propiedad implementando
     * toda la logica para realizar dicha accion
     * Si no es nulo realiza una verificacion dentro del bloque else
     * realiza una verificacion interna a ver si la propiedad
     * se encuentra hipotecada, si esto no se cumple entonces le cobra la renta normalmente
     * que en este caso, sera segun el numero de casas que posea el valor de la renta, y a menos que
     * el numero de casas sea cero y el grupo de color este completo, entonces le cobrara solamente
     * el doble del valor de la renta, implementando la logica para que el jugador que esta ahi le pague
     * al jugador propietario, Si la propiedad esta hipotecada se implementa la logica
     * para que el jugador le pague al banco
     * */
    @Override
    public void realizarAccion(Jugador player) throws IOException {

        Scanner input = new Scanner(System.in);
        int aceptar;

        mostrarInfo();

        if(propietario == player) {

            System.out.println("Bienvenido, " + player.getNombre() + ". Disfrute el paso por su propiedad");

            GeneralMethods.systemPause(); GeneralMethods.systemCls();

            return;
        }

        if(propietario == null) {

            System.out.println("¿Desea comprar la propiedad " + nombre + "?\n[1] Si\n[2] No");

            try {
                aceptar = input.nextInt();
                input.nextLine();

            }catch (InputMismatchException e){

                System.out.println("No es una opción válida");
                aceptar = 0;
            }

            if(aceptar == 1) {
                if(player.getBalance() < precio){
                    System.out.println("You poor LMAO!");
                    return;
                }
                propietario = player;
                player.pagar(precio);
                player.adquirir_propiedad(this);

                System.out.println(player.getNombre() + " ha comprado la propiedad " + nombre);

                GeneralMethods.systemCls();

            } else System.out.println(player.getNombre() + " puede continuar su camino"); GeneralMethods.systemPause(); GeneralMethods.systemCls();

        } else {

            if (!isHipotecado) {

                System.out.println(player.getNombre() + " debe pagar renta al propietario de la propiedad " + nombre);

                GeneralMethods.systemPause(); GeneralMethods.systemCls();

                if (grupoCompleto(propietario) && numeroCasas == 0) {

                    System.out.println("Valor de la renta: $" + renta.getFirst() * 2);

                    GeneralMethods.systemPause(); GeneralMethods.systemCls();

                    player.pagar(renta.getFirst()*2);
                    propietario.cobrar(renta.getFirst()*2);

                } else {

                    System.out.println("Valor de la renta: $" + renta.get(numeroCasas));

                    GeneralMethods.systemPause(); GeneralMethods.systemCls();

                    player.pagar(renta.get(numeroCasas));
                    propietario.cobrar(renta.get(numeroCasas));
                }

            }else {

                System.out.println("La propiedad " + nombre + " está hipotecada\n");
                System.out.println("Le pagará $" + renta.getFirst() + " al banco\n");

                GeneralMethods.systemPause(); GeneralMethods.systemCls();

                player.pagar(renta.getFirst());
            }
        }
    }

    /**@param player este metodo recibe un jugador, el cual es el jugador del que se desea
     * verificar si posee el grupo de color de propiedades completo, ya sea para que pueda
     * comprar casas para alguna propiedad o para cobrar el doble de la renta sin tener
     * ninguna casa o cobrar el valor de la renta segun el numero de casas verificando
     * que puede cobrar eso, lo cual es solo si el grupo de color esta completo
     * El metodo recorre el array de propiedades del jugador y compara los colores de las
     * casillas con el color de this.propiedad, aumentando un contador en uno por cada coincidencia
     * El valor final del counter tendra el numero de propiedades con el color this.colorCasilla
     * finalmente solo verifica si es azul o cafe que solo son dos propiedades para retornar true
     * y si no solo evalua que el counter sea igual a tres
     * @return true si se cumple
     * @return false si no se cumple
     * */
    public boolean grupoCompleto(Jugador player) {

        int counter = 0;

        for(CasillaAdquirible propiedad : player.getPropiedades()) {

            if (propiedad instanceof Propiedad) {

                if (((Propiedad) propiedad).colorCasilla == this.colorCasilla) {

                    counter++;
                }

            }
        }

        if(((this.colorCasilla == ColorCasilla.AZUL) || (this.colorCasilla == ColorCasilla.CAFE)) && counter == 2) {

            return true;

        } else return counter == 3;
    }
}

