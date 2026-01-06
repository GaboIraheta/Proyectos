package org.example.monopoly.models.Casualidades.Builders;

import javafx.scene.control.Tab;
import org.example.monopoly.gui.controller.alerts.AlertBuilder;
import org.example.monopoly.gui.controller.alerts.InfoAlertBuilder;
import org.example.monopoly.gui.controller.TableroController;
import org.example.monopoly.models.Casualidades.Casualidades;
import org.example.monopoly.models.Jugadores.Jugador;
import org.example.monopoly.models.Tablero.Tablero;

import java.io.IOException;

public class BuilderCasualidadMover extends BuilderCasualidad{

    /**
     * Arquitecto concreto para las casualidades que implican mover al jugador a alguna casilla.
     * Su método {@code realizarEfecto} se encarga de cambiar la posición del jugador, el cual será el
     * valor agregado a {@code value}, que se construye a través del método {@code buildValue(int value)}.
     */
    public BuilderCasualidadMover() {
        reset();
    }

    @Override
    public void reset() {
        casualidad = new Casualidades() {

            /**
             * Dependiendo de {@code value}, tal será la nueva posición del jugador. En enviar a la cárcel
             * forma parte de este método, así que si {@code value == 10} (la posición de la casilla Cárcel)
             * entonces también cambiará el atributo {@code preso:boolean} del jugador de {@code true} a {@code false}.
             * @param player
             */
            @Override
            public void realizarEfecto(Jugador player) throws IOException, InterruptedException {

                InfoAlertBuilder builder = new InfoAlertBuilder();
                builder.buildTitle("Casualidad");
                builder.buildHeader(casualidad.getName());
                builder.buildContent(casualidad.getDescription());

                builder.getAlert().showAndWait();
                //tab.cleanLabel(player.getPosicion(), player.getNombre());
                System.out.println(player.getPosicion());
                player.setPosicion(casualidad.getValue());
                System.out.println(player.getPosicion());

                //tab.updateLabel(player.getPosicion(), player.getNombre());

                if (casualidad.getValue() == 10){
                    player.setPreso(true);
                }
                else {
                    Tablero.get_tablero().getCasillas().get(getValue()).realizarAccion(player);
                }
            }
        };
    }

    public void buildValue(int value){
        casualidad.setValue(value);
    }
}
