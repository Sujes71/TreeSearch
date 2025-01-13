package es.zed.core;

import es.zed.domain.MorseSearch;
import es.zed.domain.Node;
import es.zed.infrastructure.MorseFileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MorseTranslator {

	private final Map<String, String> morseMap;
	private final Set<String> words;
	private final MorseSearch morseSearch;

	public MorseTranslator(Map<String, String> morseMap, Set<String> words) {
		this.morseMap = morseMap;
		this.words = words;
		this.morseSearch = new MorseSearch();
	}

	public MorseSearch calculateTree(String target) {
		if (target == null || target.isEmpty()) {
			throw new IllegalArgumentException("Target cannot be null or empty");
		}

		for (int i = 1; i <= Math.min(target.length(), 4); i++) {
			String morseValue = getMorseValue(target, i);
			if (morseValue == null) continue;

			Node<String> rootNode = createRootNode(morseValue);
			populateChildren(rootNode, target.substring(i), 0);
			morseSearch.addNode(rootNode);
		}

		return morseSearch;
	}

	private void populateChildren(Node<String> parentNode, String target, int depth) {
		if (target.isEmpty()) return;

		List<Node<String>> childNodes = generateChildNodes(parentNode, target, depth);

		for (Node<String> childNode : childNodes) {
			String remainingTarget = target.substring(getSegmentLength(childNode));

			if (isExactMatch(childNode.getValue()) && remainingTarget.isEmpty()) {
				childNode.setValid(true);
			}

			populateChildren(childNode, remainingTarget, depth + 1);
		}
	}

	private List<Node<String>> generateChildNodes(Node<String> parentNode, String target, int depth) {
		List<Node<String>> childNodes = new ArrayList<>();

		for (int i = 1; i <= Math.min(target.length(), 4); i++) {
			String morseValue = getMorseValue(target, i);
			if (morseValue == null) continue;

			String combinedValue = getCombinedValue(parentNode, morseValue);

			if (isPrefixMatch(combinedValue)) {
				Node<String> childNode = new Node<>(combinedValue, depth + 1);
				handleDoubleChild(parentNode, morseValue, depth, childNode, childNodes);
				parentNode.addChild(childNode);
			}
		}

		return childNodes;
	}

	private void handleDoubleChild(Node<String> parentNode, String morseValue, int depth, Node<String> childNode, List<Node<String>> childNodes) {
		Node<String> doubleChild = createDoubleChildIfNeeded(parentNode, morseValue, depth);

		if (doubleChild != null && !childNodes.contains(doubleChild)) {
			childNodes.add(doubleChild);
			parentNode.addChild(doubleChild);
		}

		if (!childNodes.contains(childNode)) {
			childNodes.add(childNode);
		}
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

	private String getMorseValue(String target, int length) {
		String segment = target.substring(0, length);
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