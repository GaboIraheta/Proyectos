package org.example.monopoly.gui.controller.alerts;

import javafx.scene.control.Alert;

public class WarningAlertBuilder extends AlertBuilder{
    public WarningAlertBuilder() {
        reset();
    }

    @Override
    public void reset() {
        alert = new Alert(Alert.AlertType.WARNING);
    }
}
