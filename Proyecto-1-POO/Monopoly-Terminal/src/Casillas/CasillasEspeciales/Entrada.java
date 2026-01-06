package Casillas.CasillasEspeciales;

import Casillas.Casilla;
import Jugadores.Jugador;
import Resources.GeneralMethods;

import java.io.IOException;

public class Entrada extends Casilla {

    //constructor de la clase
    public Entrada() {
        this.nombre = "Entrada";
        this.descripcion = "Si pasas por la entrada, recibes $200 mi compa!";
    }

    /**
     * Este metodo realiza un override del metodo abstracto
     * mostrarInfo de la clase abstracta casilla
     * muestra la informacion de la casilla de entrada
     */
    @Override
    public void mostrarInfo() throws IOException {

        System.out.println("------------------------\n");
        System.out.println("CASILLA " + nombre + "\n");
        System.out.println("Descripcion: " + descripcion + "\n");
        System.out.println("------------------------\n");

        GeneralMethods.systemCls(); GeneralMethods.systemCls();
    }

    /**@param player recibe un jugador, el cual es el jugador que cayo en la casilla y para el cual
     * la casilla va a ejecutar su accion
     * El metodo se encarga de mostrar la informacion de la casilla, ademas muestra un mensaje al jugador
     * que le indica que se encuentra en la entrada, y que ya ha recibido $200 por completar una vuelta
     */
    @Override
    public void realizarAccion(Jugador player) throws IOException {

        mostrarInfo();

        System.out.println("Excelente, " + player.getNombre() + "! Estas en la entrada, seguramente ya recibiste tus $200 por completar una vuelta al tablero ;)");

        player.cobrar(200);
    }
}
