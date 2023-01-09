package com.pottersfieldap.biblememoryhangman;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class VerseProcessing {
    public static List<Word> textToWords(String verseText) {
        List<Word> wordList = new ArrayList<>();
        String[] words = verseText.split(" ");
        for (int i = 0; i < words.length; i++) {
            Word w = new Word(words[i].length(), words[i]);
            wordList.add(w);
        }
        return wordList;
    }
}
