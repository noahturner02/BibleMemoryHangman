package com.pottersfieldap.biblememoryhangman;

/* Class to manage the words of each verse. Has 'text' for displaying the actual text from the verse, and has
filteredText for matching guesses.
 */
public class Word {
    public String text; // Actual text with punctuation and capitalization
    public String filteredText; // used to match with the guess supplied by the user. no punctuation and all lowercase
    // Constructor. Sets the regular text of the Word.
    Word(String text) {
        this.text = text;
    }
    // Get the actual text of the Word
    public String getText() {
        return text;
    }
    // Get the filtered text of the Word
    public String getFilteredText() {
        return filteredText;
    }
    // Set the filteredText of the Word
    public void setFilteredText(String filteredText) {
        this.filteredText = filteredText;
    }
}
