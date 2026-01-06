package Casillas.FactoryCasillas.FactoriesCasillaAdquirible;

import Casillas.Casilla;
import Casillas.FactoryCasillas.FactoryCasillaAdquirible;
import Casillas.Propiedades.Estacion;

import java.util.ArrayList;

/*Clase para crear objetos de estacion
* */
public class FactoryEstacion implements FactoryCasillaAdquirible {

    private String name;

    /**@param name
     * metodo constructor del factory
     * */
    public FactoryEstacion(String name) {
        this.name = name;
    }

    /**@return Casilla
     * metodo que retorna la casilla adquirible de estacion
     * */
    public Casilla CreateCasillaAdquirible() {
        ArrayList<Integer> renta = new ArrayList<>();
        renta.add(25);
        return new Estacion(this.name, renta);
    }
}
