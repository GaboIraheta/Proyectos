package org.example.monopoly.models.Casualidades.Builders;

import org.example.monopoly.gui.controller.alerts.AlertBuilder;
import org.example.monopoly.gui.controller.alerts.InfoAlertBuilder;
import org.example.monopoly.models.Casualidades.Casualidades;
import org.example.monopoly.models.Jugadores.Jugador;

public class BuilderCasualidadPagar extends BuilderCasualidad{

    /**
     * Arquitecto concreto para casualidades que involucren pagar. Esto implica restar al balance del jugador
     * una cantidad, definida por {@code value} al construirla por medio del método {@code buildValue(int value)}.
     * Su método {@code realizarEfecto} resta {@code value} a {@code Jugador.balance}.
     */
    public BuilderCasualidadPagar() {
        reset();
    }

    @Override
    public void reset() {
        casualidad = new Casualidades() {
            public void realizarEfecto(Jugador player) {
                InfoAlertBuilder builder = new InfoAlertBuilder();
                builder.buildTitle("Casualidad");
                builder.buildHeader(casualidad.getName());
                builder.buildContent(casualidad.getDescription());

                builder.getAlert().showAndWait();

                player.pagar(casualidad.getValue());
            }
        };
    }

    public void buildValue(int value){
        casualidad.setValue(value);
    }
}
