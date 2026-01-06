package org.example.monopoly.models.Casillas.CasillasEspeciales;

import org.example.monopoly.gui.controller.TableroController;
import org.example.monopoly.gui.controller.alerts.InfoAlertBuilder;
import org.example.monopoly.models.Casillas.Casilla;
import org.example.monopoly.models.Jugadores.Jugador;
import org.example.monopoly.models.Tablero.Tablero;

import java.io.IOException;

public class GoJail extends Casilla {

    //Constructor de la clase
    public GoJail() {
        this.nombre = "Go to Jail";
        this.descripcion = "Si caes en GoToJail, lo sentimos, te vas a la carcel por andar delinquiendo";
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

        String content = "Lo sentimos, " + player.getNombre() +
                ". Has cometido un delito, te vas directo a la carcel\n" +
                "Podras salir luego de tres turnos o si sacas un doble en " +
                "tu turno respectivo, o si pagas una fianza";

        TableroController tab = TableroController.getInstance();

        //tab.cleanLabel(player);

        InfoAlertBuilder builder = new InfoAlertBuilder();
        builder.buildTitle(nombre);
        builder.buildHeader(descripcion);
        builder.buildContent(content);
        builder.getAlert().showAndWait();

        player.setPosicion(10);
        TableroController.setTurno(tab.getTurno() + 1);
        player.setPreso(true);

        //tab.updateLabel(player);
    }
}
