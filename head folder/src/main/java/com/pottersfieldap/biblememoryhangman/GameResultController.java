package com.pottersfieldap.biblememoryhangman;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

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
    }
}
