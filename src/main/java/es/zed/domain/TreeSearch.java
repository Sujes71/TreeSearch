package es.zed.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TreeSearch {

	private final List<Node<String>> treeNodes;

	public TreeSearch() {
		this.treeNodes = new ArrayList<>();
	}

	public void addNode(Node<String> node) {
		this.treeNodes.add(node);
	}

	public void printAllPaths(Set<String> words) {
		Set<List<String>> uniquePaths = new HashSet<>();
		for (Node<String> rootNode : treeNodes) {
			findPaths(rootNode, new ArrayList<>(), words, uniquePaths);
		}

		uniquePaths.stream()
			.filter(path -> isCompletePath(path, words))
			.forEach(System.out::println);
	}

	private void findPaths(Node<String> node, List<String> currentPath, Set<String> words, Set<List<String>> uniquePaths) {
		if (node == null) return;

		String value = node.getValue();

		if (words.contains(value)) {
			addToPath(currentPath, value);
		}

		if (node.isValid()) {
			uniquePaths.add(new ArrayList<>(currentPath));
			return;
		}

		for (Node<String> child : node.getChildren()) {
			findPaths(child, new ArrayList<>(currentPath), words, uniquePaths);
		}
	}

	private void addToPath(List<String> path, String value) {
		if (!path.isEmpty() && value.startsWith(path.getLast())) {
			path.removeLast();
		}
		path.add(value);
	}

	private boolean isCompletePath(List<String> path, Set<String> validValues) {
		if (path.stream().noneMatch(validValues::contains)) {
			return false;
		}

		for (String value : validValues) {
			if (path.contains(value)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return "TreeSearch{" +
			"treeNodes=" + treeNodes +
			'}';
	}
}