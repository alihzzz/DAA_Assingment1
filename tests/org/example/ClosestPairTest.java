package org.example;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClosestPairTest {
    private static final Random random = new Random(1);

    private double bruteForce(Point[] points) {
        double best = Double.MAX_VALUE;
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                best = Math.min(best, points[i].distance(points[j]));
            }
        }
        return best;
    }

    private void check(Point[] points) {
        double expected = bruteForce(points);
        double actual = new ClosestPairSolver(new Metrics()).solve(points);
        assertEquals(expected, actual, 1e-9);
    }

    @RepeatedTest(20)
    void randomPoints() {
        int n = 2 + random.nextInt(1999);
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            points[i] = new Point(random.nextDouble() * 1000, random.nextDouble() * 1000);
        }
        check(points);
    }

    @Test
    void pointsOnOneVerticalLine() {
        Point[] points = new Point[300];
        for (int i = 0; i < points.length; i++) {
            points[i] = new Point(5, random.nextDouble() * 1000);
        }
        check(points);
    }

    @Test
    void duplicatePoints() {
        Point[] points = new Point[500];
        for (int i = 0; i < points.length; i++) {
            points[i] = new Point(random.nextInt(10), random.nextInt(10));
        }
        check(points);
    }

    @Test
    void twoPoints() {
        Point[] points = {new Point(0, 0), new Point(3, 4)};
        assertEquals(5.0, new ClosestPairSolver(new Metrics()).solve(points), 1e-9);
    }
}