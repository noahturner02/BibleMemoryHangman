package com.pottersfieldap.biblememoryhangman;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.shape.Line;

import java.util.List;


public class HangmanController {
    @FXML
    Pane manPane;
    @FXML
    TilePane verseTextPane;
    int LETTERWIDTH = 10;

    private void placeVerseText(String verseText) { // Places the invisible labels and shows the blanks of corresponding size
        List<Word> wordList = VerseProcessing.textToWords(verseText);
        int xLayoutTracker = 0; // Tracks the blank lines that are drawn in the verse text pane. follows from left to right
        int yLayoutTracker = 0; // Tracks the blank lines on the y axis
        int lineSize = 0;
        for (Word w : wordList) { // Make a new line for each word
            Pane p = new Pane(); // wrapper pane for the labels
            Label l = new Label(w.getText());
            p.getChildren().add(l);
            l.layoutXProperty().bind(p.widthProperty().subtract(l.widthProperty()).divide(2)); // bind the label to the center of the pane
            verseTextPane.getChildren().add(p);
        }
    }
    @FXML
    public void initialize() {
        placeVerseText("Paul a servant of Christ Jesus, called to be an apostle, set apart for the gospel of God, which he promised beforehand through his prophets in the holy scriptures, concerning his son, who was descended from David according to the flesh and was declared to be the son of God in power according to the Spirit of holiness by his resurrection from the dead, Christ Jesus our Lord.");
    }
}

