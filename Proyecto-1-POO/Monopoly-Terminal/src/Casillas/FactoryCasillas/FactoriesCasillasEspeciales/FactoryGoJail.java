package Casillas.FactoryCasillas.FactoriesCasillasEspeciales;

import Casillas.Casilla;
import Casillas.CasillasEspeciales.GoJail;
import Casillas.FactoryCasillas.FactoryCasillasEspeciales;

/**
 * Implementacion de la factory de casillas especiales para crear la casilla de Go Jail
 */
public class FactoryGoJail implements FactoryCasillasEspeciales {

    /**@return Casilla
     * metodo para crear la casilla especial de GoJail
     * */
    public Casilla CreateCasillaEspecial() {

        return new GoJail();
    }
}
