package org.example;

import java.util.Random;

public class Node {
    double key;
    int priority, count, subtreeSize;
    Node left, right;

    Node(double key) {
        this.key = key;
        this.priority = new Random().nextInt();
        this.count = 1;
        this.subtreeSize = 1;
    }
}
