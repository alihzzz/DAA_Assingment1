package org.example;

import java.util.Arrays;
import java.util.Comparator;

public class ClosestPairSolver {
    private final Metrics metrics;
    private Point[] p;
    private Point[] temp;
    private double best;

    public ClosestPairSolver(Metrics metrics) {
        this.metrics = metrics;
    }

    public double solve(Point[] points) {
        p = points.clone();
        Arrays.sort(p, Comparator.comparingDouble(pt -> pt.x));
        temp = new Point[p.length];
        best = Double.MAX_VALUE;
        solve(0, p.length - 1);
        return best;
    }

    private void solve(int lo, int hi) {
        metrics.enter();
        if (lo < hi) {
            int mid = (lo + hi) / 2;
            double midX = p[mid].x;

            solve(lo, mid);
            solve(mid + 1, hi);
            mergeByY(lo, mid, hi);

            int size = 0;
            for (int i = lo; i <= hi; i++) {
                if (Math.abs(p[i].x - midX) < best) {
                    temp[size++] = p[i];
                }
            }

            for (int i = 0; i < size; i++) {
                for (int j = i + 1; j < size && temp[j].y - temp[i].y < best; j++) {
                    metrics.comparisons++;
                    best = Math.min(best, temp[i].distance(temp[j]));
                }
            }
        }
        metrics.exit();
    }

    private void mergeByY(int lo, int mid, int hi) {
        System.arraycopy(p, lo, temp, lo, hi - lo + 1);
        int i = lo;
        int j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) {
                p[k] = temp[j++];
            } else if (j > hi) {
                p[k] = temp[i++];
            } else {
                metrics.comparisons++;
                if (temp[j].y < temp[i].y) {
                    p[k] = temp[j++];
                } else {
                    p[k] = temp[i++];
                }
            }
        }
    }
}