package es.zed.library;

import java.util.ArrayList;
import java.util.List;

public class TreeSearch {

  private int depth;
  private List<TreeSearch> childs;

  public TreeSearch(int depth) {
    this.depth = depth;
    this.childs = new ArrayList<>();
  }

  public TreeSearch(int depth, List<TreeSearch> childs) {
    this.depth = depth;
    this.childs = childs != null ? childs : new ArrayList<>();
  }

  public void addChild(TreeSearch child) {
    this.childs.add(child);
  }

  public void generateChildren(int maxDepth) {
    if (this.depth >= maxDepth) {
      return;
    }

    for (int i = 0; i < 4; i++) {
      TreeSearch child = new TreeSearch(this.depth + 1);
      this.childs.add(child);
      child.generateChildren(maxDepth);
    }
  }

  public List<TreeSearch> getChilds() {
    return childs;
  }

  public int getDepth() {
    return depth;
  }

  public TreeSearch findNodeAtDepth(int targetDepth) {
    if (this.depth == targetDepth) {
      return this;
    }

    for (TreeSearch child : childs) {
      TreeSearch result = child.findNodeAtDepth(targetDepth);
      if (result != null) {
        return result;
      }
    }

    return null;
  }
}