package org.example.monopoly.models;
import org.example.monopoly.models.Jugadores.Jugador;
import org.example.monopoly.models.Tablero.Tablero;

import java.io.IOException;
import java.util.Scanner;

public class Game {

    private int turno;

    /**
     * Método principal donde se desarrollará la totalidad del juego
     *
     * */
    public static void playGame() throws IOException {
        Scanner sc = new Scanner(System.in);
        int players;
        Tablero tablero = Tablero.get_tablero();
        String name;
        Jugador player;
        /*
         * Se muestra el menú para elegir la cantidad de jugadores  
         *
         */
        while (true) {

            System.out.println("Bienvenido a Monopoly!");
            System.out.println("======================");
            System.out.println("Cuantos jugadores participarán?");
            System.out.println("[1] 2 Jugadores");
            System.out.println("[2] 3 Jugadores");
            System.out.println("[3] 4 Jugadores");
            System.out.println("[4] 5 Jugadores");
            System.out.println("[5] 6 Jugadores");

            //logica implementada para agregar cada jugador
            try {
                players = Integer.parseInt(sc.nextLine());

                if (players < 1 || players > 5){
                    throw new Exception();
                }

                for (int i = 0; i < players + 1; ++i){
                System.out.println("Ingrese el nombre del jugador " + (i+1));
                name = sc.next();
                player = new Jugador(name);
                tablero.agregarJugador(player);
            }

            break;
            } catch (Exception e) {
                System.out.println("Ingrese una opción válida");
            }

        }


        System.out.println("Que comience el Juego!!!!");

    }

    public void setTurno(int turno) {
        this.turno = turno;
    }
}
