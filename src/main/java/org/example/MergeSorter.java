package org.example;

public class MergeSorter {
    private static final int CUTOFF = 16;

    private final Metrics metrics;
    private int[] buffer;

    public MergeSorter(Metrics metrics) {
        this.metrics = metrics;
    }

    public void sort(int[] a) {
        buffer = new int[a.length];
        sort(a, 0, a.length - 1);
    }

    private void sort(int[] a, int lo, int hi) {
        metrics.enter();
        if (hi - lo + 1 <= CUTOFF) {
            insertionSort(a, lo, hi);
        } else {
            int mid = (lo + hi) / 2;
            sort(a, lo, mid);
            sort(a, mid + 1, hi);
            merge(a, lo, mid, hi);
        }
        metrics.exit();
    }

    private void merge(int[] a, int lo, int mid, int hi) {
        System.arraycopy(a, lo, buffer, lo, hi - lo + 1);
        int i = lo;
        int j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) {
                a[k] = buffer[j++];
            } else if (j > hi) {
                a[k] = buffer[i++];
            } else {
                metrics.comparisons++;
                if (buffer[j] < buffer[i]) {
                    a[k] = buffer[j++];
                } else {
                    a[k] = buffer[i++];
                }
            }
        }
    }

    private void insertionSort(int[] a, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++) {
            int key = a[i];
            int j = i - 1;
            while (j >= lo) {
                metrics.comparisons++;
                if (a[j] > key) {
                    a[j + 1] = a[j];
                    j--;
                } else {
                    break;
                }
            }
            a[j + 1] = key;
        }
    }
}