package com.pottersfieldap.biblememoryhangman;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

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

    int LETTERWIDTH = 10;
    String longer_words = "supercalifragilistic expialidocious supercalifragilistic expialidocious supercalifragilistic expialidocious supercalifragilistic expialidocious supercalifragilistic expialidocious supercalifragilistic expialidocious supercalifragilistic expialidocious supercalifragilistic expialidocious";
    String romans = "Paul a servant of Christ Jesus, called to be an apostle, set apart for the gospel of God, which he promised beforehand through his prophets in the holy scriptures, concerning his son, who was descended from David according to the flesh and was declared to be the son of God in power according to the Spirit of holiness by his resurrection from the dead, Christ Jesus our Lord.";
    List<Word> currentWordList = new ArrayList<>();

    public List<Word> getCurrentWordList() {
        return currentWordList;
    }
    public void setCurrentWordList(List<Word> wordList) {
        this.currentWordList = wordList;
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
        }
        else {
            // Game Over Logic Here
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

    @FXML
    public void initialize() {
        placeVerseText(romans);
        hideBody();
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

