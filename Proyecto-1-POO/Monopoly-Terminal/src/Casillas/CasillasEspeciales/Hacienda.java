package Casillas.CasillasEspeciales;

import Casillas.Casilla;
import Jugadores.Jugador;
import Resources.GeneralMethods;
import Tablero.Tablero;

import java.io.IOException;

public class Hacienda extends Casilla {

    //Constructor de la clase
    public Hacienda() {
        super.nombre = "Hacienda";
        super.descripcion = "Si caes en hacienda, es hora de pagar tus impuestos";
    }

    /**
     * Este metodo realiza un override del metodo abstracto
     * mostrarInfo de la clase abstracta casilla
     * muestra la informacion de la casilla de hacienda
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
     * que le indica que se encuentra en la casilla de hacienda y que debera pagar un impuesto de $100
     * Se implementa la logica para que el jugador pague dicha cantidad y que el acumulado de impuestos
     * se actualice correctamente
     */
    @Override
    public void realizarAccion(Jugador player) throws IOException {

        mostrarInfo();

        System.out.println(player.getNombre() + " ha contribuido amablemente a la comunidad pagando impuestos");
        System.out.println("Contribuye con un valor de impuestos total de $100, gracias por su contribucion " + player.getNombre());

        GeneralMethods.systemPause(); GeneralMethods.systemCls();

        player.pagar(100);

        ParadaLibre paradaLibre = (ParadaLibre) Tablero.get_tablero().getCasillas().get(20);

        paradaLibre.setAcumulado(paradaLibre.getAcumulado() + 100);

        System.out.println(player.getNombre() + " paga la cantida de $100 por impuestos a hacienda");

        GeneralMethods.systemPause(); GeneralMethods.systemCls();
    }
}
