package Casillas.FactoryCasillas.FactoriesCasillasEspeciales;

import Casillas.Casilla;
import Casillas.CasillasEspeciales.ParadaLibre;
import Casillas.FactoryCasillas.FactoryCasillasEspeciales;

/**
 * Implementacion de la factory de casillas especiales para crear la casilla de paradaLibre
 */
public class FactoryParadaLibre implements FactoryCasillasEspeciales {

    /**@return Casilla
     * metodo para crear la casilla especial de parada libre
     * */
    public Casilla CreateCasillaEspecial() {

        return new ParadaLibre();
    }
}
