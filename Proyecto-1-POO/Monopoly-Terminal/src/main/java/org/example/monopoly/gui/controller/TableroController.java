package org.example.monopoly.gui.controller;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.effect.Bloom;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.example.monopoly.gui.viewers.ComprarCasasApplication;
import org.example.monopoly.gui.viewers.HipotecarApplication;
import org.example.monopoly.gui.viewers.NegociarApplication;
import org.example.monopoly.models.Casillas.Casilla;
import org.example.monopoly.models.Casillas.CasillaAdquirible;
import org.example.monopoly.models.Casillas.CasillasEspeciales.Carcel;
import org.example.monopoly.models.Casillas.CasillasEspeciales.ParadaLibre;
import org.example.monopoly.models.Casillas.CasillasEspeciales.WheelOfFortune;
import org.example.monopoly.models.Casillas.Propiedades.Propiedad;
import org.example.monopoly.models.Casillas.Propiedades.Servicios;
import org.example.monopoly.models.Casualidades.Builders.BuilderCasualidadMover;
import org.example.monopoly.models.Jugadores.Jugador;
import org.example.monopoly.models.Jugadores.Preso;
import org.example.monopoly.models.Tablero.Tablero;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class TableroController {

    @FXML
    private Button comprarCasas = new Button(), tirarDados = new Button(), negociar = new Button(), hipotecar = new Button();
    @FXML
    private Label propertyText = new Label();
    @FXML
    private Label taxes = new Label();
    @FXML
    private ListView<CasillaAdquirible> propiedades = new ListView<>();
    @FXML
    private Label p0 = new Label(), p1, p2 = new Label(), p3, p4, p5, p6, p7 = new Label(), p8 = new Label(), p9, p10 = new Label(), p11, p12, p13, p14, p15, p16 = new Label(), p17 = new Label(), p18, p19, p20 = new Label(), p21 = new Label(), p22 = new Label(), p23 = new Label(), p24 = new Label(), p25, p26, p27, p28, p29, p31, p32, p33 = new Label(), p34, p35, p36 = new Label(), p37, p38, p39;
    @FXML
    private Label h1, h2, h3, h4, h5, h6, h7, h8, h9, h10, h11, h12, h13, h14, h15, h16, h17, h18, h19, h20, h21, h22;
    private ArrayList<Label> posicion = new ArrayList<>();
    private static ArrayList<Label> casas;
    private Alert alerta;
    private static TableroController instance;
    private static int turno;
    @FXML
    private Label player = new Label();

    public TableroController(){
    }

    //fixme cambiar lógica a la obtención del jugador en otras ventanas
    public Label getPlayer() {
        return player;
    }

    @FXML
    private Label balance = new Label();
    @FXML
    AnchorPane Pane = new AnchorPane();

    public int getTurno() {
        return turno;
    }

    public static void setTurno(int turno) {
        TableroController.turno = turno;
    }

    public ArrayList<Label> getCasas() {
        return casas;
    }
    public ArrayList<Label> getPosicion(){
        return posicion;
    }

    /**
     * Método para aplicar singleton (no es realmente singleton pero igual evita errores)
     * @return {@code instance} La instancia de tablero
     */
    public static TableroController getInstance() {
        if (instance == null) {
            instance = new TableroController();
        }
        return instance;
    }

    /**
     * Método de inicialización del controlador entero
     */
    @FXML
    public void initialize() {

        String cargando = "";
        for (Jugador player : Tablero.get_tablero().getJugadores()) {
            if (player != Tablero.get_tablero().getJugadores().getLast()) {
                cargando = cargando.concat(player.getNombre() + " - ");
            } else {
                cargando = cargando.concat(player.getNombre());
            }
        }

        p0.setFont(Font.font("Copperplate Gothic Bold"));
        p0.setText(cargando);

        casas = new ArrayList<>();
        initializeButtons();
        initializePosition();
        initialiazeHouses();
        for (Label casa : casas) {
            casa.setText("");
        }
        for (int i = 1; i < 40; i++) {
            posicion.get(i).setText("");
        }

    }

    /**
     * Inicializa los botones para que tengan efectos visuales
     */
    private void initializeButtons() {
        tirarDados.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> tirarDados.setEffect(new Bloom()));
        negociar.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> negociar.setEffect(new Bloom()));
        hipotecar.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> hipotecar.setEffect(new Bloom()));
        comprarCasas.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> comprarCasas.setEffect(new Bloom()));
        tirarDados.addEventHandler(MouseEvent.MOUSE_EXITED, e -> tirarDados.setEffect(null));
        comprarCasas.addEventHandler(MouseEvent.MOUSE_EXITED, e -> comprarCasas.setEffect(null));
        negociar.addEventHandler(MouseEvent.MOUSE_EXITED, e -> negociar.setEffect(null));
        hipotecar.addEventHandler(MouseEvent.MOUSE_EXITED, e -> hipotecar.setEffect(null));
    }

    /**
     * Inicializa un arreglo de labels para las posiciones de los nombres en el tablero
     */
    public void initializePosition() {
        posicion.add(p0);
        posicion.add(p1);
        posicion.add(p2);
        posicion.add(p3);
        posicion.add(p4);
        posicion.add(p5);
        posicion.add(p6);
        posicion.add(p7);
        posicion.add(p8);
        posicion.add(p9);
        posicion.add(p10);
        posicion.add(p11);
        posicion.add(p12);
        posicion.add(p13);
        posicion.add(p14);
        posicion.add(p15);
        posicion.add(p16);
        posicion.add(p17);
        posicion.add(p18);
        posicion.add(p19);
        posicion.add(p20);
        posicion.add(p21);
        posicion.add(p22);
        posicion.add(p23);
        posicion.add(p24);
        posicion.add(p25);
        posicion.add(p26);
        posicion.add(p27);
        posicion.add(p28);
        posicion.add(p29);
        //El usuario nunca aparecera en la casilla de go Jail, por eso esta para no desconfigurar la lista
        posicion.add(new Label());
        posicion.add(p31);
        posicion.add(p32);
        posicion.add(p33);
        posicion.add(p34);
        posicion.add(p35);
        posicion.add(p36);
        posicion.add(p37);
        posicion.add(p38);
        posicion.add(p39);
    }

    /**
     * Inicializa el arreglo de labels para insertar las casas de las propiedades
     */
    public void initialiazeHouses() {
        casas.add(new Label());
        casas.add(h1);
        casas.add(new Label());
        casas.add(h2);
        casas.add(new Label());
        casas.add(new Label());
        casas.add(h3);
        casas.add(new Label());
        casas.add(h4);
        casas.add(h5);
        casas.add(new Label());
        casas.add(h6);
        casas.add(new Label());
        casas.add(h7);
        casas.add(h8);
        casas.add(new Label());
        casas.add(h9);
        casas.add(new Label());
        casas.add(h10);
        casas.add(h11);
        casas.add(new Label());
        casas.add(h12);
        casas.add(new Label());
        casas.add(h13);
        casas.add(h14);
        casas.add(new Label());
        casas.add(h15);
        casas.add(h16);
        casas.add(new Label());
        casas.add(h17);
        casas.add(new Label());
        casas.add(h18);
        casas.add(h19);
        casas.add(new Label());
        casas.add(h20);
        casas.add(new Label());
        casas.add(new Label());
        casas.add(h21);
        casas.add(new Label());
        casas.add(h22);
    }

    /**
     * Very important. Es el método encargado de lanzar los dados y que el jugador se mueva. Además, se encarga
     * de que el "bucle" exista.
     */
    @FXML
    public void throwDices() {

        disableButtons();

        Alert alert;
        Tablero tablero = Tablero.get_tablero();
        Jugador jugador = tablero.getJugadores().get(turno % InicioController.getJugadores());

        cleanLabel(jugador.getPosicion(), jugador.getNombre());

        if(jugador.getBalance() < 0) {
            if (jugador.bancarrota()) {

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Fin del juego");
                alert.setHeaderText("Gracias por jugar. Si está revisando esto, admiro su perseverancia <3 (denos 10 plis)");
                alert.setContentText(jugador.getNombre() + " ha perdido TODO. Ríanse de " + jugador.getNombre());
                alert.showAndWait();

                ((Stage) propertyText.getScene().getWindow()).close(); //this is so dumb :)

                return;
            }

            if (jugador.getBalance() < 0 && !propertyText.getScene().getWindow().isShowing()) {
                return;
            }

            return;
        }

        jugador.mover_casilla(jugador.tirarDados());

        if (jugador.isJuegaDeNuevo()) {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Dobles!!");
            alert.setHeaderText("Te han salido dados dobles!");
            alert.setContentText("Vuelves a jugar!");
            alert.showAndWait();
        }

        updateLabel(jugador.getPosicion(), jugador.getNombre());

        Casilla casilla = tablero.getCasillas().get(jugador.getPosicion());

        try {
            casilla.realizarAccion(jugador);

        } catch (InterruptedException | IOException e) {
            System.out.println("problem");
        }


        if (!jugador.isJuegaDeNuevo()) {

            jugador.setTurnosJugados(0);
            jugador.setJuegaDeNuevo(false);
            turno++;
            turno();

        } else if (jugador.getTurnosJugados() == 2) {

            jugador.setTurnosJugados(0);
            jugador.setJuegaDeNuevo(false);
            jugador.setPreso(true);
            jugador.setPosicion(10);
            ((Carcel) Tablero.get_tablero().getCasillas().get(10)).checkPresos(jugador);
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Preso!!??");
            alert.setHeaderText(null);
            alert.setContentText("Te han salido tres veces dobles, te vas a la carcel!!");
            alert.showAndWait();

            turno++;
            turno();

        } else {
            jugador.setJuegaDeNuevo(false);
            jugador.setTurnosJugados(jugador.getTurnosJugados() + 1);

            turno();
        }
    }

    /**
     * Método para abrir la ventana emergente negociar
     */
    @FXML
    public void negociarAction() {

        try {
            openStage(1);
        } catch (Exception e) {
            System.out.println("error");
        }
    }

    /**
     * Método para abrir la ventana emergente para comprar casas
     */
    @FXML
    public void comprarCasasAction() {

        try {
            openStage(2);
        } catch (Exception e) {
            System.out.println("error");
        }

    }

    /**
     * Método para abrir la ventana emergente para hipotecar
     */
    @FXML
    public void hipotecarAction() {

        try {
            openStage(3);
        } catch (Exception e) {
            System.out.println("error");
        }
    }

    /**
     * Método para desactiva los botones del tablero.
     */
    public void disableButtons() {
        tirarDados.setDisable(true);
        negociar.setDisable(true);
        hipotecar.setDisable(true);
        comprarCasas.setDisable(true);
        negociar.setDisable(true);
    }

    /**
     * Método que activa los botones de la ventana principal
     */
    public void enableButtons() {
        tirarDados.setDisable(false);
        negociar.setDisable(false);
        hipotecar.setDisable(false);
        comprarCasas.setDisable(false);
        negociar.setDisable(false);
    }

    /**
     * This is not a method. Is THE method.
     * Está encargada de configurar la ventana para cada turno
     */
    public void turno() {

        Jugador jugador = Tablero.get_tablero().getJugadores().get(turno % InicioController.getJugadores());

        enableButtons();

        if (!jugador.getPropiedades().isEmpty()) {
            propertyText.setVisible(true);
            propiedades.setVisible(true);
            propiedades.getItems().clear();
            ArrayList<CasillaAdquirible> propiedadesPlayer = jugador.getPropiedades();
            propiedades.getItems().addAll(propiedadesPlayer);
            ObservableList<CasillaAdquirible> itemsToRender = FXCollections.observableArrayList(propiedades.getItems());
            propiedades.setItems(itemsToRender);
            propiedades.setCellFactory(param -> new ListCell<>() {

                @Override
                protected void updateItem(CasillaAdquirible casilla, boolean empty) {
                    super.updateItem(casilla, empty);

                    if (casilla == null || empty) {
                        setText(null);
                    } else {
                        propertyText.setVisible(true);
                        if (casilla instanceof Propiedad) {
                            setText(casilla.getNombre() + "-[ " + ((Propiedad) casilla).getColorCasilla() + "]" + ((casilla.isHipotecado()) ? "-[HIPOTECADA]" : ""));
                        } else if (casilla instanceof Servicios) {
                            setText(casilla.getNombre() + "-[ SERVICIO ]" + ((casilla.isHipotecado()) ? "-[HIPOTECADA]" : ""));
                        } else {
                            setText(casilla.getNombre() + "-[ ESTACIÓN ]" + ((casilla.isHipotecado()) ? "-[HIPOTECADA]" : ""));
                        }

                    }
                }
            });
        } else {
            propiedades.setVisible(false);
            propertyText.setVisible(false);
        }

        Carcel carcel = new Carcel();

        carcel.checkPresos(jugador);

        player.setText(jugador.getNombre());
        balance.setText(String.valueOf(jugador.getBalance()));

        jugador.setJuegaDeNuevo(false);

        if (jugador.isPreso()) {

            alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Preso!");
            alerta.setHeaderText("No puedes ejecutar tu turno.");
            alerta.setContentText(jugador.getNombre() + ", te encuentras en la carcel.");
            alerta.showAndWait();

            alerta = new Alert(Alert.AlertType.CONFIRMATION);
            alerta.setTitle("Intento de fuga");
            alerta.setHeaderText("Tienes dos opciones para salir antes de cumplir tu condena.");
            alerta.setContentText("Tira dados o paga una fianza de $50");

            ButtonType tirarDados = new ButtonType("Tirar dados", ButtonBar.ButtonData.YES);
            ButtonType fianza = new ButtonType("Pagar fianza", ButtonBar.ButtonData.NO);

            alerta.getButtonTypes().setAll(tirarDados, fianza);

            Optional<ButtonType> result = alerta.showAndWait();

            if (result.isPresent() && result.get() == tirarDados) {

                jugador.tirarDados();

                if (jugador.isJuegaDeNuevo()) {
                    ((Carcel) Tablero.get_tablero().getCasillas().get(10)).getPresos().remove(
                            ((Carcel) Tablero.get_tablero().getCasillas().get(10)).searchPreso(jugador.getNombre()));
                    jugador.setPreso(false);

                    alerta = new Alert(Alert.AlertType.INFORMATION);
                    alerta.setTitle("Dados dobles!");
                    alerta.setHeaderText(jugador.getNombre() + " sale de la carcel");
                    alerta.setContentText("Corrupto!");
                    alerta.showAndWait();

                } else {

                    alerta = new Alert(Alert.AlertType.INFORMATION);
                    alerta.setTitle("No son dobles!");
                    alerta.setHeaderText(jugador.getNombre() + " continua en la carcel.");
                    alerta.setContentText("Tu nivel de corrupcion no alcanzo.");
                    alerta.showAndWait();

                    turno++;
                    turno();
                }

            } else if (result.isPresent() && result.get() == fianza) {

                if (jugador.getBalance() < 50) {

                    alerta = new Alert(Alert.AlertType.INFORMATION);
                    alerta.setTitle("Dinero insuficiente");
                    alerta.setHeaderText("No te alcanza para pagar la fianza brother.");
                    alerta.setContentText(jugador.getNombre() + " continua en la carcel.");
                    alerta.showAndWait();

                    turno++;
                    turno();

                } else {

                    jugador.pagar(50);
                    jugador.setPreso(false);
                    jugador.setJuegaDeNuevo(true);

                    for (Casilla casilla : Tablero.get_tablero().getCasillas()) {

                        if (casilla instanceof Carcel) {
                            ((Carcel) casilla).getPresos().remove(((Carcel) casilla).searchPreso(jugador.getNombre()));
                            break;
                        }
                    }

                    alerta = new Alert(Alert.AlertType.INFORMATION);
                    alerta.setTitle("Fianza pagada");
                    alerta.setHeaderText(jugador.getNombre() + " ha pagado la fianza de $50.");
                    alerta.setContentText("Sale de la carcel.");
                    alerta.showAndWait();
                }
            }
        }

        for (Jugador player : Tablero.get_tablero().getJugadores()){
            cleanLabel(player.getPosicion(), player.getNombre());
        }

        for (Jugador player : Tablero.get_tablero().getJugadores()){
            updateLabel(player.getPosicion(), player.getNombre());
        }

        boolean flag = false;
        for (Label location : posicion){
            if (!location.getText().isEmpty()){
                for (Jugador player : Tablero.get_tablero().getJugadores()){
                    if (player.getPosicion() == posicion.indexOf(location)){
                        flag = true;
                    }
                }
                if (!flag){
                    location.setText("");
                }
                flag = false;
            }
        }

        ParadaLibre paradaLibre = (ParadaLibre) Tablero.get_tablero().getCasillas().get(20);
        taxes.setText(String.valueOf(paradaLibre.getAcumulado()));

        if (jugador.isJuegaDeNuevo()) {
            jugador.setJuegaDeNuevo(false);
        }
    }

    /**
     * Método encargado de abrir ventanas emergentes dependiendo de un parámetro
     * @param _app Es un entero del 1 al 3, por medio del cual se describe la ventana que se va a abrir
     *             1 = Negociar
     *             2 = Comprar casas
     *             3 = Hipotecar
     * @throws Exception La excepción es si no se logra abrir la aplicación
     */
    private void openStage(int _app) throws Exception {

        disableButtons();

        Stage stage = new Stage();

        Application app = null;

        if (_app == 1) {
            app = new NegociarApplication();
        } else if (_app == 2) {
            app = new ComprarCasasApplication();
        } else if (_app == 3) {
            app = new HipotecarApplication();
        }

        try {
            assert app != null;
            app.start(stage);
        } catch (IOException e) {
            System.out.println("error");
        }
    }

    /**
     * Lo único que hace es setear como un String vacío cuando un jugador se mueve
     * @param position Es la posición actual del jugador
     * @param Name Es el nombre del jugador (que aparecerá en el label)
     */
    @FXML
    public void cleanLabel(int position, String Name) {
        if (posicion.isEmpty()){
            initializePosition();
        }
        String temp = this.posicion.get(position).getText();

        String[] nombres = temp.split(" - ");

        ArrayList<String> jugadores = new ArrayList<>(Arrays.asList(nombres));
        ArrayList<String> toRemove = new ArrayList<>();

        for (String name : jugadores) {
            if (name.equals(Name)) {
                toRemove.add(name);
                break;
            }
        }
        jugadores.removeAll(toRemove);

        if (jugadores.isEmpty()) {

            posicion.get(position).setText("");

        } else {

            String actualizar = "";

            if (jugadores.size() == 1) {

                actualizar = jugadores.getFirst();

            } else {

                for (String name : jugadores) {

                    if (!name.equals(jugadores.getLast())) {

                        actualizar = actualizar.concat(name + " - ");

                    } else {

                        actualizar = actualizar.concat(name);
                    }
                }
            }

            posicion.get(position).setText(actualizar);
        }
    }

    /**
     * Actualiza el label de la posición en donde el jugador se encuentra después de moverse
     * @param position Es la posición resultante del jugador
     * @param Name Es el nombre del jugador (que aparecerá en el label)
     */
    @FXML
    public void updateLabel(int position, String Name) {
        if (posicion.isEmpty()){
            initializePosition();
        }
        posicion.get(position).setFont(Font.font("Copperplate Gothic Bold"));

        if (posicion.get(position).getText().isEmpty()) {

            posicion.get(position).setText(Name);

        } else {

            posicion.get(position).setText(
                    posicion.get(position).getText() + " - " + Name
            );
        }
    }

    /**
     * Getter de taxes
     * @return taxes, es un {@code Label}
     */
    public Label getTaxes() {
        return taxes;
    }

    /**
     * Es para actualizar los labels de casa una vez se compran las casas en las propiedades
     * @param indexOfCasilla es la posición de la casilla donde se compró casas
     * @param numCasas es la cantidad de casas que se tiene
     */
    public static void buyHouses(int indexOfCasilla, int numCasas){
        casas.get(indexOfCasilla).setFont(
                Font.font("Copperplate Gothic Bold"));

        casas.get(indexOfCasilla).setStyle("-fx-text-fill: red;");
        casas.get(indexOfCasilla).setText(
                numCasas + " casas");
    }

    /**
     * Es para actualizar el label de casas cuando se venden las casas (es una opción de hipotecar)
     * @param indexOfHouses es la posición de la casilla en el arreglo
     */
    public static void sendHouses(int indexOfHouses) {
        casas.get(indexOfHouses).setText("");
    }
}