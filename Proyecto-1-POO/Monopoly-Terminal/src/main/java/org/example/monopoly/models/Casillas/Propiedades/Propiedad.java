package org.example.monopoly.models.Casillas.Propiedades;
import java.io.IOException;
import java.util.ArrayList;

import org.example.monopoly.gui.controller.alerts.AlertBuilder;
import org.example.monopoly.gui.controller.alerts.ComprarAlertBuilder;
import org.example.monopoly.gui.controller.alerts.ErrorAlertBuilder;
import org.example.monopoly.gui.controller.alerts.InfoAlertBuilder;
import org.example.monopoly.models.Casillas.CasillaAdquirible;
import org.example.monopoly.models.Jugadores.Jugador;

public class Propiedad extends CasillaAdquirible {

    String content;

    /*
    * Atributos que posee la clase propiedad
    * */
    private final ColorCasilla colorCasilla;
    private final int precioCasa;
    private int numeroCasas;

    /**@param nombre
     * @param descripcion
     * @param precio
     * @param renta
     * @param hipoteca
     * @param colorCasilla
     * @param precioCasa
     * metodo constructor de la clase
     * */

    public Propiedad(String nombre, String descripcion, int precio, ArrayList<Integer> renta,
                     int hipoteca, ColorCasilla colorCasilla, int precioCasa) {
        super(nombre, descripcion, precio, renta, hipoteca);
        this.colorCasilla = colorCasilla;
        this.precioCasa = precioCasa;
        this.numeroCasas = 0;
        this.content = "\tPROPIEDAD:\t" + nombre + "\n\tDESCRIPCIÓN:\n\t" + descripcion +
                "\n\tGRUPO DE COLOR:\t" + colorCasilla + "\n\tPRECIO:\t$" + precio +
                "\n\tRENTA:\t$" + renta.get(numeroCasas) + "\n\tPRECIO DE CASAS:\t$" + precioCasa +
                "\n\tHIPOTECA:\t$" + hipoteca;
    }

    /*Metodos setter y getters de los atributos de la clase
    * propiedad, para poder acceder a ellos y actualizarlos
    * en caso de ser necesario
    * */

    public int getNumeroCasas() {
        return numeroCasas;
    }

    public void setNumeroCasas(int numeroCasas) {
        this.numeroCasas = numeroCasas;
    }

    public int getPrecioCasa() {
        return precioCasa;
    }

    public ColorCasilla getColorCasilla() {
        return colorCasilla;
    }

    /**@param player recibe un jugador, el cual es el jugador que cayo en la casilla y para el cual
     * la casilla va a ejecutar su accion
     * El metodo se encarga de verificar primeramente si la propiedad tiene propietario
     * Si posee entonces solo muestra el mensaje de que puede continuar y destruye el metodo
     * con la instruccion return
     * Luego verifica si el propietario es nulo si es que la primera verificacion fallo
     * Si es nulo entonces le da la opcion al jugador para comprar la propiedad implementando
     * toda la logica para realizar dicha accion
     * Si no es nulo realiza una verificacion dentro del bloque else
     * realiza una verificacion interna a ver si la propiedad
     * se encuentra hipotecada, si esto no se cumple entonces le cobra la renta normalmente
     * que en este caso, sera segun el numero de casas que posea el valor de la renta, y a menos que
     * el numero de casas sea cero y el grupo de color este completo, entonces le cobrara solamente
     * el doble del valor de la renta, implementando la logica para que el jugador que esta ahi le pague
     * al jugador propietario, Si la propiedad esta hipotecada se implementa la logica
     * para que el jugador le pague al banco
     * */

    @Override
    public void realizarAccion(Jugador player) throws IOException {

        AlertBuilder builder;

        if(propietario == player) {

            builder = new InfoAlertBuilder();
            builder.buildTitle("Propiedad");
            builder.buildHeader(content);
            builder.buildContent("Bienvenido, " + player.getNombre() + ". Disfrute el paso por su propiedad");

            builder.getAlert().showAndWait();

            return;
        }

        if(propietario == null) {

            builder = new ComprarAlertBuilder();
            builder.buildTitle("Propiedad");
            builder.buildHeader(content);
            builder.buildContent("¿Desea comprar la propiedad " + nombre + "?");

            if(((ComprarAlertBuilder)builder).getSelection()) {

                if(player.getBalance() < precio){

                    builder = new ErrorAlertBuilder();
                    builder.buildTitle("Propiedad");
                    builder.buildHeader("No se pudo realizar la adquisición");
                    builder.buildContent("You poor LMAO!");
                    builder.getAlert().showAndWait();

                    return;
                }

                propietario = player;
                player.pagar(precio);
                player.adquirir_propiedad(this);

                builder = new InfoAlertBuilder();
                builder.buildTitle("Propiedad");
                builder.buildContent(player.getNombre() + " ha comprado la propiedad " + nombre);
                builder.getAlert().showAndWait();

            }

        } else {

            if (!isHipotecado) {

                builder = new InfoAlertBuilder();
                builder.buildTitle("Propiedad");
                builder.buildHeader(content);

                if (grupoCompleto(propietario) && numeroCasas == 0) {

                    builder.buildContent("La propiedad ya ha sido adquirida por " + propietario.getNombre() + "\n" +  player.getNombre() + " debe pagar renta al propietario de " + nombre + "\nValor de la renta: $" + renta.getFirst()*2);
                    builder.getAlert().showAndWait();

                    player.pagar(renta.getFirst()*2);
                    propietario.cobrar(renta.getFirst()*2);

                } else {

                    builder.buildContent("La propiedad ya ha sido adquirida por " + propietario.getNombre() + "\n" +  player.getNombre() + " debe pagar renta al propietario de " + nombre + "\nValor de la renta: $" + renta.get(numeroCasas));
                    builder.getAlert().showAndWait();

                    player.pagar(renta.get(numeroCasas));
                    propietario.cobrar(renta.get(numeroCasas));
                }

            }else {

                builder = new InfoAlertBuilder();
                builder.buildTitle("Propiedad");
                builder.buildHeader(content);
                builder.buildContent("La estación " + nombre + " está hipotecada\nLe pagará $" + renta.getFirst() + " al banco");
                builder.getAlert().showAndWait();

                player.pagar(renta.getFirst());
            }
        }
    }

    /**@param player este metodo recibe un jugador, el cual es el jugador del que se desea
     * verificar si posee el grupo de color de propiedades completo, ya sea para que pueda
     * comprar casas para alguna propiedad o para cobrar el doble de la renta sin tener
     * ninguna casa o cobrar el valor de la renta segun el numero de casas verificando
     * que puede cobrar eso, lo cual es solo si el grupo de color esta completo
     * El metodo recorre el array de propiedades del jugador y compara los colores de las
     * casillas con el color de this.propiedad, aumentando un contador en uno por cada coincidencia
     * El valor final del counter tendra el numero de propiedades con el color this.colorCasilla
     * finalmente solo verifica si es azul o cafe que solo son dos propiedades para retornar true
     * y si no solo evalua que el counter sea igual a tres
     * @return true si se cumple
     * @return false si no se cumple
     * */

    public boolean grupoCompleto(Jugador player) {

        int counter = 0;

        for(CasillaAdquirible propiedad : player.getPropiedades()) {

            if (propiedad instanceof Propiedad) {

                if (((Propiedad) propiedad).colorCasilla == this.colorCasilla) {

                    counter++;
                }

            }
        }

        if(((this.colorCasilla == ColorCasilla.AZUL) || (this.colorCasilla == ColorCasilla.CAFE)) && counter == 2) {

            return true;

        } else return counter == 3;
    }
}

