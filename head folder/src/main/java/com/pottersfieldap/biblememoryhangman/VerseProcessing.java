package com.pottersfieldap.biblememoryhangman;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

// Handles the String manipulation for the verse text. Turns one long, punctuated string into a series of lowercase
public class VerseProcessing {
    // Delimits the giant string by spaces and turns them into word objects
    public static List<Word> textToWords(String verseText) {
        List<Word> wordList = new ArrayList<>();
        String[] words = verseText.split(" "); // delimit
        for (int i = 0; i < words.length; i++) { // Search through all words.
            Word w = new Word(words[i]); // Turn into Word
            wordList.add(w); // add to wordList
        }
        wordList = filterWords(wordList); // Filter the word list
        return wordList;
    }
    // Filters out punctuation and makes it lower case. Adds it as the filtered text property on the Word.
    private static List<Word> filterWords(List<Word> wordList) {
        char leftQuote = '\u201c'; // Unicode for left quote
        char rightQuote = '\u201d'; // Unicode for right quote
        for (Word w : wordList) { // Search through all the words
            String newWord = w.getText();
            newWord = newWord.replace(",", ""); // Take out commas
            newWord = newWord.replace(":", ""); // Take out colons
            newWord = newWord.replace(".", ""); // Take out periods
            newWord = newWord.replace(";", ""); // Take out semicolons
            newWord = newWord.replace("?", ""); // Take out question marks
            newWord = newWord.replace("\"", ""); // Take out double quotes
            newWord = newWord.replace("!", ""); // Take out exclamation points
            newWord = newWord.replace("(", ""); // Take out left parentheses
            newWord = newWord.replace(")", ""); // Take out right parentheses
            newWord = newWord.replace("-", " "); // Replace dashes with spaces.
            newWord = newWord.replace(String.valueOf(leftQuote), ""); // Take out left double quotes
            newWord = newWord.replace(String.valueOf(rightQuote), ""); // Take out right double quotes
            newWord = newWord.toLowerCase(Locale.ROOT); // turn into lowercase
            w.setFilteredText(newWord); // Set as filtered text property of the Word.
        }
        return wordList;
    }

}
