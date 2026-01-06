package org.example.monopoly.models.Casillas.FactoryCasillas.FactoriesCasillasEspeciales;

import org.example.monopoly.models.Casillas.Casilla;
import org.example.monopoly.models.Casillas.CasillasEspeciales.Entrada;
import org.example.monopoly.models.Casillas.FactoryCasillas.FactoryCasillasEspeciales;

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
