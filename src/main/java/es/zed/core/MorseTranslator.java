package es.zed.core;

import es.zed.classes.MorseFileReader;
import es.zed.classes.WordFileReader;
import es.zed.library.Node;
import es.zed.library.TreeSearch;

import java.util.*;

public class MorseTranslator {

	private final Map<String, String> morseMap;
	private final Set<String> words;
	private final TreeSearch<String> tree;

	public MorseTranslator() {
		this.morseMap = MorseFileReader.loadMorseMap("morse.txt");
		this.words = new HashSet<>(WordFileReader.readWordsFromFile("words.txt"));
		this.tree = new TreeSearch<>();
	}

	public TreeSearch<String> calculateTree(String value, String target) {
		for (int i = 1; i <= Math.min(target.length(), 4); i++) {
			String morseValue = getMorseValue(value, target, i);
			if (morseValue == null) continue;

			Node<String> rootNode = createRootNode(morseValue);
			populateChildren(rootNode, target.substring(i), 0);
			tree.addNode(rootNode);
		}
		return tree;
	}

	private void populateChildren(Node<String> parentNode, String target, int depth) {
		List<Node<String>> childNodes = generateChildNodes(parentNode, target, depth);

		for (Node<String> childNode : childNodes) {
			String remainingTarget = target.substring(getSegmentLength(childNode));
			populateChildren(childNode, remainingTarget, depth + 1);
		}
	}

	private List<Node<String>> generateChildNodes(Node<String> parentNode, String target, int depth) {
		List<Node<String>> childNodes = new ArrayList<>();

		for (int i = 1; i <= Math.min(target.length(), 4); i++) {
			String morseValue = getMorseValue(null, target, i);
			if (morseValue == null) continue;

			String combinedValue = getCombinedValue(parentNode, morseValue);

			if (isPrefixMatch(combinedValue)) {
				Node<String> childNode = new Node<>(combinedValue, depth + 1);
				addNodeAndHandleDoubleChild(parentNode, morseValue, combinedValue, depth, childNode, childNodes);
			}
		}

		return childNodes;
	}

	private void addNodeAndHandleDoubleChild(Node<String> parentNode, String morseValue, String combinedValue, int depth, Node<String> childNode, List<Node<String>> childNodes) {
		Node<String> doubleChild = createDoubleChildIfNeeded(parentNode, morseValue, depth);

		if (!childNodes.contains(parentNode)) {
			childNodes.add(childNode);
			if (doubleChild != null) {
				childNodes.add(doubleChild);
			}
		}

		if (doubleChild != null) {
			parentNode.addChild(doubleChild);
		}
		parentNode.addChild(childNode);
	}

	private Node<String> createDoubleChildIfNeeded(Node<String> parentNode, String morseValue, int depth) {
		if (isExactMatch(parentNode.getValue()) && hasMultiplePrefix(parentNode.getValue())) {
			return new Node<>(parentNode.getValue().concat(morseValue), depth + 1);
		}
		return null;
	}

	private String getCombinedValue(Node<String> parentNode, String morseValue) {
		return isExactMatch(parentNode.getValue())
			? morseValue
			: parentNode.getValue().concat(morseValue);
	}

	private String getMorseValue(String value, String target, int length) {
		String segment = (value == null) ? target.substring(0, length) : value;
		return MorseFileReader.getMorseKey(morseMap, segment);
	}

	private boolean isPrefixMatch(String value) {
		return words.stream().anyMatch(word -> word.startsWith(value));
	}

	private boolean hasMultiplePrefix(String value) {
		return words.stream().filter(word -> word.startsWith(value)).count() > 1;
	}

	private boolean isExactMatch(String value) {
		return words.contains(value);
	}

	private int getSegmentLength(Node<String> node) {
		String lastChar = node.getValue().substring(node.getValue().length() - 1);
		return morseMap.getOrDefault(lastChar, "").length();
	}

	private Node<String> createRootNode(String morseValue) {
		return new Node<>(morseValue, 0);
	}
}