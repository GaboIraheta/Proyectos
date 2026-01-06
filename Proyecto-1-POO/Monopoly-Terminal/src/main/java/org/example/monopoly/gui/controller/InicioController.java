package org.example.monopoly.gui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.monopoly.gui.viewers.TableroAplication;
import org.example.monopoly.models.Jugadores.Jugador;
import org.example.monopoly.models.Tablero.Tablero;

import java.util.ArrayList;

public class InicioController {
    @FXML
    private Label p1, p2, p3, p4, p5, p6;
    @FXML
    private TextField txtF1, txtF2, txtF3, txtF4, txtF5, txtF6;
    @FXML
    private Button startButton;
    private ArrayList<Label> players;
    private ArrayList<TextField> names;
    private static int jugadores;
    private static InicioController instance;

    /**
     * Singleton (intento de porque no es private)
     * Su única función es para que los jugadores puedan acceder al tablero
     * @return instance {@code InicioController}
     */
    public static InicioController getInstance(){
        if (instance == null){
            instance = new InicioController();
            jugadores = 0;
        }

        return instance;
    }

    /**
     * Inicializa todos los componentes del menú de inicio
     */
    @FXML
    public void initialize(){
        names = new ArrayList<>();
        players = new ArrayList<>();
        initializeLabels();
        initializeTextFields();
        disableLabelsTxtFields();
        startButton.setVisible(false);



    }

    /**
     * Inicializa los labels de los jugadores
     */
    private void initializeLabels(){
        players.add(p1);
        players.add(p2);
        players.add(p3);
        players.add(p4);
        players.add(p5);
        players.add(p6);
    }

    /**
     * Inicializa los text fields en los que se ingresarán los nombres de los jugadores
     */
    private void initializeTextFields(){
        names.add(txtF1);
        names.add(txtF2);
        names.add(txtF3);
        names.add(txtF4);
        names.add(txtF5);
        names.add(txtF6);
    }

    //-------------------------------------------------------------------------------
    //Acciones de botón de jugador, cambia la cantidad de jugadores
    public void P2ButtonPressed(){
        disableLabelsTxtFields();
        jugadores = 2;
        enableLabelsTxtFields();
    }

    public void P3ButtonPressed(){
        disableLabelsTxtFields();
        jugadores = 3;
        enableLabelsTxtFields();
    }

    public void P4ButtonPressed(){
        disableLabelsTxtFields();
        jugadores = 4;
        enableLabelsTxtFields();
    }

    public void P5ButtonPressed(){
        disableLabelsTxtFields();
        jugadores = 5;
        enableLabelsTxtFields();
    }

    public void P6ButtonPressed(){
        disableLabelsTxtFields();
        jugadores = 6;
        enableLabelsTxtFields();
    }
    //-------------------------------------------------------------------------------

    /**
     * Muestra el botón de inicio una vez los text fields estén completos
     */
    public void showStart(){
        boolean flag = true;
        for (int i = 0; i < jugadores; i++){
            if (names.get(i).getText().isEmpty()){
                flag = false;
            }
        }

        startButton.setVisible(flag);
    }

    /**
     * Inhabilita text fields
     */
    public void disableLabelsTxtFields(){
        for (int i = 0; i < players.size(); i++) {
            players.get(i).setVisible(false);
            names.get(i).setVisible(false);
        }
    }

    /**
     * Habilita text fields
     */
    public void enableLabelsTxtFields(){
        for (int i = 0; i < jugadores; i++) {
            players.get(i).setVisible(true);
            names.get(i).setVisible(true);
        }
    }

    /**
     * Getter de jugadores
     * @return jugadores, es un {@code int}
     */
    public static int getJugadores() {
        return jugadores;
    }

    /**
     * Mete todos los jugadores en el tablero y da inicio al juego
     */
    public void startGame(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Tablero tablero = Tablero.get_tablero();
        for (int i = 0; i < jugadores; i++){
            tablero.agregarJugador(new Jugador(names.get(i).getText()));
        }
        alert.setTitle("New Game");
        alert.setHeaderText("Que empiece el juego!!!");
        alert.setContentText(null);
        alert.showAndWait();
        ((Stage) p1.getScene().getWindow()).close();
        TableroAplication TabApp = new TableroAplication();
        Stage stage = new Stage();
        try {
            TabApp.start(stage);
            TabApp.init();
        }catch (Exception ignore){
            System.out.println("error al abrir la pestaña");
        }

    }
}
