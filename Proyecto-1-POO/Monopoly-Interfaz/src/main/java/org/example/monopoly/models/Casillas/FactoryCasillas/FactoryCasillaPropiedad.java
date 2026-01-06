package org.example.monopoly.models.Casillas.FactoryCasillas;

import java.util.ArrayList;

import org.example.monopoly.models.Casillas.Casilla;
import org.example.monopoly.models.Casillas.Propiedades.ColorCasilla;

/*
* Interfaz para la creacion de la casilla adquirible propiedad
* la cual no puede estar en la otra factory dado que recibe
* varios parametros en su constructo a diferencia de
* servicios y estacion
* */
public interface FactoryCasillaPropiedad {
    
    Casilla createCasillaPropiedad(String nombre, String descripcion, int precio, ArrayList<Integer> renta, int hipoteca, ColorCasilla colorCasilla, int precioCasa);
}
