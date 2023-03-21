package com.pottersfieldap.biblememoryhangman;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class GameResultController {
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
        gameStateText.setText("Winner!");
        exhortationText.setText("Great job! By remembering God's word, you are hiding it in your heart. Keep it up!");
    }
}
