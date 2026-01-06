package org.example.monopoly.models.Casillas.FactoryCasillas;

import org.example.monopoly.models.Casillas.Casilla;

/*
* Factory para la creación de todas las casillas especiales que existen en el juego
*/
public interface FactoryCasillasEspeciales {
    
    Casilla CreateCasillaEspecial();
}
