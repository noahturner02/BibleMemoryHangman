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

import java.util.function.UnaryOperator;
/* This is the FXML controller for the setup wizard. This will control the logic and layout of this scene.*/
public class GameSetupController {
    // Initialize all the FXML elements
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
    // List of books of the bible. Will be used for the ChoiceBox.
    ObservableList<String> booksOfTheBible = FXCollections.observableArrayList();
    // Creates and applies a numbers-only filter to certain boxes. A one time setup routine.
    private void setTextFieldFormatters() {
        // Create the filter with a lambda expression
        UnaryOperator<TextFormatter.Change> filter = change -> {
            // text is the text that was just added
            String text = change.getText();
            // If the text is a digit, keep it. If not, discard it.
            if (text.matches("[0-9]*")) {
                return change;
            }
            return null;
        };
        // Apply this filter to chapterField, startVerseField, and endVerseField.
        chapterField.setTextFormatter(new TextFormatter<String>(filter));
        startVerseField.setTextFormatter(new TextFormatter<String>(filter));
        endVerseField.setTextFormatter(new TextFormatter<String>(filter));
    }
    // Sets the action of the loadButtonBeing clicked. Mainly starting the webscraper and adding the data to the scene
    private void loadButtonClicked() {
        // Make sure we at least know the book, chapter, and starting verse number
        if (chapterField.getText().isEmpty() || startVerseField.getText().isEmpty() || (bookChoiceBox.getValue() == null)) {
            System.out.println("Missing info. Please enter the text and the reference for your verse");
        }
        else {
            // If no end verse is given, we only want a single verse
            if (endVerseField.getText().isEmpty()) {
                endVerseField.setText(startVerseField.getText());
            }
            // Fill the verseTextField with the results of the webscraper
            verseTextField.setText(WebScraper.getRawVerseText((String) bookChoiceBox.getValue(), Integer.parseInt(chapterField.getText()), Integer.parseInt(startVerseField.getText()), Integer.parseInt(endVerseField.getText()), "ESV"));
        }
    }
    // Logic that runs when the play button is pressed. Transition scenes and send data to the hangman game
    private void startGame() {
        try {
            // Set the scripture data.
            SceneRelay sceneRelay = SceneRelay.getInstance();
            sceneRelay.setScripture(verseTextField.getText());
            // Switch scenes to the hangman game
            sceneRelay.switchScene("game-setup-wizard.fxml", "hangman-scene.fxml", 900, 600, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // Routine to add the books of the bible to the ChoiceBox
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
    /* Initialize method for FXML runs when FXMLLoader.load() is called. Since that is only called once for this class,
    This method will only run once. It will not run on replays of the game. */
    public void initialize() {
        // Call our setup routines
        setBooksOfTheBible();
        setTextFieldFormatters();
        // Play button is disabled until a verse is loaded into the verseText box.
        playButton.setDisable(true);
        // Set logic for load button
        loadButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // Call subroutine
                loadButtonClicked();
                // Re-enable the play button
                playButton.setDisable(false);
            }
        });
        // Set logic for play button being clicked
        playButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // call subroutine
                startGame();
            }
        });
    }

}
