package es.zed;

import es.zed.classes.WordFileReader;
import es.zed.core.MorseTranslator;
import es.zed.library.TreeSearch;

public class Main {
  public static void main(String[] args) {
    MorseTranslator translator = new MorseTranslator();

    TreeSearch<String> treeSearch =  translator.calculateTree(null, "--.--.---.......-.---.-.-.-..-.....--..-....-.-----..-");

    //System.out.println(translator.calculateTree(null, "--.--.---.......-.---.-.-.-..-.....--..-....-.-----..-"));

    treeSearch.printAllPaths(WordFileReader.readWordsFromFile("words.txt"));
  }
}