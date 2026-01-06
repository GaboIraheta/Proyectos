module org.example.monopoly {
    requires javafx.controls;
    requires javafx.fxml;

    exports org.example.monopoly.models;
    opens org.example.monopoly.models;
    exports org.example.monopoly.models.Jugadores;
    opens org.example.monopoly.models.Jugadores;
    exports org.example.monopoly.gui.controller;
    opens org.example.monopoly.gui.controller to javafx.fxml;
    exports org.example.monopoly.gui.viewers;
    opens org.example.monopoly.gui.viewers to javafx.fxml;
    exports org.example.monopoly.gui.controller.alerts;
    opens org.example.monopoly.gui.controller.alerts to javafx.fxml;
}