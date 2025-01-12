package es.zed.library;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TreeSearch {

	private final List<Node<String>> treeNode;

	public TreeSearch() {
		this.treeNode = new ArrayList<>();
	}

	public void addNode(Node<String> node) {
		this.treeNode.add(node);
	}

	public void printAllPaths(Set<String> set, Integer depth) {
		Set<List<String>> uniquePaths = new HashSet<>();
		for (Node<String> rootNode : treeNode) {
			List<String> path = new ArrayList<>();
			traversePaths(rootNode, path, set, uniquePaths, depth);
		}

		long validPathsCount = uniquePaths.stream()
			.filter(path -> !path.isEmpty() && isCompletePath(path, set, uniquePaths))
			.peek(System.out::println)
			.count();

		System.out.println("Total number of valid paths: " + validPathsCount);
	}

	private void traversePaths(Node<String> node, List<String> path, Set<String> set, Set<List<String>> uniquePaths, Integer depth) {
		if (node == null) return;

		String value = node.getValue();

		if (set.contains(value)) {
			if (!path.isEmpty() && value.startsWith(path.getLast())) {
				path.removeLast();
			}
			path.add(value);
		}

		if (node.getChildren().isEmpty() && !path.isEmpty() && node.getDepth() >= depth) {
			uniquePaths.add(new ArrayList<>(path));
		} else {
			node.getChildren().forEach(child -> traversePaths(child, new ArrayList<>(path), set, uniquePaths, depth));
		}
	}

	private boolean isCompletePath(List<String> path, Set<String> set, Set<List<String>> uniquePaths) {
		if (path.stream().noneMatch(set::contains)) {
			return false;
		}

		for (List<String> existingPath : uniquePaths) {
			if (existingPath.size() > path.size() && new HashSet<>(existingPath).containsAll(path)) {
				return false;
			}
		}

		return true;
	}


	@Override
	public String toString() {
		return "Tree{" +
			"treeNode=" + treeNode +
			'}';
	}
}