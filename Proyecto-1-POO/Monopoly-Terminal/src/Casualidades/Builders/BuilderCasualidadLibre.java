package Casualidades.Builders;

import Casualidades.Casualidades;
import Jugadores.Jugador;

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
                System.out.println(casualidad.getName());
                System.out.println(casualidad.getDescription());

                player.setFree(true);
            }
        };
    }
}
