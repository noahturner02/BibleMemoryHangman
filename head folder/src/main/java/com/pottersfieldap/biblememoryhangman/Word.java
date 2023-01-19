package com.pottersfieldap.biblememoryhangman;

public class Word {
    public int size;
    public String text;
    public String filteredText; // used to match with the guess supplied by the user. no punctuation and all lowercase
    Word(int size, String text) {
        this.size = size;
        this.text = text;
    }
    public String getText() {
        return text;
    }
    public int getSize() {
        return size;
    }
    public String getFilteredText() {
        return filteredText;
    }
    public void setFilteredText(String filteredText) {
        this.filteredText = filteredText;
    }
}
