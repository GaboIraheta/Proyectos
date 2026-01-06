package org.example.monopoly.models.Casillas.FactoryCasillas.FactoriesCasillasEspeciales;

import org.example.monopoly.models.Casillas.Casilla;
import org.example.monopoly.models.Casillas.CasillasEspeciales.Carcel;
import org.example.monopoly.models.Casillas.FactoryCasillas.FactoryCasillasEspeciales;

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
