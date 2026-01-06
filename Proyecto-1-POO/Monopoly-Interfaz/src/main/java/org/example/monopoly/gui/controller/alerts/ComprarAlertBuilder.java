package org.example.monopoly.gui.controller.alerts;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.util.NoSuchElementException;
import java.util.Optional;

public class ComprarAlertBuilder extends AlertBuilder{
    ButtonType buttonTypeComprar;
    ButtonType buttonTypeCancelar;

    public ComprarAlertBuilder(){
        reset();
    }

    @Override
    public void reset() {
        alert = new Alert(Alert.AlertType.CONFIRMATION);

        buttonTypeComprar = new ButtonType("Comprar");
        buttonTypeCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeComprar, buttonTypeCancelar);
    }

    /**
     * Esta función retorna verdadero si la selección no fue cancelar. Es decir, compró.
     * El getSelection ya tiene incluido el método {@code showAndWait()}, puesto que es requerido
     * para evaluar la elección.
     * @return {@code boolean} Es {@code true} si no canceló o cerró la ventana.
     */
    public boolean getSelection(){
        Optional<ButtonType> result = alert.showAndWait();
        try{
            return result.get() == buttonTypeComprar;
        }

        //En caso de que cierre la ventana sin elegir (the user ruins everything)
        catch (NoSuchElementException e) {
            return false;
        }
    }
}
