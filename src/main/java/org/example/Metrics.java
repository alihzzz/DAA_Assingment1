package org.example;

public class Metrics {
    public long comparisons = 0;
    public int maxDepth = 0;
    private int depth = 0;

    public void enter() {
        depth++;
        if (depth > maxDepth) {
            maxDepth = depth;
        }
    }

    public void exit() {
        depth--;
    }
}