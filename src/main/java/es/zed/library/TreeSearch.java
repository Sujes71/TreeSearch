package es.zed.library;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

	public void printAllValues(Set<String> set) {
		for (Node<T> rootNode : treeNode) {
			printNodeValues(rootNode, set);
		}
	}

	private void printNodeValues(Node<T> node, Set<String> set) {
		if (node == null)
			return;

		if (node.getValue() instanceof String value) {
			if (set.contains(value)) {
				System.out.println(value);
			}
		}
	}

	/**
	 * Recorre todos los caminos posibles desde la raíz a las hojas y los imprime.
	 */
	public void printAllPaths(Set<String> set) {
		for (Node<T> rootNode : treeNode) {
			List<T> path = new ArrayList<>();
			traversePaths(rootNode, path, set);
		}
	}

	private void traversePaths(Node<T> node, List<T> path, Set<String> set) {
		if (node == null) return;

		if (node.getValue() instanceof String value && set.contains(value)) {
			path.add(node.getValue());
		}
		if (node.getChildren().isEmpty()) {
			System.out.println(path);
		} else {
			for (Node<T> child : node.getChildren()) {
				traversePaths(child, new ArrayList<>(path), set);
			}
		}
	}


	@Override
	public String toString() {
		return "Tree{" +
			"treeNode=" + treeNode +
			'}';
	}
}
