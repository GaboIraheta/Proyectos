package Casualidades.Builders;

import Casillas.CasillaAdquirible;
import Casillas.Propiedades.Propiedad;
import Casualidades.Casualidades;
import Jugadores.Jugador;

public class BuilderCasualidadImpuestoCasas extends BuilderCasualidad{

    /**
     * Arquitecto concreto para la casualidad Pagar, pero que calcula el precio dependiendo de las casas y
     * hoteles que posea el jugador. Su método {@code realizarEfecto} se encarga de calcular dicho valor para
     * luego restar esa cantidad a su balance. No utiliza {@code value}, pues lo calcula automáticamente.
     */
    public BuilderCasualidadImpuestoCasas() {
        reset();
    }

    @Override
    public void reset() {
        casualidad = new Casualidades() {
            @Override
            public void realizarEfecto(Jugador player) {
                int total = 0;

                System.out.println(casualidad.getName());
                System.out.println(casualidad.getDescription());

                for (CasillaAdquirible propiedad : player.getPropiedades()){
                    if (propiedad instanceof Propiedad){
                        if (((Propiedad)propiedad).getNumeroCasas() == 5){
                            total += 100;
                        }
                        else {
                            total += (((Propiedad)propiedad).getNumeroCasas() * 25); //this should work even for the
                        }
                    }
                }

                if(total == 0){
                    System.out.println("No posee casas ni hoteles en su propiedad, no paga nada!");
                }
                else {
                    System.out.println("Debes pagar un total de: $" + total);
                }
                player.pagar(total);
            }
        };
    }
}
