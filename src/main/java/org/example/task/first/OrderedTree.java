package org.example.task.first;

public class OrderedTree {
    private TreeNode root;

    public OrderedTree() {
        root = null;
    }

    // Метод для вставки нового узла
    public void insert(double value) {
        TreeNode newNode = new TreeNode(value);
        if (root == null) {
            root = newNode;
        } else {
            insert(newNode, root);
        }
    }

    private void insert(TreeNode newNode, TreeNode current) {
        if (newNode.value < current.value) {
            if (current.left == null) {
                current.left = newNode;
                newNode.parent = current;
            } else {
                insert(newNode, current.left);
            }
        } else if (current.right == null) {
            current.right = newNode;
            newNode.parent = current;
        } else {
            insert(newNode, current.right);
        }
    }

    // Метод для поиска узла с заданным значением
    public TreeNode search(double value) {
        return search(root, value);
    }

    private TreeNode search(TreeNode current, double value) {
        if (current == null || current.value == value) {
            return current;
        }
        if (value < current.value) {
            return search(current.left, value);
        } else {
            return search(current.right, value);
        }
    }

    // Метод для удаления узла с заданным ключом
    public void delete(double key) {
        TreeNode nodeToDelete = search(root, key);
        if (nodeToDelete != null) {
            removeNode(nodeToDelete);
        }
    }

    // Метод для удаления узла
    private void removeNode(TreeNode node) {
        if (node.left == null && node.right == null) { // Листовой узел
            if (node == root) {
                root = null;
            } else {
                if (node.isLeftChild()) {
                    node.getParent().setLeft(null);
                } else {
                    node.getParent().setRight(null);
                }
            }
        } else if (node.left != null && node.right != null) {
            // Погружаем узел вниз, меняя местами с наименьшим правым потомком
            TreeNode successor = findMinNode(node.right);
            swapNodes(node, successor);
            removeNode(successor);
        } else { // Один ребенок
            TreeNode child = (node.left == null) ? node.right : node.left;
            if (child != null) child.setParent(node.getParent());
            if (node == root) {
                root = child;
            } else if (node.isLeftChild()) {
                node.getParent().setLeft(child);
            } else {
                node.getParent().setRight(child);
            }
        }
    }

    // Метод для поиска минимального узла в правом поддереве
    private TreeNode findMinNode(TreeNode current) {
        while (current.getLeft() != null) {
            current = current.getLeft();
        }
        return current;
    }

    /**
     * Метод для поиска узел с максимальным значением в Treap
     *
     * @param node узел
     * @return минимальный узел в Treap
     */
    private TreeNode findMaxNode(TreeNode node) {
        while (node.right != null) {
            node = node.right;
        }
        return node;
    }

    // Метод для обмена ключей между двумя узлами
    private void swapNodes(TreeNode a, TreeNode b) {
        double tempKey = a.value;
        a.setValue(b.value);
        b.setValue(tempKey);
    }

    public int sell(double minPrice, int k) {
        int sold = 0;
        while ( k > 0 ) {
            TreeNode maxNode = findMaxNode(root);
            if (minPrice < maxNode.value) {
                sold += 1;
                k -= 1;
                insert(maxNode.value - minPrice);
                removeNode(maxNode);
            } else if (minPrice == maxNode.value) {
                removeNode(maxNode);
                sold += 1;
                k -= 1;
            } else {
                k = 0;
            }
        }
        return sold;
    }






}
