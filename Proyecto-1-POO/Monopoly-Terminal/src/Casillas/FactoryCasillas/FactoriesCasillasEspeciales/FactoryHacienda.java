package Casillas.FactoryCasillas.FactoriesCasillasEspeciales;

import Casillas.Casilla;
import Casillas.CasillasEspeciales.Hacienda;
import Casillas.FactoryCasillas.FactoryCasillasEspeciales;

/**
 * Implementacion de la factory de casillas especiales para crear la casilla de hacienda
 */
public class FactoryHacienda implements FactoryCasillasEspeciales {

    /**@return Casilla
     * metodo para crear la casilla especial de hacienda
     * */
    public Casilla CreateCasillaEspecial() {

        return new Hacienda();
    }
}
