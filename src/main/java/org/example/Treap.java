package org.example;

import lombok.Getter;

import java.lang.reflect.Array;
import java.util.Random;

/**
 * Теория<br/>
 * <a href="http://e-maxx.ru/algo/treap">http://e-maxx.ru/algo/treap</a><br/>
 * <a href="https://www.geeksforgeeks.org/treap-a-randomized-binary-search-tree/">https://www.geeksforgeeks.org/treap-a-randomized-binary-search-tree/</a><br/>
 * <a href="https://www.geeksforgeeks.org/implementation-of-search-insert-and-delete-in-treap/">https://www.geeksforgeeks.org/implementation-of-search-insert-and-delete-in-treap/</a><br/>
 * <a href="http://faculty.washington.edu/aragon/pubs/rst89.pdf">http://faculty.washington.edu/aragon/pubs/rst89.pdf</a><br/>
 * <a href="https://habr.com/ru/articles/101818/">https://habr.com/ru/articles/101818/</a><br/>
 * <a href="https://habr.com/ru/articles/102006/">https://habr.com/ru/articles/102006/</a><br/>
 * Примеение в linux kernel<br/>
 * <a href="https://www.kernel.org/doc/mirror/ols2005v2.pdf">https://www.kernel.org/doc/mirror/ols2005v2.pdf</a>
 */
public class Treap<K extends Comparable<K>> {

    Node<K> root;

    public Treap() {}


    public void add(K key, int value) {
        root = insert(root, key, value);
    }

    public void remove(K key) {
        root = deleteNode(root, key);
    }

    public boolean search(K key) {
        return searchNode(root, key) != null;
    }

    public Node<K>[] split(K key) {
        return root.split(key);
    }

    public Statistic getStats(K keyMinMinusOne, K keyMax) {
        // [x < kMin, kMin <= x]
        Node<K>[] tmp1 = split(keyMinMinusOne);
        if(tmp1[1] == null) return null;
        // [kMin <= x < kMax, kMax <= x]
        Node<K>[] tmp2 = tmp1[1].split(keyMax);
        System.out.println("Found interval");
        return tmp2[0].statistic;
    }

    public K getMinNode(K keyMinMinusOne, K keyMax) {
        // [x < kMin, kMin <= x]
        Node<K>[] tmp1 = split(keyMinMinusOne);
        if(tmp1[1] == null) return null;
        // [kMin <= x < kMax, kMax <= x]
        Node<K>[] tmp2 = tmp1[1].split(keyMax);
        if(tmp2[0] == null) return null;
        return tmp2[0].key;
    }

    private static int sizeOf(Node<?> node) {
        return node != null ? node.size : 0;
    }

    private Node<K> searchNode(Node<K> cur, K key) {
        if (cur == null || key.compareTo(cur.key) == 0) {
            return cur;
        }
        cur.pushPromise();
        if (key.compareTo(cur.key) > 0) {
            return searchNode(cur.right, key);
        }
        return searchNode(cur.left, key);
    }

    private Node<K> deleteNode(Node<K> cur, K key) {
        if (cur == null)
            return cur;
        cur.pushPromise();
        if (key.compareTo(cur.key) < 0)
            cur.setLeft(deleteNode(cur.left, key));
        else if (key.compareTo(cur.key) > 0)
            cur.setRight(deleteNode(cur.right, key));


            // IF KEY IS AT ROOT

            // If left is NULL
        else if (cur.left == null) {
            cur = cur.right;  // Make right child as root
        }
        // If Right is NULL
        else if (cur.right == null) {
            cur = cur.left;  // Make left child as root
        }
        // If key is at root and both left and right are not NULL
        else if (cur.left.priority < cur.right.priority) {
            cur = leftRotation(cur);
            cur.setLeft(deleteNode(cur.left, key));
        } else {
            cur = rightRotation(cur);
            cur.setRight(deleteNode(cur.right, key));
        }

        return cur;
    }

    private Node<K> insert(Node<K> cur, K key, int value) {
        if (cur == null) {
            return new Node<>(key, value);
        }
        cur.pushPromise();
        if (key.compareTo(cur.key) > 0) {
            cur.setRight(insert(cur.right, key, value));
            if (cur.right.priority < cur.priority) {
                cur = leftRotation(cur);
            }

        } else {
            cur.setLeft(insert(cur.left, key, value));
            if (cur.left.priority < cur.priority) {
                cur = rightRotation(cur);
            }

        }
        return cur;
    }

    /* T1, T2 and T3 are subtrees of the tree rooted with y
  (on left side) or x (on right side)
                y                               x
               / \     Right Rotation          /  \
              x   T3   – – – – – – – >        T1   y
             / \       < - - - - - - -            / \
            T1  T2     Left Rotation            T2  T3 */

    private Node<K> leftRotation(Node<K> x) {
        x.pushPromise();
        Node<K> y = x.right;
        y.pushPromise();
        Node<K> T2 = y.left;

        x.setRight(T2);
        y.setLeft(x);

        return y;
    }

    private Node<K> rightRotation(Node<K> y) {
        y.pushPromise();
        Node<K> x = y.left;
        x.pushPromise();
        Node<K> T2 = x.right;

        y.setLeft(T2);
        x.setRight(y);

        return x;
    }

    public static class Statistic {
        int minValue;
        int sumValue;
        int maxValue;

        @Override
        public String toString() {
            return "Statistic{" +
                    "minValue=" + minValue +
                    ", sumValue=" + sumValue +
                    ", maxValue=" + maxValue +
                    '}';
        }
    }

    @Getter
    public static class Node<K extends Comparable<K>> {
        static Random RND = new Random();
        K key;
        int priority;
        Node<K> left;
        Node<K> right;
        int size;
        int value;
        int addPromise;
        Statistic statistic = new Statistic();

        public Node(K key, int value) {
            this(key, value, RND.nextInt());
        }

        public Node(K key, int value, int priority) {
            this(key, priority, value, null, null);
        }

        public Node(K key, int priority, int value, Node<K> left, Node<K> right) {
            this.key = key;
            this.priority = priority;
            this.left = left;
            this.right = right;
            this.value = value;
            recalculate();
        }

        public void recalculate() {
            size = sizeOf(left) + sizeOf(right) + 1;
            setMinValue(minOf(minOf(minValueOf(left), minValueOf(right)), value));
            setMaxValue(maxOf(maxOf(maxValueOf(left), maxValueOf(right)), value));
            setSumValue(sumValueOf(left) + sumValueOf(right) + value);
        }

        private static Integer valueOf(Node<?> node) {
            return node != null ? node.value + node.addPromise : null;
        }

        private static Integer minValueOf(Node<?> node) {
            return node != null ? node.statistic.minValue : null;
        }

        private static Integer maxValueOf(Node<?> node) {
            return node != null ? node.statistic.maxValue : null;
        }

        private static Integer sumValueOf(Node<?> node) {
            return node != null ? node.statistic.sumValue : 0;
        }

        private static int minOf(Integer a, Integer b) {
            if (a != null && b != null) {
                return Math.min(a, b);
            }
            if (a != null) {
                return a;
            }
            if (b != null) {
                return b;
            }
            return Integer.MAX_VALUE;
        }

        private static int maxOf(Integer a, Integer b) {
            if (a != null && b != null) {
                return Math.max(a, b);
            }
            if (a != null) {
                return a;
            }
            if (b != null) {
                return b;
            }
            return Integer.MIN_VALUE;
        }

        public void setLeft(Node<K> left) {
            this.left = left;
            recalculate();
        }

        public void setRight(Node<K> right) {
            this.right = right;

            recalculate();
        }

        public void setMinValue(int minValue) {
            statistic.minValue = minValue + addPromise;
        }

        public void setMaxValue(int maxValue) {
            statistic.maxValue = maxValue + addPromise;
        }

        public void setSumValue(int sumValue) {
            statistic.sumValue = sumValue + addPromise * size;
        }

        @SuppressWarnings("unchecked")
        public Node<K>[] split(K key) {
            Node<K> tmp = null;

            Node<K>[] res = (Node<K>[]) Array.newInstance(this.getClass(), 2);

            if (this.key.compareTo(key) <= 0) {
                if (this.right == null) {
                    res[1] = null;
                } else {
                    Node<K>[] rightSplit = this.right.split(key);
                    res[1] = rightSplit[1];
                    tmp = rightSplit[0];
                }
                res[0] = new Node<>(this.key, priority, value, this.left, tmp);
            } else {
                if (left == null) {
                    res[0] = null;
                } else {
                    Node<K>[] leftSplit = this.left.split(key);
                    res[0] = leftSplit[0];
                    tmp = leftSplit[1];
                }
                res[1] = new Node<>(this.key, priority, value, tmp, this.right);
            }
            return res;
        }

        @Override
        public String toString() {
            return String.format("(%s,%d,%d[%d]m%d s%d M%d +%d)", key, priority, valueOf(this), size, statistic.minValue, statistic.sumValue, statistic.maxValue, addPromise);
        }

        public void addToPromiseAdd(int add) {
            addPromise += add;
            recalculate();
        }

        public void pushPromise() {
            if (addPromise == 0) {
                return;
            }
            if (left != null) {
                left.addToPromiseAdd(addPromise);
            }
            if (right != null) {
                right.addToPromiseAdd(addPromise);
            }
            value += addPromise;
            addToPromiseAdd(-addPromise); //reset to zero and recalculate
        }
    }
}
