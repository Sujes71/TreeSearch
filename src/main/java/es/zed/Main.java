package es.zed;

import es.zed.core.MorseTranslator;
import java.util.List;

public class Main {
  public static void main(String[] args) {
    MorseTranslator translator = new MorseTranslator();

    System.out.println(translator.calculateTree(null, "--.--.---.......-.---.-.-.-..-.....--..-....-.-----..-", 0));
  }
}