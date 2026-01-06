package Casualidades;
import Jugadores.Jugador;

import java.io.IOException;

/**
 * Clase encargada de manejar los distintos tipos de casualidades, producto que generan los
 * builders concretos. Posee los atributos {@code name:String}, {@code description:String} y {@code value:int}.
 * {@code value} es usado para hacer referencia a dinero o posición, dependiendo del builder concreto.
 */

public abstract class Casualidades {
    private TipoCasualidad type;
    private String name;
    private String description;
    private int value;

    //El constructor vacío es necesario para la implementación del builder.
    public Casualidades() {   }

    //Las casualidades realizan diversos tipos de acciones, y estas acciones también son distintas en
    //magnitud. De ahí, el método abstracto, que se definirá en cada builder concreto. No confundir con
    //realizarAccion() que es un método para las casillas.
    public abstract void realizarEfecto(Jugador player) throws IOException, InterruptedException;

    //setters y getters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
