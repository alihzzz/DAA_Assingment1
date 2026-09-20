package org.example;

import java.util.Random;

public class QuickSorter {
    private final Metrics metrics;
    private final Random random = new Random();

    public QuickSorter(Metrics metrics) {
        this.metrics = metrics;
    }

    public void sort(int[] a) {
        sort(a, 0, a.length - 1);
    }

    private void sort(int[] a, int lo, int hi) {
        metrics.enter();
        while (lo < hi) {
            int pivot = a[lo + random.nextInt(hi - lo + 1)];

            int lt = lo;
            int i = lo;
            int gt = hi;
            while (i <= gt) {
                metrics.comparisons++;
                if (a[i] < pivot) {
                    swap(a, lt, i);
                    lt++;
                    i++;
                } else if (a[i] > pivot) {
                    swap(a, i, gt);
                    gt--;
                } else {
                    i++;
                }
            }

            if (lt - lo < hi - gt) {
                sort(a, lo, lt - 1);
                lo = gt + 1;
            } else {
                sort(a, gt + 1, hi);
                hi = lt - 1;
            }
        }
        metrics.exit();
    }

    private void swap(int[] a, int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }
}