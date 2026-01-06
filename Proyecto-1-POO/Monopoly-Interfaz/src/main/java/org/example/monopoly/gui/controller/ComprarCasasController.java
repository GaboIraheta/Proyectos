package org.example.monopoly.gui.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.example.monopoly.gui.controller.InterfacesControllers.commonMethods;
import org.example.monopoly.models.Casillas.Casilla;
import org.example.monopoly.models.Casillas.CasillaAdquirible;
import org.example.monopoly.models.Casillas.Propiedades.Propiedad;
import org.example.monopoly.models.Jugadores.Jugador;
import org.example.monopoly.models.Tablero.Tablero;

import java.util.Objects;

public class ComprarCasasController implements commonMethods {

    @FXML
    ComboBox<Propiedad> comboBoxPropiedades = new ComboBox<>();
    @FXML
    Label labelPrecioCasas, propiedadSeleccionada, controlSeleccion;

    TableroController tableroController = TableroController.getInstance();

    /**
     * Inicializa todos los componentes de la ventana
     */
    @FXML
    public void initialize() {
        controlSeleccion.setVisible(false);

        Tablero tablero = Tablero.get_tablero();

        Jugador jugador = null;

        for (Jugador player : tablero.getJugadores()) {
            if (Objects.equals(player, tablero.getJugadores().get(TableroController.getInstance().getTurno() % InicioController.getJugadores()))) {
                jugador = player;
            }
        }

        try {
            assert jugador != null;
            for (CasillaAdquirible propiedad : jugador.getPropiedades()) {
                if (propiedad instanceof Propiedad && !comboBoxPropiedades.getItems().contains(propiedad)) {
                    comboBoxPropiedades.getItems().add((Propiedad) propiedad);
                }
            }

            ObservableList<Propiedad> itemsPropiedades = FXCollections.observableArrayList(comboBoxPropiedades.getItems());
            comboBoxPropiedades.setItems(itemsPropiedades);

        } catch(Exception e) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Ha ocurrido un error.");
            alerta.setContentText("La carga de las propiedades ha fallado.");
            alerta.showAndWait();
        }

        //esto permite configurar que al seleccionar un item del comboBox se actualicen dichos labels
        comboBoxPropiedades.setOnAction(actionEvent -> {

            Propiedad propiedad = comboBoxPropiedades.getValue();

            if(propiedad != null) {
                labelPrecioCasas.setText("$" + propiedad.getPrecioCasa());
                propiedadSeleccionada.setVisible(true);
                propiedadSeleccionada.setText(propiedad.getNombre() + " - " +
                        propiedad.getColorCasilla());
            }
        });

        //esto permite mostrar los atributos preferibles de la lista
        comboBoxPropiedades.setCellFactory(new Callback<>() {
            @Override
            public ListCell<Propiedad> call(ListView<Propiedad> propiedadListView) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(Propiedad propiedad, boolean empty) {
                        super.updateItem(propiedad, empty);
                        if (propiedad != null) {
                            setText(propiedad.getNombre() + " - " + propiedad.getColorCasilla());
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });
    }

    /**
     * Compra una casa para la propiedad tomando en cuenta si tiene el grupo de color completo de las propiedades
     */
    @FXML
    public void comprarCasaAction() {

        controlSeleccion.setVisible(false);
        Tablero tablero = Tablero.get_tablero();

        boolean flag = false;

        if(comboBoxPropiedades.getValue() == null) {
            controlSeleccion.setVisible(true);
            flag = true;
        }

        if(!flag) {

            Alert alerta;

            Jugador jugador = null;
            for(Jugador player : tablero.getJugadores()) {
                if(Objects.equals(player, tablero.getJugadores().get(tableroController.getTurno()%InicioController.getJugadores()))) {
                    jugador = player;
                }
            }

            Propiedad propiedad = comboBoxPropiedades.getValue();

            try {
                if (propiedad.getNumeroCasas() != 5 && propiedad.grupoCompleto(Objects.requireNonNull(jugador))) {

                    if (jugador.getBalance() > propiedad.getPrecioCasa()) {

                        jugador.pagar(propiedad.getPrecioCasa());
                        propiedad.setNumeroCasas(propiedad.getNumeroCasas() + 1);

                        int indexOfCasilla = 0;

                        for(Casilla casilla : tablero.getCasillas()) {

                            if(Objects.equals(casilla, propiedad)) {
                                indexOfCasilla = tablero.getCasillas().indexOf(casilla);
                                break;
                            }
                        }

                        alerta = new Alert(Alert.AlertType.CONFIRMATION);
                        alerta.setTitle("Confirmacion");
                        alerta.setHeaderText(jugador.getNombre() + " ha comprado una casa.");
                        alerta.setContentText("Una casa ha sido agregada a " + propiedad.getNombre() + ".");
                        alerta.showAndWait();

                        if(propiedad.getNumeroCasas() != 5) {

                            TableroController.buyHouses(indexOfCasilla, propiedad.getNumeroCasas());

                            tableroController.enableButtons();
                            closeWindow();
                        }

                        if (propiedad.getNumeroCasas() == 5) {
                            alerta = new Alert(Alert.AlertType.CONFIRMATION);
                            alerta.setTitle("Confirmacion");
                            alerta.setHeaderText("Felicidades! Has comprado cinco cosas en " + propiedad.getNombre());
                            alerta.setContentText("Ahora tienes un hotel!");
                            alerta.showAndWait();

                            tableroController.getCasas().get(indexOfCasilla).setText("HOTEL");

                            tableroController.enableButtons();
                            closeWindow();
                        }

                    } else {
                        alerta = new Alert(Alert.AlertType.INFORMATION);
                        alerta.setTitle("Confirmacion");
                        alerta.setHeaderText("Lo sentimos, " + jugador.getNombre() + ".");
                        alerta.setContentText("No tienes suficiente dinero en tu cuenta para comprar la casa.");
                        alerta.showAndWait();

                        tableroController.enableButtons();
                        closeWindow();
                    }

                } else {
                    assert jugador != null;
                    if (!propiedad.grupoCompleto(jugador)) {
                        alerta = new Alert(Alert.AlertType.WARNING);
                        alerta.setTitle("No se pudo realizar la compra");
                        alerta.setHeaderText("El grupo de color no esta completo.");
                        alerta.setContentText(jugador.getNombre() + " debes tener el grupo de color completo " +
                                " para comprar casas en tus propiedades");
                        alerta.showAndWait();

                        tableroController.enableButtons();
                        closeWindow();

                    } else {
                        alerta = new Alert(Alert.AlertType.INFORMATION);
                        alerta.setTitle("Informacion");
                        alerta.setHeaderText("La propiedad alcanzo su maximo de casas y hoteles.");
                        alerta.setContentText("No puedes comprar mas casas en esta propiedad.");
                        alerta.showAndWait();

                        tableroController.enableButtons();
                        closeWindow();
                    }
                }

            } catch(NullPointerException e) {
                alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("Error");
                alerta.setHeaderText("Ha ocurrido un error.");
                alerta.setContentText("El jugador no ha sido encontrado");
                alerta.showAndWait();

                tableroController.enableButtons();
                closeWindow();
            }
        }
    }

    /**
     * Llama a {@code closeWindow()} al presionar el botón cancelar
     */
    @FXML
    public void cancelarAction() {
        propiedadSeleccionada.setVisible(false);
        tableroController.enableButtons();
        closeWindow();
    }

    /**
     * Cierra la ventana
     */
    @FXML
    public void closeWindow() {
        ((Stage) propiedadSeleccionada.getScene().getWindow()).close();
    }
}
