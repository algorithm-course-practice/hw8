package org.example.task.second;

public class Treap {
    private Node root;


    private Node[] split(Node node, int key) {
        if (node == null) return new Node[]{null, null};
        if (key > node.key) {
            Node[] splitRight = split(node.right, key);
            node.right = splitRight[0];
            return new Node[]{node, splitRight[1]};
        } else {
            Node[] splitLeft = split(node.left, key);
            node.left = splitLeft[1];
            return new Node[]{splitLeft[0], node};
        }
    }


    private Node merge(Node left, Node right) {
        if (left == null) return right;
        if (right == null) return left;

        if (left.priority > right.priority) {
            left.right = merge(left.right, right);
            return left;
        } else {
            right.left = merge(left, right.left);
            return right;
        }
    }


    public void insert(int key) {
        if (exists(key)) return; // Avoid duplicates
        Node[] splitNodes = split(root, key);
        root = merge(merge(splitNodes[0], new Node(key)), splitNodes[1]);
    }


    public boolean exists(int key) {
        Node current = root;
        while (current != null) {
            if (current.key == key) return true;
            current = (key < current.key) ? current.left : current.right;
        }
        return false;
    }

    /**
     * Найдите значение следующий элемент >= ключ
     * @param key ключ
     * @return
     */
    public int next(int key) {
        Node[] splitNodes = split(root, key);
        Node current = splitNodes[1]; // Right subtree contains all >= key
        int result = -1;

        if (current != null) {
            while (current.left != null) {
                current = current.left;
            }
            result = current.key;
        }

        root = merge(splitNodes[0], splitNodes[1]); // Restore the tree
        return result;
    }
}



