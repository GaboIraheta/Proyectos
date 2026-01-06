package Casualidades.Builders;

import Casillas.CasillasEspeciales.Entrada;
import Casualidades.Casualidades;
import Jugadores.Jugador;
import Tablero.Tablero;

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
                System.out.println(casualidad.getName());
                System.out.println(casualidad.getDescription());

                player.setPosicion(casualidad.getValue());
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
