package org.example.monopoly.models.Tablero;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import org.example.monopoly.gui.controller.TableroController;
import org.example.monopoly.models.Casillas.*;
import org.example.monopoly.models.Casillas.Propiedades.*;
import org.example.monopoly.models.Casillas.CasillasEspeciales.Carcel;
import org.example.monopoly.models.Jugadores.*;
import org.example.monopoly.models.Resources.*;
import org.example.monopoly.models.Casillas.FactoryCasillas.*;
import org.example.monopoly.models.Casillas.FactoryCasillas.FactoriesCasillaPropiedad.*;
import org.example.monopoly.models.Casillas.FactoryCasillas.FactoriesCasillaAdquirible.*;
import org.example.monopoly.models.Casillas.FactoryCasillas.FactoriesCasillasEspeciales.*;

public class Tablero {

    private final ArrayList<Casilla> casillas; //coleccion de casillas que simulan el tablero como tal
    private final ArrayList<Jugador> jugadores; //coleccion de jugadores
    private static Tablero tablero; //objeto tablero que va a almacenar la unica instancia de esta clase

    /**
     * Se define el constructor de la clase privado
     * Se implementa el patron de diseno Singleton
     */
    private Tablero() {
        casillas = agregarCasilla();
        jugadores = new ArrayList<>();
    }

    /**
     * Metodo estatico que devuelve la instancia unica de la clase
     * Si ya existe una instancia almacenada retorna dicha instancia de tablero
     * Si no existe ninguna instancia almacenada la crea y la devuelve
     *
     * @return una instancia de tablero
     */

    public static Tablero get_tablero() {

        if (tablero == null) {

            tablero = new Tablero();
        }

        return tablero;
    }

    //getters
    public ArrayList<Casilla> getCasillas() {
        return casillas;
    }

    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }

    /**
     * @param jugador metodo que agrega cada jugador que recibe
     *                a la lista de jugadores
     */

    public void agregarJugador(Jugador jugador) {
        jugadores.add(jugador);
    }

    /**
     * Se encarga de leer el archivo de tablero.txt y llenar un array el cual
     * el metodo devuelve para llenar el array de casillas necesario
     * para el tablero
     *
     * @return El array de tablero lleno
     */

    private ArrayList<Casilla> agregarCasilla() {
        FactoryCasillasEspeciales factoryEspecial;
        FactoryCasillaAdquirible factoryAdquirible;
        FactoryCasillaPropiedad factoryPropiedad = new FactoryPropiedad();
        ArrayList<Casilla> tablero = new ArrayList<>();
        ArrayList<Integer> precios = new ArrayList<>();
        Casilla casilla;
        Enum type;
        String name, price, hipoteca, housePrice, description;


        try {
            Scanner sc;
            try {
                Path file = Path.of("MONOPOLY/TextFiles/Tablero.txt");
                sc = new Scanner(file);
            } catch (Exception e){
                Path file = Path.of("TextFiles/Tablero.txt");
                sc = new Scanner(file);
            }

            while (sc.hasNextLine()) {
                String Type = sc.next();

                try {
                    type = ColorCasilla.valueOf(Type);
                    name = sc.next().replace(".", " ");
                    price = sc.next();
                    for (int i = 0; i < 6; ++i) {
                        precios.add(Integer.parseInt(sc.next()));
                    }

                    housePrice = sc.next();
                    hipoteca = sc.next();
                    description = "Propiedad de color";

                    casilla = factoryPropiedad.createCasillaPropiedad(name, description, Integer.parseInt(price), (ArrayList<Integer>) precios.clone(), Integer.parseInt(hipoteca), (ColorCasilla) type, Integer.parseInt(housePrice));

                    tablero.add(casilla);
                    precios.clear();

                } catch (IllegalArgumentException e) {
                    type = ExtraEnums.valueOf(Type);

                    switch (type) {
                        case ExtraEnums.CARCEL -> {
                            factoryEspecial = new FactoryCarcel();
                            casilla = factoryEspecial.CreateCasillaEspecial();
                        }
                        case ExtraEnums.CASUALIDAD -> {
                            factoryEspecial = new FactoryCasualidad();
                            casilla = factoryEspecial.CreateCasillaEspecial();
                        }
                        case ExtraEnums.ENTRADA -> {
                            factoryEspecial = new FactoryEntrada();
                            casilla = factoryEspecial.CreateCasillaEspecial();
                        }
                        case ExtraEnums.ESTACION -> {
                            factoryAdquirible = new FactoryEstacion(sc.next().replace(".", " "));
                            casilla = factoryAdquirible.CreateCasillaAdquirible();
                        }
                        case ExtraEnums.GOJAIL -> {
                            factoryEspecial = new FactoryGoJail();
                            casilla = factoryEspecial.CreateCasillaEspecial();
                        }
                        case ExtraEnums.HACIENDA -> {
                            factoryEspecial = new FactoryHacienda();
                            casilla = factoryEspecial.CreateCasillaEspecial();
                        }
                        case ExtraEnums.PARADALIBRE -> {
                            factoryEspecial = new FactoryParadaLibre();
                            casilla = factoryEspecial.CreateCasillaEspecial();
                        }
                        default -> {
                            factoryAdquirible = new FactoryServicios(sc.next().replace(".", " "));
                            casilla = factoryAdquirible.CreateCasillaAdquirible();
                        }
                    }

                    tablero.add(casilla);

                }
            }

            return tablero;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}