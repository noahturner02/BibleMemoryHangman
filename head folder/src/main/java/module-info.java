module com.example.biblememoryhangman {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens com.pottersfieldap.biblememoryhangman to javafx.fxml;
    exports com.pottersfieldap.biblememoryhangman;
}