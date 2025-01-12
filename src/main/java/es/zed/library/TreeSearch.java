package es.zed.library;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TreeSearch<T> {

	private final List<Node<T>> treeNode;

	public TreeSearch() {
		this.treeNode = new ArrayList<>();
	}

	public void addNode(Node<T> node) {
		this.treeNode.add(node);
	}

	public Node<T> getNodeByIndex(Integer index) {
		if (treeNode.isEmpty()) return null;
		return treeNode.get(index);
	}

	public void printAllPaths(Set<String> set) {
		Set<List<T>> uniquePaths = new HashSet<>();
		for (Node<T> rootNode : treeNode) {
			traversePaths(rootNode, new ArrayList<>(), set, uniquePaths);
		}

		uniquePaths.stream()
			.filter(path -> !path.isEmpty())
			.forEach(System.out::println);
	}

	private void traversePaths(Node<T> node, List<T> path, Set<String> set, Set<List<T>> uniquePaths) {
		if (node == null) return;

		if (node.getValue() instanceof String value && set.contains(value)) {
			path = deleteShort(path, value);
			path.add(node.getValue());
		}

		if (node.getChildren().isEmpty() && !path.isEmpty()) {
			uniquePaths.add(new ArrayList<>(path));
		} else {
			for (Node<T> child : node.getChildren()) {
				traversePaths(child, new ArrayList<>(path), set, uniquePaths);
			}
		}
	}

	@SuppressWarnings("unchecked")
	private List<T> deleteShort(List<T> path, String value) {
		List<String> stringPath = (List<String>) path;
		List<String> updatedPath = stringPath.stream()
			.filter(word -> !value.startsWith(word))
			.collect(Collectors.toList());

		return (List<T>) updatedPath;
	}

	@Override
	public String toString() {
		return "Tree{" +
			"treeNode=" + treeNode +
			'}';
	}
}
