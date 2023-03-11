package com.pottersfieldap.biblememoryhangman;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class VerseProcessing {
    public static List<Word> textToWords(String verseText) {
        List<Word> wordList = new ArrayList<>();
        String[] words = verseText.split(" ");
        for (int i = 0; i < words.length; i++) {
            Word w = new Word(words[i].length(), words[i]);
            wordList.add(w);
        }
        wordList = filterWords(wordList);
        return wordList;
    }
    private static List<Word> filterWords(List<Word> wordList) {
        // filter out punctuation and make everything lowercase
        List<Word> filteredWordList = new ArrayList<>();
        for (Word w : wordList) {
            String newWord = w.getText();
            newWord = newWord.replace(",", "");
            newWord = newWord.replace(":", "");
            newWord = newWord.replace(".", "");
            newWord = newWord.replace(";", "");
            newWord = newWord.toLowerCase(Locale.ROOT);
            w.setFilteredText(newWord);
           // System.out.println(w.getFilteredText());
        }
        return wordList;
    }

}
