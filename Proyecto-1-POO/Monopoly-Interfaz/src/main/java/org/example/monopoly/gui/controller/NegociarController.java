package org.example.monopoly.gui.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.example.monopoly.gui.controller.InterfacesControllers.commonMethods;
import org.example.monopoly.models.Casillas.CasillaAdquirible;
import org.example.monopoly.models.Casillas.Propiedades.Propiedad;
import org.example.monopoly.models.Jugadores.Jugador;
import org.example.monopoly.models.Tablero.Tablero;

import java.util.ArrayList;
import java.util.Objects;

public class NegociarController implements commonMethods {

    @FXML
    private ChoiceBox<Jugador> choiceBoxPlayers;
    @FXML
    private ComboBox<CasillaAdquirible> comboBoxPropiedades;
    @FXML
    private TextField textFieldOferta;
    @FXML
    private Label labelJugadorOfertado, labelPropiedad, labelOferta, labelCampo1, labelCampo2, labelCampo3;
    private Alert alerta = new Alert(Alert.AlertType.NONE);
    private final TableroController tableroController = TableroController.getInstance();

    /**
     * It does, indeed, initialize
     */
    @FXML
    public void initialize() {
        Tablero tablero = Tablero.get_tablero();

        labelCampo1.setVisible(false);
        labelCampo2.setVisible(false);
        labelCampo3.setVisible(false);

        comboBoxPropiedades.setDisable(true);

        ArrayList<Jugador> jugadores = Tablero.get_tablero().getJugadores();
        for(Jugador player : jugadores) {
            if(!Objects.equals(player, tablero.getJugadores().get(tableroController.getTurno()%InicioController.getJugadores()))) {
                choiceBoxPlayers.getItems().add(player);
            }
        }

        ObservableList<Jugador> itemsPLayers = FXCollections.observableArrayList(choiceBoxPlayers.getItems());
        choiceBoxPlayers.setItems(itemsPLayers);

        comboBoxPropiedades.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(comboBoxPropiedades.isDisabled()) {
                comboBoxPropiedades.setDisable(false);
                labelCampo1.setVisible(false);
            } else {
                comboBoxPropiedades.setVisible(true);
            }
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
                                    (item instanceof Propiedad ? " - " + ((Propiedad) item).getColorCasilla() : ""));
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });

        choiceBoxPlayers.valueProperty().addListener((observableValue, jugador, t1) -> {
            if(t1 != null) {
                comboBoxPropiedades.setDisable(false);
                labelCampo1.setVisible(false);
                labelJugadorOfertado.setText(choiceBoxPlayers.getValue().getNombre());

                Jugador jugador1 = null;
                for (Jugador player : Tablero.get_tablero().getJugadores()){
                    if(Objects.equals(player, choiceBoxPlayers.getValue())) {
                        jugador1 = player;
                    }
                }

                assert jugador1 != null;
                ArrayList<CasillaAdquirible> propiedades = jugador1.getPropiedades();
                comboBoxPropiedades.getItems().clear();

                for(CasillaAdquirible propiedad : propiedades) {
                    if (!comboBoxPropiedades.getItems().contains(propiedad)){
                        comboBoxPropiedades.getItems().add(propiedad);
                    }
                }

                ObservableList<CasillaAdquirible> itemsPropiedades = FXCollections.observableArrayList(comboBoxPropiedades.getItems());
                comboBoxPropiedades.setItems(itemsPropiedades);

            } else {
                comboBoxPropiedades.setDisable(true);
            }
        });

        comboBoxPropiedades.valueProperty().addListener(((observableValue, casillaAdquirible, t1) -> {
            if (t1 != null){
                labelPropiedad.setFont(Font.font("Copperplate Gothic Bold"));
                labelPropiedad.setText(comboBoxPropiedades.getSelectionModel().getSelectedItem().getNombre());
            }
        }));

        comboBoxPropiedades.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            if(comboBoxPropiedades.isDisabled()) {
                labelCampo1.setVisible(true);
            } else {
                mouseEvent.consume();
            }
        });

        textFieldOferta.textProperty().addListener((observableValue, s, t1) -> {
            if(!t1.isEmpty()) {
                labelOferta.setFont(Font.font("Copperplate Gothic Bold"));
                labelOferta.setText("$" + textFieldOferta.getText());
            }
        });
    }

    /**
     * Se encarga de la lógica para aceptar una oferta y toma en cuenta que los campos requeridos
     * no estén vacíos
     */
    @FXML
    public void aceptarAction() {
        cleanLabels();
        Tablero tablero = Tablero.get_tablero();

        boolean flag = false;

        if(choiceBoxPlayers.getValue() == null) {
            labelCampo1.setVisible(true);
            flag = true;
        }

        if(comboBoxPropiedades.getValue() == null) {
            labelCampo2.setVisible(true);
            flag = true;
        }

        if(textFieldOferta.getText().isEmpty()) {
            labelCampo3.setVisible(true);
            flag = true;
        }

        if(!flag) {
            try {
                try{
                    Jugador jugador = null;
                    for(Jugador player : tablero.getJugadores()){
                        if(Objects.equals(player, choiceBoxPlayers.getValue())) {
                            jugador = player;
                        }
                    }

                    CasillaAdquirible propiedadElegida = null;
                    assert jugador != null;
                    for(CasillaAdquirible casilla : jugador.getPropiedades()) {
                        if(Objects.equals(casilla, comboBoxPropiedades.getSelectionModel().getSelectedItem())) {
                            propiedadElegida = casilla;
                        }
                    }

                    jugador.cobrar(Integer.parseInt(textFieldOferta.getText()));
                    jugador.getPropiedades().remove(propiedadElegida);


                for(Jugador player : Tablero.get_tablero().getJugadores()) {
                    if(player.getNombre().equals(tablero.getJugadores().get(tableroController.getTurno()%InicioController.getJugadores()).getNombre())) {

                            player.pagar(Integer.parseInt(textFieldOferta.getText()));
                            assert propiedadElegida != null;
                            player.adquirir_propiedad(propiedadElegida);

                            break;
                        }
                    }

                    alerta = new Alert(Alert.AlertType.INFORMATION);
                    alerta.setTitle("Negociación exitosa!");
                    alerta.setHeaderText(jugador.getNombre() + " ha aceptado la oferta!");
                    assert propiedadElegida != null;
                    alerta.setContentText("Propiedad negociada: " + propiedadElegida.getNombre());
                    alerta.showAndWait();

                    tableroController.enableButtons();
                    closeWindow();

                } catch(Exception e) {
                    alerta = new Alert(Alert.AlertType.ERROR);
                    alerta.setTitle("Error");
                    alerta.setHeaderText("Ha ocurrido un error.");
                    alerta.setContentText("Error en la seleccion de propiedad.");
                    alerta.showAndWait();
                    throw new NullPointerException("No se puede seleccionar un propiedad");
                }

            } catch (NullPointerException e) {
                alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("Error");
                alerta.setHeaderText("Ha ocurrido un error.");
                alerta.setContentText("Error en alguna seleccion de jugador/propiedad.");
                alerta.show();
                throw new NullPointerException("Ha ocurrido un error al aceptar la oferta");
            }
        }
    }

    /**
     * Se encarga de la lógica necesaria para rechazar la oferta
     */
    @FXML
    public void rechazarAction() {
        cleanLabels();

        boolean flag = false;

        if(choiceBoxPlayers.getSelectionModel().getSelectedItem() == null) {
            labelCampo1.setVisible(true);
            flag = true;
        }

        if(comboBoxPropiedades.getSelectionModel().getSelectedItem() == null) {
            labelCampo2.setVisible(true);
            flag = true;
        }

        if(textFieldOferta.getText().isEmpty()) {
            labelCampo3.setVisible(true);
            flag = true;
        }

        if(!flag) {
            alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Informacion");
            alerta.setHeaderText("Oferta rechazada");
            alerta.setContentText(labelJugadorOfertado.getText() + " no ha aceptado la oferta");
            alerta.showAndWait();

            tableroController.enableButtons();
            closeWindow();
        }
    }

    /**
     * Cuando el botón cancelar se presiona... (Refer to the closeWindow() javadoc for more)
     */
    @FXML
    public void cancelarAction() {
        cleanLabels();
        tableroController.enableButtons();
        closeWindow();
    }

    /**
     * (Continuation of cancelarAction() javadoc) Cierra la ventana
     */
    @Override
    public void closeWindow() {
        ((Stage) labelCampo1.getScene().getWindow()).close();
    }

    /**
     * Establece la visibilidad de los labels que controlan la selección de los campos requeridos
     */
    private void cleanLabels() {
        labelCampo1.setVisible(false);
        labelCampo2.setVisible(false);
        labelCampo3.setVisible(false);
    }

    /**
     * I thought me and God knew what this method does...
     * Now only God knows
     * Creo que inicializa el combobox ._.XD
     */
    public void preventiveMethod(){
        Jugador jugador = null;
        for (Jugador player : Tablero.get_tablero().getJugadores()){
            if (player.getNombre().equals(String.valueOf(choiceBoxPlayers.getValue()))){
                jugador = player;
            }
        }

        labelJugadorOfertado.setVisible(true);
        labelJugadorOfertado.setText(choiceBoxPlayers.getValue().getNombre());
        labelPropiedad.setVisible(true);
        labelPropiedad.setText(comboBoxPropiedades.getSelectionModel().getSelectedItem().getNombre());

        ObservableList<CasillaAdquirible> itemsPropiedades = FXCollections.observableArrayList(comboBoxPropiedades.getItems());
        comboBoxPropiedades.setItems(itemsPropiedades);

        CasillaAdquirible propiedadElegida = null;
        assert jugador != null;
        for( CasillaAdquirible propiedad : jugador.getPropiedades()) {
            System.out.println(propiedad.getNombre());
            System.out.println(comboBoxPropiedades.getSelectionModel().getSelectedItem().getNombre());
            if(propiedad.getNombre().equals(comboBoxPropiedades.getSelectionModel().getSelectedItem().getNombre())) {
                propiedadElegida = propiedad;
            }
        }

        assert propiedadElegida != null;
        labelPropiedad.setText(propiedadElegida.getNombre());
    }
}
