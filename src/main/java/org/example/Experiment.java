package org.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.Random;

public class Experiment {
    private static final int[] SIZES = {1_000, 10_000, 50_000, 100_000, 500_000, 1_000_000};
    private static final String[] TYPES = {"random", "sorted", "reverse", "duplicates"};
    private static final int RUNS = 5;

    private interface ArrayAlgorithm {
        void run(int[] a, Metrics metrics);
    }

    public static void run(String fileName) throws IOException {
        Random random = new Random(1);
        try (PrintWriter out = new PrintWriter(fileName)) {
            print(out, "algorithm,input,n,time_ms,max_depth,comparisons");

            for (int n : SIZES) {
                for (String type : TYPES) {
                    int[] data = makeArray(n, type, random);
                    measure(out, "MergeSort", type, data,
                            (a, m) -> new MergeSorter(m).sort(a));
                    measure(out, "QuickSort", type, data,
                            (a, m) -> new QuickSorter(m).sort(a));
                    measure(out, "DeterministicSelect", type, data,
                            (a, m) -> new DeterministicSelector(m).select(a, a.length / 2));

                    measureClosestPair(out, type, makePoints(n, type, random));
                }
            }
        }
    }

    private static void measure(PrintWriter out, String name, String type, int[] data,
                                ArrayAlgorithm algorithm) {
        long bestTime = Long.MAX_VALUE;
        Metrics metrics = null;
        for (int run = 0; run <= RUNS; run++) {
            int[] copy = data.clone();
            metrics = new Metrics();
            long start = System.nanoTime();
            algorithm.run(copy, metrics);
            long time = System.nanoTime() - start;
            if (run > 0 && time < bestTime) {
                bestTime = time;
            }
        }
        print(out, row(name, type, data.length, bestTime, metrics));
    }

    private static void measureClosestPair(PrintWriter out, String type, Point[] points) {
        long bestTime = Long.MAX_VALUE;
        Metrics metrics = null;
        for (int run = 0; run <= RUNS; run++) {
            metrics = new Metrics();
            long start = System.nanoTime();
            new ClosestPairSolver(metrics).solve(points);
            long time = System.nanoTime() - start;
            if (run > 0 && time < bestTime) {
                bestTime = time;
            }
        }
        print(out, row("ClosestPair", type, points.length, bestTime, metrics));
    }

    private static String row(String name, String type, int n, long nanos, Metrics m) {
        return String.format(Locale.US, "%s,%s,%d,%.3f,%d,%d",
                name, type, n, nanos / 1_000_000.0, m.maxDepth, m.comparisons);
    }

    private static void print(PrintWriter out, String line) {
        System.out.println(line);
        out.println(line);
    }

    private static int[] makeArray(int n, String type, Random random) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            if (type.equals("random")) {
                a[i] = random.nextInt();
            } else if (type.equals("sorted")) {
                a[i] = i;
            } else if (type.equals("reverse")) {
                a[i] = n - i;
            } else {
                a[i] = random.nextInt(10);
            }
        }
        return a;
    }

    private static Point[] makePoints(int n, String type, Random random) {
        Point[] p = new Point[n];
        for (int i = 0; i < n; i++) {
            if (type.equals("random")) {
                p[i] = new Point(random.nextDouble() * 1_000_000, random.nextDouble() * 1_000_000);
            } else if (type.equals("sorted")) {
                p[i] = new Point(i, random.nextDouble() * 1_000_000);
            } else if (type.equals("reverse")) {
                p[i] = new Point(n - i, random.nextDouble() * 1_000_000);
            } else {
                p[i] = new Point(random.nextInt(100), random.nextInt(100));
            }
        }
        return p;
    }
}