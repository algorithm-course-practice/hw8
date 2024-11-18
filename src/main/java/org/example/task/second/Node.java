package org.example.task.second;

import java.util.Random;

public class Node {
    int key, priority;
    Node left, right;

    Node(int key) {
        this.key = key;
        this.priority = new Random().nextInt();
    }
}
