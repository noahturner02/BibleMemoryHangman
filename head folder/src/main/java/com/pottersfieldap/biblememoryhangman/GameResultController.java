package com.pottersfieldap.biblememoryhangman;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class GameResultController {
    private boolean win;
    @FXML
    Label gameStateText;
    @FXML
    Label exhortationText;
    @FXML
    Button replayButton;
    @FXML
    Button quitButton;

    @FXML
    public void initialize() {
        SceneRelay sceneRelay = SceneRelay.getInstance();
        win = sceneRelay.getWin();

        if (win) {
            gameStateText.setText("Winner!");
            exhortationText.setText("Great job! By remembering God's word, you are hiding it in your heart. Keep it up!");
        } else {
            gameStateText.setText("You Lose");
            exhortationText.setText("Don't be discouraged. Memorizing takes practice. Try again?");
        }
        quitButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.exit(0);
            }
        });
        replayButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // fill this in once I figure out live scene switching
            }
        });
    }
}
