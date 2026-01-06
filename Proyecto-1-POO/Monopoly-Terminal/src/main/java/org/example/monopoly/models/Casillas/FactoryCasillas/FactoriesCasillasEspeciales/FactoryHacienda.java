package org.example.monopoly.models.Casillas.FactoryCasillas.FactoriesCasillasEspeciales;

import org.example.monopoly.models.Casillas.Casilla;
import org.example.monopoly.models.Casillas.CasillasEspeciales.Hacienda;
import org.example.monopoly.models.Casillas.FactoryCasillas.FactoryCasillasEspeciales;

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
