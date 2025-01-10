package es.zed.library;

import java.util.ArrayList;
import java.util.List;

public class Tree<T> {

	private final List<Node<T>> treeNode;

	public Tree() {
		this.treeNode = new ArrayList<>();
	}

	public void addNode(Node<T> node) {
		this.treeNode.add(node);
	}

	public Node<T> getNodeByIndex(Integer index) {
		if (treeNode.isEmpty()) return null;
		return treeNode.get(index);
	}
	@Override
	public String toString() {
		return "Tree{" +
			"treeNode=" + treeNode +
			'}';
	}
}
