package org.example.monopoly.models.Casillas.FactoryCasillas.FactoriesCasillasEspeciales;

import org.example.monopoly.models.Casillas.Casilla;
import org.example.monopoly.models.Casillas.CasillasEspeciales.WheelOfFortune;
import org.example.monopoly.models.Casillas.FactoryCasillas.FactoryCasillasEspeciales;

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
