package Casillas.FactoryCasillas.FactoriesCasillaPropiedad;

import java.util.ArrayList;

import Casillas.Casilla;
import Casillas.FactoryCasillas.FactoryCasillaPropiedad;
import Casillas.Propiedades.ColorCasilla;
import Casillas.Propiedades.Propiedad;

/**
 * Implementacion de la factory de la casilla propiedad para crear las propiedades generales
 */
public class FactoryPropiedad implements FactoryCasillaPropiedad {

    /**@param nombre
     * @param precioCasa
     * @param colorCasilla
     * @param descripcion
     * @param hipoteca
     * @param renta
     * @param precio
     * @return Casilla
     * metodo para crear casilla adquirible de propiedad
     * */
    public Casilla createCasillaPropiedad(String nombre, String descripcion, int precio, ArrayList<Integer> renta, int hipoteca, ColorCasilla colorCasilla, int precioCasa) {

        return new Propiedad(nombre, descripcion, precio, renta, hipoteca, colorCasilla, precioCasa);
    }
}
