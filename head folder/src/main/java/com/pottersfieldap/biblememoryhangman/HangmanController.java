package com.pottersfieldap.biblememoryhangman;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class HangmanController {
    @FXML
    Pane manPane;
    @FXML
    TilePane verseTextPane;
    @FXML
    TextField guessField;
    @FXML
    Button guessButton;
    @FXML
    Circle head;
    @FXML
    Line spine;
    @FXML
    Line rightLeg;
    @FXML
    Line leftLeg;
    @FXML
    Line rightArm;
    @FXML
    Line leftArm;

    String scripture = "";
    List<Word> currentWordList = new ArrayList<>();
    SceneRelay sceneRelay = SceneRelay.getInstance();

    public List<Word> getCurrentWordList() {
        return currentWordList;
    }
    public void setCurrentWordList(List<Word> wordList) {
        this.currentWordList = wordList;
    }
    private boolean checkForWin() {
        boolean win = true;
        for (int i = 0; i < verseTextPane.getChildren().size(); i++) {
            Pane p = (Pane) verseTextPane.getChildren().get(i);
            if (!p.getChildren().get(0).isVisible()) {
                win = false;
                break;
            }
        }
        return win;
    }
    private void placeVerseText(String verseText) { // Places the invisible labels and shows the blanks of corresponding size
        List<Word> wordList = VerseProcessing.textToWords(verseText);
        setCurrentWordList(wordList);
        int xLayoutTracker = 0; // Tracks the blank lines that are drawn in the verse text pane. follows from left to right
        int yLayoutTracker = 0; // Tracks the blank lines on the y axis
        int lineSize = 0;
        for (Word w : wordList) { // Place the invisible label and line for each word
            Pane p = new Pane(); // wrapper pane for the labels
            Label l = new Label(w.getText());
            l.setVisible(false);
            p.getChildren().add(l);
            l.layoutXProperty().bind(p.widthProperty().subtract(l.widthProperty()).divide(2)); // bind the label to the center of the pane
            verseTextPane.getChildren().add(p);
            drawLines(p);
        }
    }

    private String getGuess() {
        return guessField.getText();
    }

    private void drawLines(Pane p) {
        Line l = new Line();
        Label word = (Label) p.getChildren().get(0);
        word.layoutXProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                l.setStartX((p.getWidth() - word.getWidth()) / 2);
            }
        });
        word.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                l.setLayoutY(word.getHeight());
            }
        });
        word.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                l.setEndX((p.getWidth() - (p.getWidth() - word.getWidth()) / 2));
            }
        });
        p.getChildren().add(l);
    }

    private void revealPiece() {
        // Reveals the next piece of the hangman
        if (!head.isVisible()) {
            head.setVisible(true);
        }
        else if (!spine.isVisible()) {
            spine.setVisible(true);
        }
        else if (!rightArm.isVisible()) {
            rightArm.setVisible(true);
        }
        else if (!leftArm.isVisible()) {
            leftArm.setVisible(true);
        }
        else if (!rightLeg.isVisible()) {
            rightLeg.setVisible(true);
        }
        else if (!leftLeg.isVisible()) {
            leftLeg.setVisible(true);
            endGame(false);
        }
    }

    private void matchAndReveal(String guess) {
        boolean hit = false;
        for (int i = 0; i < getCurrentWordList().size(); i++) {
            if (getCurrentWordList().get(i).getFilteredText().equalsIgnoreCase(guess)) {
                revealWordByIndex(i);
                hit = true;
            }
        }
        if (checkForWin()) {
            endGame(true);
        }
        if (!hit) {
            revealPiece();
        }
    }

    private void revealWordByIndex(int i) {
        Pane p = (Pane) verseTextPane.getChildren().get(i);
        p.getChildren().get(0).setVisible(true);
    }

    private void hideBody() {
        head.setVisible(false);
        spine.setVisible(false);
        leftArm.setVisible(false);
        rightArm.setVisible(false);
        leftLeg.setVisible(false);
        rightLeg.setVisible(false);
    }

    private void disableControls() {
        // When the game has ended, disable the guessField and guess Button.
        guessField.setDisable(true);
        guessButton.setDisable(true);
    }

    private void enableControls() {
        // When the scene is being loaded and reset, re-enable the controls
        guessField.setDisable(false);
        guessButton.setDisable(false);
    }

    private void endGame(Boolean win) {
        sceneRelay.setWin(win);
        sceneRelay.addToStageMap("hangman-scene.fxml", (Stage) verseTextPane.getScene().getWindow());
        sceneRelay.switchScene("hangman-scene.fxml", "game-result-window.fxml", 300, 600, true);
        disableControls();
        if (win) {
            sceneRelay.getFromStageMap("game-result-window.fxml").setTitle("You Win!");
        } else {
            sceneRelay.getFromStageMap("game-result-window.fxml").setTitle("You Lose!");
        }
    }

    @FXML
    public void initialize() {
        SceneRelay sceneRelay = SceneRelay.getInstance();
        scripture = sceneRelay.getScripture();
        System.out.println("Here is the scripture: " + scripture);
        placeVerseText(scripture);
        hideBody();
        enableControls();
        guessButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println(getGuess());
                matchAndReveal(getGuess());
                guessField.setText("");
            }
        });
        guessField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                matchAndReveal(getGuess());
                guessField.setText("");
            }
        });
    }
}

