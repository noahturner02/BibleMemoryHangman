package com.pottersfieldap.biblememoryhangman;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.function.UnaryOperator;

public class GameSetupController {
    @FXML
    Button loadButton;
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

    private void setTextFieldFormatters() {
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String text = change.getText();
            if (text.matches("[0-9]*")) {
                return change;
            }
            return null;
        };
        chapterField.setTextFormatter(new TextFormatter<String>(filter));
        startVerseField.setTextFormatter(new TextFormatter<String>(filter));
        endVerseField.setTextFormatter(new TextFormatter<String>(filter));
    }
    private void loadButtonClicked() {
        if (chapterField.getText().isEmpty() || startVerseField.getText().isEmpty() || (bookChoiceBox.getValue() == null)) {
            System.out.println("Missing info. Please enter the text and the reference for your verse");
        }
        else {
            // If no end verse is given, we only want a single verse
            if (endVerseField.getText().isEmpty()) {
                endVerseField.setText(startVerseField.getText());
            }
            verseTextField.setText(WebScraper.getRawVerseText((String) bookChoiceBox.getValue(), Integer.parseInt(chapterField.getText()), Integer.parseInt(startVerseField.getText()), Integer.parseInt(endVerseField.getText()), "ESV"));
        }
    }
    private void startGame() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("hangman-scene.fxml"));
            Stage window = (Stage) playButton.getScene().getWindow();
            window.setScene(new Scene(root, 900, 600));
        } catch (Exception e) {
            e.printStackTrace();
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
        setTextFieldFormatters();
        playButton.setDisable(true);
        loadButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                loadButtonClicked();
                playButton.setDisable(false);
            }
        });
        playButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                startGame();
            }
        });
    }

}
