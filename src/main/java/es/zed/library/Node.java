package es.zed.library;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Node<T> {

  private UUID id;
  private int depth;
  private T value;
  private List<Node<T>> children;

  public Node(T value, int depth) {
    this.depth = depth;
    this.value = value;
    this.children = new ArrayList<>();
    this.id = UUID.randomUUID();
  }

  public T getValue() {
    return value;
  }

  public int getDepth() {
    return depth;
  }

  public List<Node<T>> getChildren() {
    return children;
  }

  public void addChild(Node<T> child) {
    this.children.add(child);
  }

  @Override
  public String toString() {
    return "Node{" +
        "depth=" + depth +
        ", value=" + value +
        ", children=" + children +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Node<?> node = (Node<?>) o;
    return Objects.equals(id, node.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(depth, value, children);
  }
}