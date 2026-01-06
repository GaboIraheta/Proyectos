package Casillas;

import Jugadores.Jugador;

import java.io.IOException;

/**
 * Interfaz que contiene la declaracion del metodo de realizar accion que implementan todas las casillas existentes
 */
public interface CasillaMethods {
    void realizarAccion(Jugador player) throws IOException, InterruptedException;
}
