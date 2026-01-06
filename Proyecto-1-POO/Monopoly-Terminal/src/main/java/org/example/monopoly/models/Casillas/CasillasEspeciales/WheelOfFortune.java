package org.example.monopoly.models.Casillas.CasillasEspeciales;

import org.example.monopoly.gui.controller.alerts.AlertBuilder;
import org.example.monopoly.gui.controller.alerts.InfoAlertBuilder;
import org.example.monopoly.models.Casillas.Casilla;
import org.example.monopoly.models.Casualidades.Builders.*;
import org.example.monopoly.models.Casualidades.Casualidades;
import org.example.monopoly.models.Jugadores.Jugador;

import org.example.monopoly.models.Casualidades.*;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class WheelOfFortune extends Casilla {

    /*
    * El arrayList de casualidades contiene todas las casualidades que pueden aparecer
    * en el juego cuando un jugador cae en una casilla de WheelOfFortune
    * Para esta clase ha sido implementado el patron de diseño Singleton dado que no debe
    * existir mas de un WheelOfFortune, ademas de un atributo position que lleva las posiciones
    * del array de casualidades
    * */

    private ArrayList<Casualidades> casualidades;
    private static WheelOfFortune instancia;

    //Getter para obtener el Array de casualidades
    public ArrayList<Casualidades> getCasualidades() {
        return casualidades;
    }

    /**@code Constructor de la clase
     * dicho constructor es privado dado que solo la clase misma debe poder instanciarse
     * */

    private WheelOfFortune() {
        this.nombre = "Casilla de casualidad";
        this.descripcion = "¿Qué mejor que incorporar una lotería todopoderosa en el proceso de crear un Monopolio?";
        casualidades = agregarCasualidad();
    }

    //Metodo para obtener la instancia unica de la clase
    public static WheelOfFortune getInstance() {

        if(instancia == null){
            instancia = new WheelOfFortune();
        }

        return instancia;
    }

    /**@param player recibe un jugador, el cual es el jugador que cayo en la casilla y para el cual
     * la casilla va a ejecutar su accion
     * El metodo se encarga de mostrar la informacion de la casilla
     * E implementa la logica para que el jugador tome una casualidad, y para la casualidad
     * realice su efecto al jugador en juego
     */

    @Override
    public void realizarAccion(Jugador player) throws IOException, InterruptedException {

        String content = player.getNombre() + " toma una casualidad . . .";

        InfoAlertBuilder builder = new InfoAlertBuilder();

        builder.buildTitle(nombre);
        builder.buildHeader(descripcion);
        builder.buildContent(content);
        builder.getAlert().showAndWait();

        Casualidades casualidadActual = casualidades.getFirst();
        casualidadActual.realizarEfecto(player);

        casualidades.remove(casualidadActual);
        casualidades.add(casualidadActual);

    }

    /**
     * Empieza a leer los datos obtenidos en el archivo {@code Casualidades.txt},
     * luego se verifica que tipo de casualidad es y se instancia un objeto del tipo de casualidad respectiva y
     * se agrega a la lista, cuando la lista este completamente llena, entonces la revolverá aleatoriamente
     * y se devuelve la lista revuelta
     *
     * @return la lista aleatoriamente revuelta de casualidades
     */

    public ArrayList<Casualidades> agregarCasualidad() {
        ArrayList<Casualidades> fill = new ArrayList<>();
        BuilderCasualidad builder = null;
        Enum type = null;

        try {
            Path file = Path.of("TextFiles/Casualidades.txt");
            Scanner sc = new Scanner(file);
            String cant;

            while (sc.hasNext()) {
                String line = sc.next();
                String name = sc.next().replace(".", " ");
                String description = sc.next().replace(".", " ");

                type = TipoCasualidad.valueOf(line);

                switch (type) {
                    case TipoCasualidad.IMPUESTO_CASAS:
                        builder = new BuilderCasualidadImpuestoCasas();
                        builder.buildName(name);
                        builder.buildDescription(description);
                        break;

                    case TipoCasualidad.PAGO:
                        cant = sc.next();
                        builder = new BuilderCasualidadPagar();
                        builder.buildName(name);
                        builder.buildDescription(description);
                        ((BuilderCasualidadPagar) builder).buildValue(Integer.parseInt(cant));
                        break;

                    case TipoCasualidad.COBRO:
                        cant = sc.next();
                        builder = new BuilderCasualidadCobrar();
                        builder.buildName(name);
                        builder.buildDescription(description);
                        ((BuilderCasualidadCobrar) builder).buildValue(Integer.parseInt(cant));
                        break;

                    case TipoCasualidad.MOVIMIENTO:
                        cant = sc.next();
                        builder = new BuilderCasualidadMover();
                        builder.buildName(name);
                        builder.buildDescription(description);
                        ((BuilderCasualidadMover) builder).buildValue(Integer.parseInt(cant));
                        break;

                    case TipoCasualidad.LIBRE:
                        builder = new BuilderCasualidadLibre();
                        builder.buildName(name);
                        builder.buildDescription(description);
                        break;
                    default:
                        System.out.println("How did you even managed to fuck this up?");

                }
                if (builder != null) fill.add(builder.getCasualidad());
            }
        }catch (IOException e){
            throw new RuntimeException(e);
        }

        Collections.shuffle(fill);
        return fill;
    }
}
