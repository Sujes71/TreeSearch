package es.zed.core;

import es.zed.classes.MorseFileReader;
import es.zed.classes.WordFileReader;
import es.zed.library.Node;
import es.zed.library.Tree;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class MorseTranslator {
	private final Map<String, String> morseMap;
	private final Set<String> words;
	private final Tree<String> tree;

	public MorseTranslator() {
		morseMap = MorseFileReader.loadMorseMap("morse.txt");
		words = new HashSet<>(WordFileReader.readWordsFromFile("words.txt"));
		tree = new Tree<>();
	}

	public Tree<String> calculateTree(String value, String target, Integer depth) {
		List<String> values = new ArrayList<>();

		for (int i = 1; i <= (Math.min(target.length(), 4)) ; i++) {
			if (Objects.isNull(value) || value.length() <= 4) {
				values.add(MorseFileReader.getMorseKey(morseMap, value == null ? target.substring(0, i) : value));
				Node<String> node = new Node<>(depth, values.get(i - 1));
				tree.addNode(node);
				tree.addNode(calculateChildren(node, value == null ? target.substring(0, i) : value, target.substring(i), depth));
			}
			return calculateTree(null, target.substring(values.size()), depth + 1);
		}
		return tree;
	}

	public Node<String> calculateChildren(Node<String> node, String value, String target, Integer depth) {
		List<String> values = new ArrayList<>();

		for (int i = 1; i <= (Math.min(target.length(), 4)) ; i++) {
			values.add(MorseFileReader.getMorseKey(morseMap, target.substring(0, i)));
			Node<String> childNode = new Node<>(depth + 1, values.get(i - 1));
			node.addChild(childNode);

			if (i == 4) {
				calculateTree(value.concat(target.substring(0, 1)), target, depth);
			}
		}
		return node;
	}
}