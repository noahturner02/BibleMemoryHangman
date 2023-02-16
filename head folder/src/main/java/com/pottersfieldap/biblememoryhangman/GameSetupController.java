package com.pottersfieldap.biblememoryhangman;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

    ObservableList<String> booksOfTheBible = FXCollections.observableArrayList();
    private void playButtonClicked() {
        if (verseTextField.getText().isEmpty() || chapterField.getText().isEmpty() || startVerseField.getText().isEmpty() || (bookChoiceBox.getValue() == null)) {
            System.out.println("Missing info. Please enter the text and the reference for your verse");
        }
        else {
            System.out.println("Let the games begin!");
        }
    }
    private void setBooksOfTheBible() {
        bookChoiceBox.setItems(booksOfTheBible);
        booksOfTheBible.addAll(
                "Genesis", "Exodus", "Leviticus", "Numbers", "Deuteronomy", "Joshua", "Judges", "Ruth", "1 Samuel",
                "2 Samuel", "1 Kings", "2 Kings", "1 Chronicles", "2 Chronicles", "Ezra", "Nehemiah", "Esther", "Job",
                "Psalms", "Proverbs", "Ecclesiastes", "Song of Solomon", "Isaiah", "Jeremiah", "Lamentations",
                "Ezekiel", "Daniel", "Hosea", "Joel", "Amos", "Obadiah", "Jonah", "Micah", "Nahum", "Habakkuk",
                "Zephaniah", "Haggai", "Zechariah", "Malachi", "Matthew", "Mark", "Luke", "John", "Acts", "Romans",
                "1 Corinthians", "2 Corinthians", "Galatians", "Ephesians", "Philippians", "Colossians", "1 Thessalonians",
                "2 Thessalonians", "1 Timothy", "2 Timothy", "Titus", "Philemon", "Hebrews", "James", "1 Peter",
                "2 Peter", "1 John", "2 John", "3 John", "Jude", "Revelation"
        );
    }
    @FXML
    public void initialize() {
        setBooksOfTheBible();
        playButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                playButtonClicked();
            }
        });
    }

}
