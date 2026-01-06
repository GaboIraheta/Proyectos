module org.parcialfinal_poo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires static lombok;

    exports org.parcialfinal_poo.gui.viewers;
    opens org.parcialfinal_poo.gui.viewers to javafx.fxml;
    exports org.parcialfinal_poo.gui.controllers;
    opens org.parcialfinal_poo.gui.controllers to javafx.fxml;
}