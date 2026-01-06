package Casillas.FactoryCasillas.FactoriesCasillasEspeciales;

import Casillas.Casilla;
import Casillas.CasillasEspeciales.Entrada;
import Casillas.FactoryCasillas.FactoryCasillasEspeciales;

/**
 * Implementacion de la factory de casillas especiales para crear la casilla de entrada
 */
public class FactoryEntrada implements FactoryCasillasEspeciales {

    /**@return Casilla
     * metodo para crear la casilla especial de entrada
     * */
    public Casilla CreateCasillaEspecial() {

        return new Entrada();
    }
}
