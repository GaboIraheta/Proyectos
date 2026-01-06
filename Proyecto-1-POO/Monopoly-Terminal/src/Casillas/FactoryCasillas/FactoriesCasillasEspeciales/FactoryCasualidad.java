package Casillas.FactoryCasillas.FactoriesCasillasEspeciales;

import Casillas.Casilla;
import Casillas.CasillasEspeciales.WheelOfFortune;
import Casillas.FactoryCasillas.FactoryCasillasEspeciales;

/**
 * Implementacion de la factory de casillas especiales para crear la casilla de WheelOfFortune
 */
public class FactoryCasualidad implements FactoryCasillasEspeciales {

    /**@return Casilla
     * metodo para obtener la instancia unica de WheelOfFortune
     * */
    public Casilla CreateCasillaEspecial() {

        return WheelOfFortune.getInstance();
    }

}
