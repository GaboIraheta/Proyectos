package org.example.monopoly.models.Casillas.FactoryCasillas.FactoriesCasillasEspeciales;

import org.example.monopoly.models.Casillas.Casilla;
import org.example.monopoly.models.Casillas.CasillasEspeciales.GoJail;
import org.example.monopoly.models.Casillas.FactoryCasillas.FactoryCasillasEspeciales;

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
