package org.example.monopoly.models.Casillas.FactoryCasillas.FactoriesCasillaAdquirible;

import org.example.monopoly.models.Casillas.Casilla;
import org.example.monopoly.models.Casillas.FactoryCasillas.FactoryCasillaAdquirible;
import org.example.monopoly.models.Casillas.Propiedades.Servicios;

/*
* Clase para crear objeto de servicios
* */
public class FactoryServicios implements FactoryCasillaAdquirible {
    
    private String name;

    /**@param name
     * metodo constructor del factory
     * */
    public FactoryServicios(String name) {
        this.name = name;
    }

    /**@return Casilla
     * metodo que retorna la casilla adquirible de servicios*/
    public Casilla CreateCasillaAdquirible() {

        return new Servicios(this.name);
    }
}
