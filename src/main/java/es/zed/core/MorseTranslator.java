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

	private Node<String> rootNode;

	public MorseTranslator() {
		this.morseMap = MorseFileReader.loadMorseMap("morse.txt");
		this.words = new HashSet<>(WordFileReader.readWordsFromFile("words.txt"));
		this.tree = new TreeSearch<>();
	}

	public TreeSearch<String> calculateTree(String value, String target) {
		for (int i = 1; i <= Math.min(target.length(), 4); i++) {
			String morseValue = getMorseValue(value, target, i);
			if (morseValue == null) continue;

			rootNode = new Node<>(morseValue, 0);
			populateChildren(rootNode, target.substring(i), 0);
			tree.addNode(rootNode);
		}
		return tree;
	}

	private void populateChildren(Node<String> parentNode, String target, int depth) {
		List<Node<String>> childNodes = new ArrayList<>();

		for (int i = 1; i <= Math.min(target.length(), 4); i++) {
			String morseValue = getMorseValue(null, target, i);
			if (morseValue == null) continue;

			String combinedValue = isExactMatch(parentNode.getValue())
				? morseValue
				: parentNode.getValue().concat(morseValue);

			if (isPrefixMatch(combinedValue)) {
				Node<String> childNode = new Node<>(combinedValue, depth + 1);

				if (!childNodes.contains(parentNode)) {
					childNodes.add(childNode);
				}
				parentNode.addChild(childNode);

			}
		}

		for (Node<String> childNode : childNodes) {
			String remainingTarget = target.substring(getSegmentLength(childNode));
			populateChildren(childNode, remainingTarget, depth + 1);
		}
	}

	private String getMorseValue(String value, String target, int length) {
		String segment = (value == null) ? target.substring(0, length) : value;
		return MorseFileReader.getMorseKey(morseMap, segment);
	}

	private boolean isPrefixMatch(String value) {
		return words.stream().anyMatch(word -> word.startsWith(value));
	}

	private boolean hasMultiplePrefix(String value) {
		long count = words.stream()
			.filter(word -> word.startsWith(value))
			.count();
		return count > 1;
	}

	private boolean isExactMatch(String value) {
		return words.contains(value);
	}

	private int getSegmentLength(Node<String> node) {
		String lastChar = node.getValue().substring(node.getValue().length() - 1);
		return morseMap.get(lastChar).length();
	}
}