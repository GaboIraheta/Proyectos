package Casillas.FactoryCasillas.FactoriesCasillasEspeciales;

import Casillas.Casilla;
import Casillas.CasillasEspeciales.Carcel;
import Casillas.FactoryCasillas.FactoryCasillasEspeciales;

/**
 * Implementacion del factory de casillas especiales para crear la casilla de carcel
 */
public class FactoryCarcel implements FactoryCasillasEspeciales {

    /**@return Casilla
     * metodo para crear casilla especial de carcel
     * */
    public Casilla CreateCasillaEspecial() {
        
        return new Carcel();
    }
}
