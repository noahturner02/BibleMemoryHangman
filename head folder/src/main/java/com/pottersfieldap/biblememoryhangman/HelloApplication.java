package com.pottersfieldap.biblememoryhangman;

import com.gargoylesoftware.htmlunit.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader= new FXMLLoader(HelloApplication.class.getResource("game-setup-wizard.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 300);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
        WebScraper.getRawVerseText("Romans", 3, 1, 4, "ESV");
    }

    public static void main(String[] args) {
        launch();
    }
}