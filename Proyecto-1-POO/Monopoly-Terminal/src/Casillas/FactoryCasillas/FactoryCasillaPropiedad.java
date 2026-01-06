package Casillas.FactoryCasillas;

import java.util.ArrayList;

import Casillas.Casilla;
import Casillas.Propiedades.ColorCasilla;

/*
* Interfaz para la creacion de la casilla adquirible propiedad
* la cual no puede estar en la otra factory dado que recibe
* varios parametros en su constructo a diferencia de
* servicios y estacion
* */
public interface FactoryCasillaPropiedad {
    
    Casilla createCasillaPropiedad(String nombre, String descripcion, int precio, ArrayList<Integer> renta, int hipoteca, ColorCasilla colorCasilla, int precioCasa);
}
