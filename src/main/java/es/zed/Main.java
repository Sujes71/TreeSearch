package es.zed;

import es.zed.core.MorseTranslator;
import es.zed.domain.MorseSearch;
import es.zed.infrastructure.MorseFileReader;
import es.zed.infrastructure.WordFileReader;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Main {
  public static void main(String[] args) {
    Map<String, String> morseMap = MorseFileReader.loadMorseMap("morse.txt");
    Set<String> words = new HashSet<>(WordFileReader.readWordsFromFile("words.txt"));

    MorseTranslator translator = new MorseTranslator(morseMap, words);

    MorseSearch morseSearch =  translator.calculateTree("--.--.---.......-.---.-.-.-..-.....--..-....-.-----..-");
    morseSearch.printAllPaths(words);
  }
}