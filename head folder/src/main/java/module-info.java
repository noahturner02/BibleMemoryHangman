module com.example.biblememoryhangman {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires htmlunit;

    opens com.pottersfieldap.biblememoryhangman to javafx.fxml;
    exports com.pottersfieldap.biblememoryhangman;
}