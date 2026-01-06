package org.example.monopoly.gui.controller.alerts;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;

/**
 * This is an unnecessary class that I built because I didn't want to do this for every class,
 * and it was easier to do this and create an object for every square. Nonetheless... a dumb construct
 * that I would bet is not even an Adapter (I still don't comprehend what an Adapter is).
 */
public abstract class AlertBuilder {
    @FXML
    protected Alert alert;

    public abstract void reset();

    public void buildTitle(String title) {
        alert.setTitle(title);
    }

    public void buildHeader(String header){
        alert.setHeaderText(header);
    }

    public void buildContent(String content){
        alert.setContentText(content);
    }

    public Alert getAlert(){
        return alert;
    }
}
