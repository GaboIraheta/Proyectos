package org.example.monopoly.models.Casillas.FactoryCasillas.FactoriesCasillasEspeciales;

import org.example.monopoly.models.Casillas.Casilla;
import org.example.monopoly.models.Casillas.CasillasEspeciales.ParadaLibre;
import org.example.monopoly.models.Casillas.FactoryCasillas.FactoryCasillasEspeciales;

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
