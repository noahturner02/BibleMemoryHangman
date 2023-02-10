package com.pottersfieldap.biblememoryhangman;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class GameSetupController {
    @FXML
    Button playButton;
    @FXML
    TextArea verseTextField;
    @FXML
    ChoiceBox bookChoiceBox;
    @FXML
    TextField chapterField;
    @FXML
    TextField startVerseField;
    @FXML
    TextField endVerseField;

    private void playButtonClicked() {
        if (verseTextField.getText().isEmpty() || chapterField.getText().isEmpty() || startVerseField.getText().isEmpty() || !bookChoiceBox.isShowing()) {
            System.out.println("Missing info. Please enter the text and the reference for your verse");
        }
        else {
            System.out.println("Let the games begin!");
        }
    }

    @FXML
    public void initialize() {
        playButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                playButtonClicked();
            }
        });
    }

}
