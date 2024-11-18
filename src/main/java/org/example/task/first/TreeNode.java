package org.example.task.first;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TreeNode {
    double value;
    TreeNode left;
    TreeNode right;
    TreeNode parent;

    public TreeNode(double value) {
        this.value = value;
    }

    public boolean isLeftChild() {
        return parent != null && parent.getLeft() == this;
    }
}
