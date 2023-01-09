package com.pottersfieldap.biblememoryhangman;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.util.List;


public class HangmanController {
    @FXML
    StackPane manPane;
    @FXML
    StackPane verseTextPane;

    private void createBlanksFromText(String verseText) { // Turns the verse text into a set of blanks
        List<Word> wordList = VerseProcessing.textToWords("Paul a servant of Christ Jesus, called to be an apostle, set apart for the gospel of God");
        System.out.println(wordList.get(4).getSize());
    }
    @FXML
    public void initialize() {
        createBlanksFromText("Paul a servant of Christ Jesus, called to be an apostle, set apart for the gospel of God");
    }
}

