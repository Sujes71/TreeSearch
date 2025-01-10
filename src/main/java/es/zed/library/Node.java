package es.zed.library;

import java.util.ArrayList;
import java.util.List;

public class Node<T> {

  private int depth;
  private T value;
  private List<Node<T>> children;

  public Node(int depth, T value) {
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
}