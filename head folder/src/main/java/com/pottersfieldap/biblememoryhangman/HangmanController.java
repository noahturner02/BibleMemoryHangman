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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// This class handles the actual game logic.
public class HangmanController {
    // Initialization of FXML elements
    @FXML
    StackPane manPane;
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

    // Initializing other things
    String scripture = "";
    List<Word> currentWordList = new ArrayList<>();
    SceneRelay sceneRelay = SceneRelay.getInstance();
    // Gets the list of words
    public List<Word> getCurrentWordList() {
        return currentWordList;
    }
    // Sets the current word list
    public void setCurrentWordList(List<Word> wordList) {
        this.currentWordList = wordList;
    }
    private boolean checkForWin() { // Iterates through all the word panes to see if they are all visible
        boolean win = true;
        for (int i = 0; i < verseTextPane.getChildren().size(); i++) { // All the children of the TilePane
            Pane p = (Pane) verseTextPane.getChildren().get(i); // Cast the child into a Pane
            if (!p.getChildren().get(0).isVisible()) { // If the word is invisible
                // We haven't won yet. exit the method and continue playing
                win = false;
                break;
            }
        }
        // If all the words are visible, then the game has been won, return true.
        return win;
    }
    // Places the invisible labels and shows the blanks of corresponding size
    private void placeVerseText(String verseText) {
        List<Word> wordList = VerseProcessing.textToWords(verseText); // Generate the word list from raw text
        setCurrentWordList(wordList); // Set the list to the class, making it available to all
        for (Word w : wordList) { // Place the invisible label and line for each word
            Pane p = new Pane(); // wrapper pane for the labels
            Label l = new Label(w.getText()); // Make label
            l.setVisible(false); // Make label invisible
            p.getChildren().add(l); // Add the label to wrapper pane
            l.layoutXProperty().bind(p.widthProperty().subtract(l.widthProperty()).divide(2)); // bind the label to the center of the pane
            verseTextPane.getChildren().add(p); // Add wrapper pane to TilePane
            drawLines(p); // Draw the line for this word
        }
    }
    // Just returns contents of guessField
    private String getGuess() {
        return guessField.getText();
    }
    // Draws the line underneath the invisible word.
    private void drawLines(Pane p) {
        Line l = new Line(); // Make a new line
        Label word = (Label) p.getChildren().get(0); // Get the label from the Pane
        // ChangeListeners will keep the blank under the word even when the label changes place
        word.layoutXProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                l.setStartX((p.getWidth() - word.getWidth()) / 2);  // Bind the start of the line to the beginning of the word
            }
        });
        word.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                l.setLayoutY(word.getHeight()); // Bind the y layout of the line to underneath the word
            }
        });
        word.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                l.setEndX((p.getWidth() - (p.getWidth() - word.getWidth()) / 2)); // Bind the end of the line to the end of the word
            }
        });
        p.getChildren().add(l); // Add the line to the Pane
    }
    // Reveals the next piece of the hangman
    private void revealPiece() {
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
    // Matches the player's guess to the current list of words
    private void matchAndReveal(String guess) {
        boolean hit = false;
        for (int i = 0; i < getCurrentWordList().size(); i++) { // Iterate through word list
            if (getCurrentWordList().get(i).getFilteredText().equalsIgnoreCase(guess)) { // if the guess equals the word
                revealWordByIndex(i); // reveal the word
                hit = true;
            }
        }
        if (checkForWin()) { // If a win has happened, end the game
            endGame(true);
        }
        if (!hit) { // If the guess didn't match any words, reveal a piece
            revealPiece();
        }
    }
    // Given a word's index, make it visible
    private void revealWordByIndex(int i) {
        Pane p = (Pane) verseTextPane.getChildren().get(i); // Get the pane from the TilePane
        p.getChildren().get(0).setVisible(true); // make the label visible
    }
    // Makes the whole body invisible.
    private void hideBody() {
        head.setVisible(false);
        spine.setVisible(false);
        leftArm.setVisible(false);
        rightArm.setVisible(false);
        leftLeg.setVisible(false);
        rightLeg.setVisible(false);
    }
    // When the game has ended, disable the guessField and guess Button.
    private void disableControls() {
        guessField.setDisable(true);
        guessButton.setDisable(true);
    }
    // When the scene is being loaded and reset, re-enable the controls
    private void enableControls() {
        guessField.setDisable(false);
        guessButton.setDisable(false);
    }
    // Once the game has ended, switch the scene to the result screen and disable controls
    private void endGame(Boolean win) {
        sceneRelay.setWin(win);
        sceneRelay.newStage("game-result-window.fxml", 300, 600); // Make new Stage
        disableControls();
        if (win) {
            sceneRelay.getFromStageMap("game-result-window.fxml").setTitle("You Win!");
        } else {
            sceneRelay.getFromStageMap("game-result-window.fxml").setTitle("You Lose!");
        }
    }

    @FXML
    // FXML initialize method. Since this scene is always created new, this will run anytime the scene shows.
    public void initialize() {
        scripture = sceneRelay.getScripture(); // Get the scripture from the setup
        placeVerseText(scripture); // Place the verse text into the game
        hideBody(); // hide all the body parts
        enableControls(); // enable controls
        guessButton.setOnMouseClicked(new EventHandler<MouseEvent>() { // When the guessButton is clicked, will try to match guess
            @Override
            public void handle(MouseEvent mouseEvent) {
                matchAndReveal(getGuess()); // match guess
                guessField.setText(""); // empty box
            }
        });
        guessField.setOnKeyPressed(event -> { // When ENTER is pressed inside the guessBox, it will try to match what's inside
            if (event.getCode() == KeyCode.ENTER) {
                matchAndReveal(getGuess()); // match guess
                guessField.setText(""); // empty box
            }
        });
    }
}

