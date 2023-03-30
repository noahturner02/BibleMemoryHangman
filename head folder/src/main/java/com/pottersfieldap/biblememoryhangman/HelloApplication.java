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
        // Call sceneRelay to add setup_stage to the stagemap for future reference
        SceneRelay sceneRelay = SceneRelay.getInstance();
        sceneRelay.addToStageMap("setup_stage", stage);

        // Load the setup wizard to start the program
        FXMLLoader fxmlLoader= new FXMLLoader(HelloApplication.class.getResource("game-setup-wizard.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 300);
        stage.setTitle("Bible Memory Hangman");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}