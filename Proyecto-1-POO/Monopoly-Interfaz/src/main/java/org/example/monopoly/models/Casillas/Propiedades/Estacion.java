package org.example.monopoly.models.Casillas.Propiedades;

import org.example.monopoly.gui.controller.alerts.AlertBuilder;
import org.example.monopoly.gui.controller.alerts.ComprarAlertBuilder;
import org.example.monopoly.gui.controller.alerts.ErrorAlertBuilder;
import org.example.monopoly.gui.controller.alerts.InfoAlertBuilder;
import org.example.monopoly.models.Casillas.CasillaAdquirible;
import org.example.monopoly.models.Jugadores.Jugador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Estacion extends CasillaAdquirible {

    String content;

    /**@param nombre
     * @param renta
     * Metodo constructor de la clase
     * */

    public Estacion(String nombre, ArrayList<Integer> renta) {
        super(nombre, "Estacion de servicio accesible a la comunidad", 200, renta, 100);
        this.content = "\tESTACIÓN:\t\t" + nombre + "\n\tDESCRIPCIÓN:\n\t\t" + descripcion + "\n\tPRECIO:\t\t$" + precio + "\n\tHIPOTECA:\t\t$" + hipoteca; //this is dumb
    }

    /**@param player recibe un jugador, el cual es el jugador que cayo en la casilla y para el cual
     * la casilla va a ejecutar su accion
     * El metodo se encarga de verificar primeramente si la estacion tiene propietario
     * Si posee entonces solo muestra el mensaje de que puede continuar y destruye el metodo
     * con la instruccion return
     * Luego verifica si el propietario es nulo si es que la primera verificacion fallo
     * Si es nulo entonces le da la opcion al jugador para comprar la estacion implementando
     * toda la logica para realizar dicha accion
     * Si no es nulo realiza una verificacion dentro del bloque else
     * realizar una verificacion interna a ver si la estacion
     * se encuentra hipotecada, si esto no se cumple entonces le cobra la renta normalmente
     * implementando la logica para que el jugador que esta ahi le pague al jugador propietario
     * Si la estacion esta hipotecada se implementa la logica para que el jugador le pague al banco*/

    @Override
    public void realizarAccion(Jugador player) throws IOException{

        AlertBuilder builder;

        if(propietario == player) {

            builder = new InfoAlertBuilder();
            builder.buildTitle("Estación");
            builder.buildHeader(content);
            builder.buildContent("Bienvenido, " + player.getNombre() + ". Disfrute el paso por su propiedad");
            builder.getAlert().showAndWait();

            return;
        }

        if(propietario == null) {

            builder = new ComprarAlertBuilder();
            builder.buildTitle("Propiedad");
            builder.buildHeader(content);
            builder.buildContent("¿Desea comprar la estación " + nombre + "?");

            if(((ComprarAlertBuilder)builder).getSelection()) {

                if(player.getBalance() < precio){

                    builder = new ErrorAlertBuilder();
                    builder.buildTitle("Estación");
                    builder.buildHeader("No se pudo realizar la adquisición");
                    builder.buildContent("You poor LMAO!");
                    builder.getAlert().showAndWait();

                    return;
                }

                propietario = player;
                player.pagar(precio);
                player.adquirir_propiedad(this);

                builder = new InfoAlertBuilder();
                builder.buildTitle("Estación");
                builder.buildContent(player.getNombre() + " ha comprado la estación " + nombre);
                builder.getAlert().showAndWait();

            }

        } else {

            if(!isHipotecado()) {

                builder = new InfoAlertBuilder();
                builder.buildTitle("Estación");
                builder.buildHeader(content);

                int cantidad = 0;

                for(CasillaAdquirible propiedad : propietario.getPropiedades()) {

                    if(propiedad instanceof Estacion) {

                        cantidad++;
                    }
                }

                builder.buildContent("La propiedad ya ha sido adquirida por " + propietario.getNombre() + "\n" +  player.getNombre() + " debe pagar renta al propietario de la estacion " + nombre + "\nValor de la renta: $" + renta.getFirst()*cantidad);
                builder.getAlert().showAndWait();


                player.pagar(renta.getFirst()*cantidad);
                propietario.cobrar(renta.getFirst()*cantidad);

            } else {

                builder = new InfoAlertBuilder();
                builder.buildTitle("Estación");
                builder.buildHeader(content);
                builder.buildContent("La estación " + nombre + " está hipotecada\nLe pagará $" + renta.getFirst() + " al banco");
                builder.getAlert().showAndWait();

                player.pagar(renta.getFirst());
            }
        }
    }
}
