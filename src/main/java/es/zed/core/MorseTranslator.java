package es.zed.core;

import es.zed.classes.MorseFileReader;
import es.zed.classes.WordFileReader;
import es.zed.library.Node;
import es.zed.library.TreeSearch;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

	public TreeSearch<String> calculateTree(String value, String target, Integer depth) {
		List<String> values = new ArrayList<>();

		for (int i = 1; i <= (Math.min(target.length(), 4)) ; i++) {
			values.add(MorseFileReader.getMorseKey(morseMap, value == null ? target.substring(0, i) : value));
			Node<String> node = new Node<>(depth, values.get(i - 1));
			calculateChildren(node, target.substring(i), depth);
			tree.addNode(node);
		}
		return tree;
	}

	public void calculateChildren(Node<String> node, String target, Integer depth) {
		List<String> values = new ArrayList<>();

		for (int i = 1; i <= (Math.min(target.length(), 4)) ; i++) {
			values.add(MorseFileReader.getMorseKey(morseMap, target.substring(0, i)));
			Node<String> childNode = new Node<>(depth + 1, values.get(i - 1));
			node.addChild(childNode);
		}
	}
}