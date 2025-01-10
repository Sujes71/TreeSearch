package es.zed.core;

import es.zed.classes.MorseFileReader;
import es.zed.classes.WordFileReader;
import es.zed.library.Node;
import es.zed.library.TreeSearch;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MorseTranslator {
	private final Map<String, String> morseMap;
	private final Set<String> words;
	private final TreeSearch<String> tree;

	public MorseTranslator() {
		morseMap = MorseFileReader.loadMorseMap("morse.txt");
		words = new HashSet<>(WordFileReader.readWordsFromFile("words.txt"));
		tree = new TreeSearch<>();
	}

	public TreeSearch<String> calculateTree(String value, String target, int depth) {
		for (int i = 1; i <= Math.min(target.length(), 4); i++) {
			String morseValue = getMorseValue(value, target, i);
			if (morseValue == null) continue;

			Node<String> node = new Node<>(depth, morseValue);
			calculateChildren(node, target.substring(i), depth);
			tree.addNode(node);
		}
		return tree;
	}

	private void calculateChildren(Node<String> node, String target, int depth) {
		for (int i = 1; i <= Math.min(target.length(), 4); i++) {
			String morseValue = getMorseValue(null, target, i);
			if (morseValue == null) continue;

			Node<String> childNode = new Node<>(depth + 1, morseValue);
			node.addChild(childNode);
		}
	}

	private String getMorseValue(String value, String target, int length) {
		String segment = value == null ? target.substring(0, length) : value;
		return MorseFileReader.getMorseKey(morseMap, segment);
	}
}