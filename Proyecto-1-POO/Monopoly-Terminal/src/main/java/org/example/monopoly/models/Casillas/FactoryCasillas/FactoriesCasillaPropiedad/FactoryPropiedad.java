package org.example.monopoly.models.Casillas.FactoryCasillas.FactoriesCasillaPropiedad;

import java.util.ArrayList;

import org.example.monopoly.models.Casillas.Casilla;
import org.example.monopoly.models.Casillas.FactoryCasillas.FactoryCasillaPropiedad;
import org.example.monopoly.models.Casillas.Propiedades.ColorCasilla;
import org.example.monopoly.models.Casillas.Propiedades.Propiedad;

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
