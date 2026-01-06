package org.example.monopoly.models.Casillas.CasillasEspeciales;

import org.example.monopoly.gui.controller.alerts.InfoAlertBuilder;
import org.example.monopoly.models.Casillas.Casilla;
import org.example.monopoly.models.Jugadores.Jugador;

import java.io.IOException;

public class Entrada extends Casilla {

    //constructor de la clase
    public Entrada() {
        this.nombre = "Entrada";
        this.descripcion = "Si pasas por la entrada, recibes $200 mi compa!";
    }

    /**@param player recibe un jugador, el cual es el jugador que cayo en la casilla y para el cual
     * la casilla va a ejecutar su accion
     * El metodo se encarga de mostrar la informacion de la casilla, ademas muestra un mensaje al jugador
     * que le indica que se encuentra en la entrada, y que ya ha recibido $200 por completar una vuelta
     */

    @Override
    public void realizarAccion(Jugador player) throws IOException {

        String content = "Excelente, " + player.getNombre() +
                "! Estas en la entrada, seguramente ya recibiste " +
                "tus $200 por completar una vuelta al tablero";

        InfoAlertBuilder builder = new InfoAlertBuilder();
        builder.buildTitle(nombre);
        builder.buildHeader(descripcion);
        builder.buildContent(content);

        builder.getAlert().showAndWait();

        player.cobrar(200);
    }
}
