package es.zed.core;

import es.zed.classes.MorseFileReader;
import es.zed.classes.WordFileReader;
import es.zed.library.Node;
import es.zed.library.TreeSearch;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class MorseTranslator {
	private final Map<String, String> morseMap;
	private final Set<String> words;
	private final TreeSearch<String> tree;

	private Node<String> node;
	List<String> candidates;

	public MorseTranslator() {
		morseMap = MorseFileReader.loadMorseMap("morse.txt");
		words = new HashSet<>(WordFileReader.readWordsFromFile("words.txt"));
		tree = new TreeSearch<>();
	}

	public TreeSearch<String> calculateTree(String value, String target) {
		for (int i = 1; i <= Math.min(target.length(), 4); i++) {
			String morseValue = getMorseValue(value, target, i);
			if (morseValue == null) continue;

			this.node = new Node<>(morseValue, 0);
			calculateChildren(node, target.substring(i), 0);
			tree.addNode(node);
		}
		return tree;
	}

	private void calculateChildren(Node<String> parentNode, String target, int depth) {
		List<Node<String>> nodes = new ArrayList<>();
		String morseValue = "";
		String value;
		for (int i = 1; i <= Math.min(target.length(), 4); i++) {
			morseValue = getMorseValue(null, target, i);
			if (Objects.nonNull(morseValue)) {
				value = parentNode.getValue().concat(morseValue);
				if (startsWith(value) || isEquals(value)) {
					Node<String> childNode;
					if(isEquals(value)) {
						childNode = new Node<>(morseValue, depth + 1);
					} else {
						childNode = new Node<>(value, depth + 1);
					}
					if (nodes.contains(parentNode)){
						nodes.remove(parentNode);
					}
					else {
						nodes.add(childNode);
					}

					if (depth == 0) {
						this.node.addChild(childNode);
					}
					else {
						this.node.findNode(parentNode).addChild(childNode);
					}
				}
			}
		}
		for (Node<String> parent : nodes) {
			String remove = morseMap.get(parent.getValue().substring(parent.getValue().length() - 1));
			calculateChildren(parent, target.substring(remove.length()), depth + 1);
		}
	}

	private String getMorseValue(String value, String target, int length) {
		String segment = value == null ? target.substring(0, length) : value;
		return MorseFileReader.getMorseKey(morseMap, segment);
	}

	private boolean startsWith(String value) {
		for (String word : words) {
			if (word.startsWith(value)) {
				return true;
			}
		}
		return false;
	}

	private boolean isEquals(String value) {
		for (String word : words) {
			if (word.equals(value)) {
				return true;
			}
		}
		return false;
	}
}