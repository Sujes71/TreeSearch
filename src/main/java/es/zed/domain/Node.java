package es.zed.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Node<T> {

  private final UUID id;
  private final int depth;
  private final T value;
  private final List<Node<T>> children;
  private boolean isValid;

  public Node(T value, int depth) {
    this.id = UUID.randomUUID();
    this.depth = depth;
    this.value = value;
    this.children = new ArrayList<>();
    this.isValid = false;
  }

  public T getValue() {
    return value;
  }

  public List<Node<T>> getChildren() {
    return new ArrayList<>(children);
  }

  public boolean isValid() {
    return isValid;
  }

  public void setValid(boolean isValid) {
    this.isValid = isValid;
  }

  public void addChild(Node<T> child) {
    if (Objects.nonNull(child)) {
      this.children.add(child);
    }
  }

  @Override
  public String toString() {
    return "Node{" +
        ", depth=" + depth +
        ", value=" + value +
        ", children=" + children +
        '}';
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    Node<?> other = (Node<?>) obj;
    return Objects.equals(id, other.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}