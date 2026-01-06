package Casillas.CasillasEspeciales;

import Casillas.Casilla;
import Jugadores.Jugador;
import Resources.GeneralMethods;

import javax.imageio.IIOException;
import java.io.IOException;

public class ParadaLibre extends Casilla {
    /**
    * El atributo acumulado es el que lleva el control de la cantidad de impuestos totales
    * que han sido pagados por los jugadores, el cual se actualiza en la clase hacienda
    * */
    private int acumulado;

    /**@code
     * Setter y getter para que el acumulado se actualice en la clase hacienda y para ser mostrado
     * */
    public int getAcumulado() {
        return acumulado;
    }

    public void setAcumulado(int acumulado) {
        this.acumulado = acumulado;
    }

    //Constructor de la clase
    public ParadaLibre() {
        this.nombre = "Parada libre";
        this.descripcion = "Si caes Parada libre, Felicididades! Te llevas todos los impuestos (te los robas)";
        this.acumulado = 0;
    }

    /**
     * Este metodo realiza un override del metodo abstracto
     * mostrarInfo de la clase abstracta casilla
     * muestra la informacion de la casilla de ParadaLibre
     */
    @Override
    public void mostrarInfo() throws IOException {

        System.out.println("------------------------\n");
        System.out.println("CASILLA" + nombre);
        System.out.println("Descripcion: " + descripcion);
        System.out.println("------------------------\n");

        GeneralMethods.systemPause(); GeneralMethods.systemCls();
    }

    /**@param player recibe un jugador, el cual es el jugador que cayo en la casilla y para el cual
     * la casilla va a ejecutar su accion
     * El metodo se encarga de mostrar la informacion de la casilla, ademas muestra un mensaje al jugador
     * que le indica que se encuentra en la casilla de parada libre y que se le van a pagar todos los
     * impuestos acumulados en el pool, ademas se implementa la logica para que se le pague al jugador
     * si es que existe un pool distinto de cero, asi como de actualizar el pool de nuevo a cero si se le paga
     */
    @Override
    public void realizarAccion(Jugador player) throws IOException {

        mostrarInfo();

        if(acumulado != 0) {

            System.out.println("Felciidades, usted es un corrupto!!! Se lleva todo el dinero acumulado por impuestos");
            System.out.println("Por un total de $" + acumulado + ". LADRON!!!");


            player.cobrar(acumulado);

            acumulado = 0;

        } else System.out.println("Mala suerte! No hay nada para robar, siga su camino sapo"); GeneralMethods.systemPause(); GeneralMethods.systemCls();
    }
}
