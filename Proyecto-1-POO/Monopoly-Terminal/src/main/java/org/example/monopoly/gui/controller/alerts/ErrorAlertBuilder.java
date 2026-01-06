package org.example.monopoly.gui.controller.alerts;

import javafx.scene.control.Alert;

public class ErrorAlertBuilder extends AlertBuilder{
    public ErrorAlertBuilder() {
        reset();
    }

    @Override
    public void reset() {
        alert = new Alert(Alert.AlertType.ERROR);
    }
}
