package com.pottersfieldap.biblememoryhangman;

public class Word {
    public int size;
    public String text;
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
}
