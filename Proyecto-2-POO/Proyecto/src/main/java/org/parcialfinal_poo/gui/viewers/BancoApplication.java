package org.parcialfinal_poo.gui.viewers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class BancoApplication extends Application {
    //Fixme RECORDAR CAMBIAR CREDENCIALES DE MYSQL PARA PODER PROBARLO, ESTAN EN LA CLASE DataBase
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BancoApplication.class.getResource("banco-view.fxml")); //00042823 Referencia al fxml de la aplicación
        Scene scene = new Scene(fxmlLoader.load(),960,540); //00042823 Genera una escena con la configuración del fxml, ancho 960px largo 540px
        stage.setTitle("Sistema de registros"); //00042823 Establece el nombre de la ventana
        stage.setScene(scene); //00042823 Establece la configuración de la escena en una ventana
        stage.show(); //00042823 Muestra la ventana seleccionada
    }

    public static void main(String[] args) {
        launch(); //00042823 Es la función que hace que el programa funcione (no comprendo cómo funciona)
    }
}