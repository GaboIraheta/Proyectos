package Casillas.FactoryCasillas.FactoriesCasillaAdquirible;

import Casillas.Casilla;
import Casillas.FactoryCasillas.FactoryCasillaAdquirible;
import Casillas.Propiedades.Servicios;

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
