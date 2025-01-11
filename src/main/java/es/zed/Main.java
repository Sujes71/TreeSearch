package es.zed;

import es.zed.core.MorseTranslator;

public class Main {
  public static void main(String[] args) {
    MorseTranslator translator = new MorseTranslator();

    System.out.println(translator.calculateTree(null, "--.--.---.......-.---.-.-.-..-.....--..-....-.-----..-"));
  }
}