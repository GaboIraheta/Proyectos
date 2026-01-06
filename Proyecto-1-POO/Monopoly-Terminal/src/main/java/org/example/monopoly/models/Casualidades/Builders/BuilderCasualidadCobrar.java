package org.example.monopoly.models.Casualidades.Builders;

import org.example.monopoly.gui.controller.alerts.AlertBuilder;
import org.example.monopoly.gui.controller.alerts.InfoAlertBuilder;
import org.example.monopoly.models.Casualidades.Casualidades;
import org.example.monopoly.models.Jugadores.Jugador;

public class BuilderCasualidadCobrar extends BuilderCasualidad{

    /**
     * Arquitecto concreto para la casualidad cobrar. Su método {@code realizarEfecto} se encarga de
     * agregar dinero al balance del jugador en el que se realice el efecto. El dinero se agrega por medio
     * del método {@code buildValue(int value)}, siendo {@value} el valor a sumar al balance.
     */
    public BuilderCasualidadCobrar() {
        reset();
    }

    @Override
    public void reset() {
        casualidad = new Casualidades() {
            /**
             * Este método toma a un jugador, y agregará a su balance la cantidad construida por el
             * Arquitecto.
             * @param player
             */
            @Override
            public void realizarEfecto(Jugador player) {
                InfoAlertBuilder builder = new InfoAlertBuilder();
                builder.buildTitle("Casualidad");
                builder.buildHeader(casualidad.getName());
                builder.buildContent(casualidad.getDescription());

                builder.getAlert().showAndWait();

                player.cobrar(casualidad.getValue());
            }

        };
    }

    public void buildValue(int value) {
        casualidad.setValue(value);
    }
}
