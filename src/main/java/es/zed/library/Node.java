package es.zed.library;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Node<T> {

  private int depth;
  private T value;
  private List<Node<T>> children;

  public Node(T value, int depth) {
    this.depth = depth;
    this.value = value;
    this.children = new ArrayList<>();
  }

  public T getValue() {
    return value;
  }

  public void setValue(T value) {
    this.value = value;
  }

  public List<Node<T>> getChildren() {
    return children;
  }

  public Integer getNumberOfChildren() {
    return children.size();
  }

  public Node<T> findNode(Node<T> target) {
    if (this.equals(target)) {
      return this; // Si el nodo actual es el objetivo, lo retornamos.
    }

    for (Node<T> child : this.children) {
      Node<T> result = child.findNode(target); // Llamada recursiva en los hijos.
      if (result != null) {
        return result; // Si encontramos el nodo en algún hijo, lo retornamos.
      }
    }

    return null;
  }


  public void addChild(Node<T> child) {
    this.children.add(child);
  }

  public void removeChild(Node<T> child) {
    this.children.remove(child);
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
    return depth == node.depth && Objects.equals(value, node.value) && Objects.equals(children,
        node.children);
  }

  @Override
  public int hashCode() {
    return Objects.hash(depth, value, children);
  }
}