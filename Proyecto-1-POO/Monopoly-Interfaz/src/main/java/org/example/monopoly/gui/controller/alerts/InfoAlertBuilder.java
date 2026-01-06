package org.example.monopoly.gui.controller.alerts;

import javafx.scene.control.Alert;

public class InfoAlertBuilder extends AlertBuilder {
    public InfoAlertBuilder() {
        reset();
    }

    @Override
    public void reset() {
        alert = new Alert(Alert.AlertType.INFORMATION);
    }
}
