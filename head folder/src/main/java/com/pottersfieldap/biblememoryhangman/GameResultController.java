package com.pottersfieldap.biblememoryhangman;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class GameResultController {
    // Initializing
    private boolean win;
    SceneRelay sceneRelay = SceneRelay.getInstance();
    @FXML
    Label gameStateText;
    @FXML
    Label exhortationText;
    @FXML
    Button replayButton;
    @FXML
    Button quitButton;

    @FXML
    // FXML initialize method. Since this scene is always made new, this will always run when it is shown.
    public void initialize() {
        win = sceneRelay.getWin(); // get win status from hangman game
        if (win) { // If the game is won, set text appropriate for winning
            gameStateText.setText("Winner!");
            exhortationText.setText("Great job! By remembering God's word, you are hiding it in your heart. Keep it up!");
        } else { // If the game is lost, set text appropriate for losing
            gameStateText.setText("You Lose");
            exhortationText.setText("Don't be discouraged. Memorizing takes practice. Try again?");
        }
        quitButton.setOnMouseClicked(new EventHandler<MouseEvent>() { // When the quit button is pressed, exit the game
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.exit(0);
            }
        });
        replayButton.setOnMouseClicked(new EventHandler<MouseEvent>() { // When the replay button is pressed, restart.
            @Override
            public void handle(MouseEvent mouseEvent) {
                sceneRelay.addToStageMap("game-result-window.fxml", (Stage) replayButton.getScene().getWindow()); // Add this stage to the map
                sceneRelay.getFromStageMap("hangman-scene.fxml").hide(); // Hide the hangman scene
                sceneRelay.switchScene("game-result-window.fxml", "game-setup-wizard.fxml", 0, 0, false); // Switch from this stage to the setup.
            }
        });
    }
}
