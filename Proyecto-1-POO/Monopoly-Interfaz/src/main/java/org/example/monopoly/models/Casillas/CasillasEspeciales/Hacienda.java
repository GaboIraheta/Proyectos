package org.example.monopoly.models.Casillas.CasillasEspeciales;

import javafx.scene.control.Tab;
import org.example.monopoly.gui.controller.TableroController;
import org.example.monopoly.gui.controller.alerts.AlertBuilder;
import org.example.monopoly.gui.controller.alerts.InfoAlertBuilder;
import org.example.monopoly.models.Casillas.Casilla;
import org.example.monopoly.models.Jugadores.Jugador;
import org.example.monopoly.models.Tablero.Tablero;

import java.io.IOException;

public class Hacienda extends Casilla {

    //Constructor de la clase
    public Hacienda() {
        super.nombre = "Hacienda";
        super.descripcion = "Si caes en hacienda, es hora de pagar tus impuestos";
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

        TableroController tab =  TableroController.getInstance();

        String content = player.getNombre() + " ha contribuido amablemente a la comunidad pagando impuestos\n" +
                "Contribuye con un valor de impuestos total de $100, gracias por su contribución " +
                player.getNombre();

        InfoAlertBuilder builder = new InfoAlertBuilder();
        builder.buildTitle(nombre);
        builder.buildHeader(descripcion);
        builder.buildContent(content);

        builder.getAlert().showAndWait();

        player.pagar(100);

        ParadaLibre paradaLibre = (ParadaLibre) Tablero.get_tablero().getCasillas().get(20);

        paradaLibre.setAcumulado(paradaLibre.getAcumulado() + 100);
        tab.getTaxes().setText(String.valueOf(paradaLibre.getAcumulado()));
    }
}
