package org.example.monopoly.gui.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.example.monopoly.gui.controller.InterfacesControllers.commonMethods;
import org.example.monopoly.models.Casillas.Casilla;
import org.example.monopoly.models.Casillas.CasillaAdquirible;
import org.example.monopoly.models.Casillas.Propiedades.Propiedad;
import org.example.monopoly.models.Jugadores.Jugador;
import org.example.monopoly.models.Tablero.Tablero;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class HipotecarController implements commonMethods {

    @FXML
    private Label controlSeleccion, propiedadSeleccionada;
    @FXML
    private ComboBox<CasillaAdquirible> comboBoxPropiedades = new ComboBox<>();
    private Alert alerta;

    /**
     * El método que inicializa todos los componentes del controller
     */
    @FXML
    public void initialize() {
        alerta = new Alert(Alert.AlertType.INFORMATION);
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
                if (!comboBoxPropiedades.getItems().contains(propiedad)) {
                    comboBoxPropiedades.getItems().add(propiedad);
                }
            }

            ObservableList<CasillaAdquirible> itemsPropiedades = FXCollections.observableArrayList(comboBoxPropiedades.getItems());
            comboBoxPropiedades.setItems(itemsPropiedades);

        } catch(Exception e) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Ha ocurrido un error.");
            alerta.setContentText("La carga de las propiedades ha fallado.");
            alerta.showAndWait();
        }

        comboBoxPropiedades.valueProperty().addListener((observable, oldValue, newValue) -> {
            controlSeleccion.setVisible(false);
            propiedadSeleccionada.setVisible(true);
            propiedadSeleccionada.setText(comboBoxPropiedades.getValue().getNombre());
        });

        comboBoxPropiedades.setCellFactory(new Callback<>() {
            @Override
            public ListCell<CasillaAdquirible> call(ListView<CasillaAdquirible> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(CasillaAdquirible item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item.getNombre() +
                                    (item instanceof Propiedad ? " - " + ((Propiedad) item).getColorCasilla() : "") +
                                    (item.isHipotecado() ? " - Hipotecado" : ""));
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });
    }

    /**
     * Es el método que permite la hipoteca de casas. Toma en cuenta si hay casas o no; además de verificar
     * si el jugador está en bancarrota para que este se vea forzado a hipotecar hasta que tenga un balance
     * positivo
     */
    @FXML
    public void hipotecarAction() {

        controlSeleccion.setVisible(false);

        boolean flag = false;

        if(comboBoxPropiedades.getValue() == null) {
            controlSeleccion.setVisible(true);
            flag = true;
        }

        if(!flag) {

            Jugador jugador = null;
            for(Jugador player : Tablero.get_tablero().getJugadores()) {
                if(Objects.equals(player, Tablero.get_tablero().getJugadores().get(TableroController.getInstance().getTurno()%InicioController.getJugadores()))) {
                    jugador = player;
                }
            }

            Jugador finalJugador = jugador;

            if(!comboBoxPropiedades.getValue().isHipotecado()) {

                if(comboBoxPropiedades.getValue() instanceof Propiedad &&
                        ((Propiedad) comboBoxPropiedades.getValue()).getNumeroCasas() != 0) {

                    //todo implementar logica de vender todas las casas con la alerta
                    alerta = new Alert(Alert.AlertType.WARNING);
                    alerta.setTitle("Cuidado!");
                    assert jugador != null;
                    alerta.setHeaderText(jugador.getNombre() + ", tu propiedad posee " +
                            ((Propiedad) comboBoxPropiedades.getValue()).getNumeroCasas() + " casas");
                    alerta.setContentText("Presiona OK si aceptas vender todas tus casas para hipotecar.");

                    ButtonType botonOK = new ButtonType("OK", ButtonBar.ButtonData.YES);
                    ButtonType botonCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.NO);

                    alerta.getButtonTypes().setAll(botonOK, botonCancelar);

                    Optional<ButtonType> result = alerta.showAndWait();

                    if(result.isPresent() && result.get() == botonOK) {
                        Jugador jugador1 = null;
                        for(Jugador player : Tablero.get_tablero().getJugadores()) {
                            if(Objects.equals(player, Tablero.get_tablero().getJugadores().get(TableroController.getInstance().getTurno()%InicioController.getJugadores()))) {
                                jugador1 = player;
                            }
                        }

                        finalJugador = jugador1;

                        AtomicInteger money = new AtomicInteger();

                        Alert alerta1;

                        while(((Propiedad) comboBoxPropiedades.getValue()).getNumeroCasas() != 0) {

                            ((Propiedad) comboBoxPropiedades.getValue()).setNumeroCasas(
                                    ((Propiedad) comboBoxPropiedades.getValue()).getNumeroCasas() - 1);

                            assert finalJugador != null;
                            finalJugador.cobrar(
                                    ((Propiedad) comboBoxPropiedades.getValue()).getPrecioCasa() / 2);
                            money.addAndGet((((Propiedad) comboBoxPropiedades.getValue()).getPrecioCasa() / 2));

                            alerta1 = new Alert(Alert.AlertType.CONFIRMATION);
                            alerta1.setTitle("Confirmacion");
                            alerta1.setHeaderText(finalJugador.getNombre() + " ha vendido todas las casas de " +
                                    comboBoxPropiedades.getValue().getNombre());
                            alerta1.setContentText("Te devuelven $" + money.get() + " por cada casa");
                            alerta.showAndWait();
                        }

                        comboBoxPropiedades.getValue().setHipotecado(true);
                        assert finalJugador != null;
                        finalJugador.cobrar(comboBoxPropiedades.getValue().getHipoteca());

                        //TODO aqui se debe poner esa logica de actualizar el label
                        int indexOfCasilla = 0;

                        for(Casilla casilla : Tablero.get_tablero().getCasillas()) {

                            if(Objects.equals(casilla, comboBoxPropiedades.getValue())) {
                                indexOfCasilla = Tablero.get_tablero().getCasillas().indexOf(casilla);
                                break;
                            }
                        }

                        TableroController.sendHouses(indexOfCasilla);

                        alerta1 = new Alert(Alert.AlertType.CONFIRMATION);
                        alerta1.setTitle("Confirmacion");
                        alerta1.setHeaderText("La propiedad " + comboBoxPropiedades.getValue().getNombre() +
                                " ha sido hipotecada");
                        alerta1.setContentText("Te devuelven $" + comboBoxPropiedades.getValue().getHipoteca() +
                                " de la hipoteca de la propiedad");
                        alerta1.showAndWait();

                        if(jugador.getBalance() >= 0) {

                            closeWindow();
                        }

                    } else if(result.isPresent() && result.get() == botonCancelar) {

                        alerta.close();
                    }

                } else {

                    comboBoxPropiedades.getValue().setHipotecado(true);
                    assert finalJugador != null;
                    finalJugador.cobrar(comboBoxPropiedades.getValue().getHipoteca());

                    alerta = new Alert(Alert.AlertType.CONFIRMATION);
                    alerta.setTitle("Confirmacion");
                    alerta.setHeaderText("La propiedad " + comboBoxPropiedades.getValue().getNombre() +
                            " ha sido hipotecada");
                    alerta.setContentText("Te devuelven $" + comboBoxPropiedades.getValue().getHipoteca() +
                            " de la hipoteca de la propiedad");
                    alerta.show();

                    if(jugador.getBalance() >= 0) {

                        closeWindow();
                    }
                }

            } else {

                assert finalJugador != null;
                if(finalJugador.getBalance() - comboBoxPropiedades.getValue().getHipoteca() > 0) {

                    finalJugador.pagar(comboBoxPropiedades.getValue().getHipoteca());
                    comboBoxPropiedades.getValue().setHipotecado(false);

                    alerta = new Alert(Alert.AlertType.CONFIRMATION);
                    alerta.setTitle("Confirmacion");
                    alerta.setHeaderText("La propiedad " + comboBoxPropiedades.getValue().getNombre() +
                            "ha sido deshipotecada");
                    alerta.setContentText(finalJugador.getNombre() + " paga $" +
                            comboBoxPropiedades.getValue().getHipoteca() + " por la hipoteca");
                    alerta.show();
                }

                if(jugador.getBalance() >= 0) {

                    closeWindow();
                }
            }
        }
    }

    /**
     * Cierra la ventana de hipotecar. Toma en cuenta si el jugador está en bancarrota para no dejarlo
     * salir de la ventana.
     */
    @FXML
    public void cancelarAction() {
        Jugador  jugador = Tablero.get_tablero().getJugadores().get(TableroController.getInstance().getTurno()%InicioController.getJugadores());
        if (jugador.getBalance() < 0){
            alerta.setAlertType(Alert.AlertType.WARNING);
            alerta.setTitle("Bancarrota");
            alerta.setHeaderText("Su balance aún está negativo!!");
            alerta.setContentText("No puede salir de esta pestaña mientras esté en bancarrota");
            alerta.showAndWait();
        }
        else {
            controlSeleccion.setVisible(false);
            TableroController.getInstance().enableButtons();
            closeWindow();
        }
    }

    /**
     * Kinda dumb, pero hace un casting de cualquier elemento para cerrar la ventana.
     */
    @FXML
    public void closeWindow() {
        ((Stage) controlSeleccion.getScene().getWindow()).close();
    }


}
