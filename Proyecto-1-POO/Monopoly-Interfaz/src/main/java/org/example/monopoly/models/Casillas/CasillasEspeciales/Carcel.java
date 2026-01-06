package org.example.monopoly.models.Casillas.CasillasEspeciales;

import org.example.monopoly.gui.controller.alerts.InfoAlertBuilder;
import javafx.scene.control.Alert;
import org.example.monopoly.models.Casillas.Casilla;
import org.example.monopoly.models.Casualidades.Builders.BuilderCasualidad;
import org.example.monopoly.models.Jugadores.Jugador;
import org.example.monopoly.models.Jugadores.Preso;
import org.example.monopoly.models.Tablero.Tablero;
import org.example.monopoly.gui.controller.alerts.AlertBuilder;

import java.io.IOException;
import java.util.ArrayList;

public class Carcel extends Casilla {

    /**
     * Se declara el arreglo que contendrá a cada uno de los jugadores
     * cuando estos se encuentren presos, es decir, isPreso está en {@code true}
     * Además se crea un ArrayList toRemove
     * que contendrá temporalmente a los jugadores que deben salir
     * de la cárcel, después de todas las verificaciones se remueve all lo que
     * este dentro del array de presos que coincida con los objetos de torRemove
     */

    private final ArrayList<Preso> presos;

    private ArrayList<Preso> toRemove = new ArrayList<>();

    /*
     * getter para obtener el array de presos
     */
    public ArrayList<Preso> getPresos() {
        return presos;
    }

    //Constructor de la clase
    public Carcel() {
        this.nombre = "Carcel";
        this.descripcion = "Si caes en Carcel, puede visitar a tus amigos presos";
        this.presos = new ArrayList<>();
    }

    /**@param jugador recibe un objeto jugadorm el cual es el jugador que se quiere verificar que salga o entre
    * CheckPresos es un metodo que se encarga de verificar los presos que deben salir de la carcel
    * y que los jugadores que despues de un turno tienen true en su atributo isPreso, ingresen en la carcel
    * (internamente), en este metodo se utiliza toRemove, esto para no ocasionar una excepcion con respecto
    * a la eliminacion en la lista de presos
    * */

    public void checkPresos(Jugador jugador) {

        Tablero tablero = Tablero.get_tablero();

        for (Jugador player : tablero.getJugadores()) {

            if (player.isPreso() && searchPreso(player.getNombre()) == null){

                Preso preso = new Preso(player);
                preso.getPreso().setTurnosJugados(0);
                preso.getPreso().setJuegaDeNuevo(false);
                presos.add(preso);

            }
        }

        for (Preso ladron : presos) {

            if (ladron.getTurnosPreso() != 4 && !ladron.getPreso().isFree() && ladron.getPreso() == jugador) {

                ladron.setTurnosPreso(ladron.getTurnosPreso() + 1);

            } else if(ladron.getTurnosPreso() == 4 || ladron.getPreso().isFree() || !ladron.getPreso().isPreso()){

                ladron.getPreso().setPreso(false);
                toRemove.add(ladron);

                if(ladron.getPreso().isFree()){

                    Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
                    alerta.setTitle("Confirmacion");
                    alerta.setHeaderText("Que cuello!");
                    alerta.setContentText("Sale de la carcel con su tarjeta LIBRE.");
                    alerta.showAndWait();

                    ladron.getPreso().setFree(false);

                } else {

                    Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
                    alerta.setTitle("Confirmacion");
                    alerta.setHeaderText("Se cumplen tres turnos preso.");
                    alerta.setContentText(ladron.getPreso().getNombre() +
                            " sale de la carcel tras cumplir su condena.");
                    alerta.showAndWait();
                }
            }
        }

        presos.removeAll(toRemove);
    }

    /**@return este metodo retorna un objeto preso, el cual es el preso que se esta buscando
     * @param name recibe un nombre, el cual debe ser el nombre del jugador que se encuentra preso
     * El metodo se encarga de buscar el preso segun su nombre y retornar a dicho objeto si existe
     * una coincidencia
     * */

    public Preso searchPreso(String name){

        for (Preso preso : presos){

            if(preso.getPreso().getNombre().equals(name)){

                return preso;
            }
        }

        return null;
    }

    /**@param player recibe un jugador, el cual es el jugador que cayo en la casilla y para el cual
     * la casilla va a ejecutar su accion
     * El metodo se encarga de mostrar la informacion de la casilla, ademas muestra un mensaje al jugador
     * que le indica que se encuentra en la carcel pero solo de pasada
     */
    @Override
    public void realizarAccion(Jugador player) throws IOException {

        String content = "FELICIDADES " + player.getNombre() +
                ". Estás de pasadita\n" +
                "Puedes visitar a tus amigos corruptos\n";

        AlertBuilder builder = new InfoAlertBuilder();
        builder.buildTitle(nombre);
        builder.buildHeader(descripcion);
        builder.buildContent(content);
        builder.getAlert().showAndWait();
    }
}
