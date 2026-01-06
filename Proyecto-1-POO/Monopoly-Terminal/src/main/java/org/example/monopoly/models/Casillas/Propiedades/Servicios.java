package org.example.monopoly.models.Casillas.Propiedades;

import org.example.monopoly.gui.controller.alerts.AlertBuilder;
import org.example.monopoly.gui.controller.alerts.ComprarAlertBuilder;
import org.example.monopoly.gui.controller.alerts.ErrorAlertBuilder;
import org.example.monopoly.gui.controller.alerts.InfoAlertBuilder;
import org.example.monopoly.models.Casillas.CasillaAdquirible;
import org.example.monopoly.models.Jugadores.Jugador;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Servicios extends CasillaAdquirible {
    String content;

    /**@param nombre
     * metodo constructor de la clase
     * */
    public Servicios(String nombre) {
        super(nombre, "Servicios necesarios para la comunidad", 150, null, 75);
        this.content = "\tServicio: " + nombre + "\n\tDESCRIPCIÓN: " + descripcion + "\n\tPRECIO: $" + precio + "\n\tHIPOTECA: $" + hipoteca;
    }

    /**@param player recibe un jugador, el cual es el jugador que cayo en la casilla y para el cual
     * la casilla va a ejecutar su accion
     * El metodo se encarga de verificar primeramente si el servicio tiene propietario
     * Si posee entonces solo muestra el mensaje de que puede continuar y destruye el metodo
     * con la instruccion return
     * Luego verifica si el propietario es nulo si es que la primera verificacion fallo
     * Si es nulo entonces le da la opcion al jugador para comprar el servicio implementando
     * toda la logica para realizar dicha accion
     * Si no es nulo realiza una verificacion dentro del bloque else
     * realiza una verificacion interna a ver si el servicio
     * se encuentra hipotecado, si esto no se cumple entonces le cobra la renta normalmente
     * implementando la logica para que el jugador que esta ahi le pague al jugador propietario
     * En este caso la logica se implementa de tal manera que el jugador le pague al propietario
     * 10 veces lo que tira el dado si posee los dos servicios y si posee solo uno, que le pague
     * 5 veces lo que el dado marque
     * Si la propiedad esta hipotecada se implementa la logica para que el jugador le pague al banco
     * En este caso, pagandole 10 veces el valor de los dados*/

    @Override
    public void realizarAccion(Jugador player) throws IOException{

        AlertBuilder builder = null;

        if(propietario == player) {
            builder = new InfoAlertBuilder();
            builder.buildTitle("Servicio");
            builder.buildHeader("Bienvenido, " + player.getNombre() + ". Disfrute el paso por su propiedad");
            builder.buildContent(content);
            builder.getAlert().showAndWait();

            return;
        }

        if(propietario == null) {

            builder = new ComprarAlertBuilder();
            builder.buildTitle("Servicio");
            builder.buildHeader("¿Desea comprar el servicio " + nombre + "?");
            builder.buildContent(content);

            if(((ComprarAlertBuilder)builder).getSelection()) {

                if(player.getBalance() < precio){

                    builder = new ErrorAlertBuilder();
                    builder.buildTitle("Servicio");
                    builder.buildHeader("No se pudo realizar la adquisición");
                    builder.buildContent("You poor LMAO!");
                    builder.getAlert().showAndWait();
                    return;
                }

                propietario = player;
                player.pagar(precio);
                player.adquirir_propiedad(this);

                builder = new InfoAlertBuilder();
                builder.buildTitle("Servicio");
                builder.buildContent(player.getNombre() + " ha comprado el servicio " + nombre);
                builder.getAlert().showAndWait();

            }

        } else {

            int cantidad = 0;
            boolean temp = false;
            int tempDados;

            if(player.isJuegaDeNuevo()) {
                temp = true;
            }

            if(!isHipotecado()) {

                for(CasillaAdquirible propiedad : propietario.getPropiedades()) {
                    if(propiedad instanceof Servicios) {
                        cantidad++;
                    }
                }

                if(cantidad == 2){

                    builder = new InfoAlertBuilder();
                    builder.buildTitle("Servicio");
                    builder.buildHeader("El propietario posee todos los servicios");
                    builder.buildContent("Se pagará 10 veces el valor que indiquen los dados");
                    builder.getAlert().showAndWait();

                    tempDados = player.tirarDados();

                    builder.buildTitle("Servicio");
                    builder.buildHeader("El propietario posee todos los servicios");
                    builder.buildContent(player.getNombre() + " paga $" + (10*tempDados) + " al propietario del servicio " + nombre);
                    builder.getAlert().showAndWait();

                    player.pagar(10* tempDados);
                    this.getPropietario().cobrar(10*tempDados);

                } else {

                    builder = new InfoAlertBuilder();
                    builder.buildTitle("Servicio");
                    builder.buildHeader("El propietario es de solo un servicio");
                    builder.buildContent("Se pagará 5 veces el valor que indiquen los dados");
                    builder.getAlert().showAndWait();

                    tempDados = player.tirarDados();
                    System.out.println(player.getNombre() + " paga $" + (5*tempDados) + " al propietario del servicio " + nombre);

                    builder.buildTitle("Servicio");
                    builder.buildHeader("El propietario es de solo un servicio");
                    builder.buildContent(player.getNombre() + " paga $" + (5*tempDados) + " al propietario del servicio " + nombre);
                    builder.getAlert().showAndWait();

                    player.pagar(5* tempDados);
                    this.getPropietario().cobrar(5*tempDados);
                }

                player.setJuegaDeNuevo(temp);

            } else {

                builder = new InfoAlertBuilder();
                builder.buildTitle("Servicio");
                builder.buildHeader("El servicio " + nombre + " está hipotecado");
                builder.buildContent("Se pagará 10 veces el valor que indiquen los dados al banco");
                builder.getAlert().showAndWait();

                tempDados = player.tirarDados();

                builder.buildTitle("Servicio");
                builder.buildHeader("El servicio " + nombre + " está hipotecado");
                builder.buildContent(player.getNombre() + " paga $" + (10*tempDados) + " al banco");
                builder.getAlert().showAndWait();

                player.pagar(10*tempDados);
            }
        }
    }
}
