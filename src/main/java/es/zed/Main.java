package es.zed;

import es.zed.classes.MorseFileReader;
import es.zed.classes.WordFileReader;
import es.zed.core.MorseTranslator;
import es.zed.library.TreeSearch;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Main {
  public static void main(String[] args) {
    Map<String, String> morseMap = MorseFileReader.loadMorseMap("morse.txt");
    Set<String> words = new HashSet<>(WordFileReader.readWordsFromFile("words.txt"));

    MorseTranslator translator = new MorseTranslator(morseMap, words);

    TreeSearch treeSearch =  translator.calculateTree("--.--.---.......-.---.-.-.-..-.....--..-....-.-----..-");
    treeSearch.printAllPaths(words, 15);
  }
}