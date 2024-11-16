package org.example;

public class Treap {
    private Node root;

    /**
     * Получить размер поддерева
     * @param node узел
     * @return размер
     */
    private int getSize(Node node) {
        return node == null ? 0 : node.subtreeSize;
    }

    /**
     * Обновим размер поддерева
     * @param node узел
     */
    private void updateSize(Node node) {
        if (node != null) {
            node.subtreeSize = node.count + getSize(node.left) + getSize(node.right);
        }
    }

    /**
     * Разделим маршрут на два поддерева: левое с ключами < key, правое с ключами >= key
     * @param node узел
     * @param key ключ
     * @return ветки
     */
    private Node[] split(Node node, double key) {
        if (node == null) return new Node[]{null, null};
        if (key > node.key) {
            Node[] splitRight = split(node.right, key);
            node.right = splitRight[0];
            updateSize(node);
            return new Node[]{node, splitRight[1]};
        } else {
            Node[] splitLeft = split(node.left, key);
            node.left = splitLeft[1];
            updateSize(node);
            return new Node[]{splitLeft[0], node};
        }
    }

    /**
     * Объединить два поддерева
     * @param left левая ветка
     * @param right правая ветка
     * @return узел
     */
    private Node merge(Node left, Node right) {
        if (left == null) return right;
        if (right == null) return left;

        if (left.priority > right.priority) {
            left.right = merge(left.right, right);
            updateSize(left);
            return left;
        } else {
            right.left = merge(left, right.left);
            updateSize(right);
            return right;
        }
    }

    /**
     * Вставка ключа в Treap
     * @param key ключ
     */
    public void insert(double key) {
        Node[] splitNodes = split(root, key);
        Node middle = splitNodes[1];

        if (middle != null && middle.key == key) {
            middle.count++;
            updateSize(middle);
            root = merge(splitNodes[0], middle);
        } else {
            root = merge(merge(splitNodes[0], new Node(key)), splitNodes[1]);
        }
    }

    /**
     * Удаление ключа key из Treap
     * @param key ключ
     */
    public void delete(double key) {
        Node[] splitNodes = split(root, key);
        Node middle = split(splitNodes[1], key + 1)[0];

        if (middle != null && middle.key == key) {
            if (middle.count > 1) {
                middle.count--;
                updateSize(middle);
                root = merge(splitNodes[0], merge(middle, splitNodes[1]));
            } else {
                root = merge(splitNodes[0], splitNodes[1]);
            }
        }
    }

    /**
     * Найдем и удалим первые K элементов >= минимальной цены minPrice
     * @param minPrice минимальная цена minPrice
     * @param k количество болванок
     * @return прибыль сделки
     */
    public int sell(double minPrice, int k) {
        int sold = 0;
        Node[] splitNodes = split(root, minPrice);

        while (splitNodes[1] != null && k > 0) {
            Node maxNode = getMinNode(splitNodes[1]);

            if (maxNode.count <= k) {
                k -= maxNode.count;
                sold += maxNode.count;
                splitNodes[1] = deleteMin(splitNodes[1]);
            } else {
                sold += k;
                maxNode.count -= k;
                k = 0;
                updateSize(maxNode);
            }
        }

        root = merge(splitNodes[0], splitNodes[1]);
        return sold;
    }

    /**
     * Получим минимальный узел в Treap
     * @param node узел
     * @return минимальный узел в Treap
     */
    private Node getMinNode(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    /**
     * Удалим минимальный узел из Treap
     * @param node узел
     * @return удвленный минимальный узел
     */
    private Node deleteMin(Node node) {
        if (node.left == null) return node.right;
        node.left = deleteMin(node.left);
        updateSize(node);
        return node;
    }

    // Find the next element >= key
    public int next(int key) {
        Node[] splitNodes = split(root, key);
        Node current = splitNodes[1]; // Right subtree contains all >= key
        int result = -1;

        if (current != null) {
            while (current.left != null) {
                current = current.left;
            }
            result = (int) current.key;
        }

        root = merge(splitNodes[0], splitNodes[1]); // Restore the tree
        return result;
    }
}
