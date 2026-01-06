package org.example.monopoly.models.Casualidades.Builders;

import org.example.monopoly.gui.controller.alerts.AlertBuilder;
import org.example.monopoly.gui.controller.alerts.InfoAlertBuilder;
import org.example.monopoly.models.Casualidades.Casualidades;
import org.example.monopoly.models.Jugadores.Jugador;

public class BuilderCasualidadLibre extends BuilderCasualidad{

    /**
     * Este Arquitecto construye la casualidad que le da al jugador un "Salga de la Cárcel". No utiliza
     * {@code value}, pues no es requerido para la acción descrita. Dicha acción se realiza a través del
     * método {@code realizarEfecto}.
     */
    public BuilderCasualidadLibre() {
        reset();
    }

    @Override
    public void reset() {
        casualidad = new Casualidades() {
            /**
             * Lo que hace es simplemente {@code player.setFree(true)}
             * @param player
             */
            @Override
            public void realizarEfecto(Jugador player) {
                InfoAlertBuilder builder = new InfoAlertBuilder();
                builder.buildTitle("Casualidad");
                builder.buildHeader(casualidad.getName());
                builder.buildContent(casualidad.getDescription());

                builder.getAlert().showAndWait();

                player.setFree(true);
            }
        };
    }
}
