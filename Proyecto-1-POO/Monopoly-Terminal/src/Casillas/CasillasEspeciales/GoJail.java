package Casillas.CasillasEspeciales;

import Casillas.Casilla;
import Jugadores.Jugador;
import Jugadores.Preso;
import Resources.GeneralMethods;
import Tablero.Tablero;

import java.io.IOException;

public class GoJail extends Casilla {

    //Constructor de la clase
    public GoJail() {
        this.nombre = "Go to Jail";
        this.descripcion = "Si caes en GoToJail, lo sentimos, te vas a la carcel por andar delinquiendo";
    }

    /**
     * Este metodo realiza un override del metodo abstracto
     * mostrarInfo de la clase abstracta casilla
     * muestra la informacion de la casilla de GoJail
     */
    @Override
    public void mostrarInfo() throws IOException {

        System.out.println("------------------------\n");
        System.out.println("CASILLA " + nombre + "\n");
        System.out.println("Descripcion: " + descripcion + "\n");
        System.out.println("------------------------\n");

        GeneralMethods.systemPause(); GeneralMethods.systemCls();
    }

    /**@param player recibe un jugador, el cual es el jugador que cayo en la casilla y para el cual
     * la casilla va a ejecutar su accion
     * El metodo se encarga de mostrar la informacion de la casilla, ademas muestra un mensaje al jugador
     * que le indica que se encuentra en la casilla para ir a la carcel
     * Luego el metodo se encargabde toda la logica para establecer que el jugador este en la carcel en los
     * proximos turnos
     */
    @Override
    public void realizarAccion(Jugador player) throws IOException {

        mostrarInfo();

        System.out.println("Lo sentimos, " + player.getNombre() + ". Has cometido un delito, te vas directo a la carcel");
        System.out.println("Podras salir luego de tres turnos o si sacas un doble en tu turno respectivo, o si pagas una fianza");

        GeneralMethods.systemPause(); GeneralMethods.systemCls();
        player.setPosicion(10);

        player.setPreso(true);
    }
}
