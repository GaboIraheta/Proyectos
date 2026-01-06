package Casualidades.Builders;

import Casualidades.Casualidades;
import Jugadores.Jugador;

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
                System.out.println(casualidad.getName());
                System.out.println(casualidad.getDescription());

                player.pagar(casualidad.getValue());
            }
        };
    }

    public void buildValue(int value){
        casualidad.setValue(value);
    }
}
