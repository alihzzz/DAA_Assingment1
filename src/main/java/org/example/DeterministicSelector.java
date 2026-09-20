package org.example;

public class DeterministicSelector {
    private final Metrics metrics;

    public DeterministicSelector(Metrics metrics) {
        this.metrics = metrics;
    }

    public int select(int[] a, int k) {
        return select(a, 0, a.length - 1, k);
    }

    private int select(int[] a, int lo, int hi, int k) {
        metrics.enter();
        int result;
        if (hi - lo + 1 <= 5) {
            insertionSort(a, lo, hi);
            result = a[k];
        } else {
            int pivot = medianOfMedians(a, lo, hi);

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

            if (k < lt) {
                result = select(a, lo, lt - 1, k);
            } else if (k > gt) {
                result = select(a, gt + 1, hi, k);
            } else {
                result = a[k];
            }
        }
        metrics.exit();
        return result;
    }

    private int medianOfMedians(int[] a, int lo, int hi) {
        int n = hi - lo + 1;
        int groups = (n + 4) / 5;
        int[] medians = new int[groups];
        for (int g = 0; g < groups; g++) {
            int start = lo + g * 5;
            int end = Math.min(start + 4, hi);
            insertionSort(a, start, end);
            medians[g] = a[(start + end) / 2];
        }
        return select(medians, 0, groups - 1, groups / 2);
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

    private void swap(int[] a, int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }
}