package org.example;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class SortTest {
    private final Random random = new Random(1);

    private void check(int[] input) {
        int[] expected = input.clone();
        Arrays.sort(expected);

        int[] merge = input.clone();
        new MergeSorter(new Metrics()).sort(merge);
        assertArrayEquals(expected, merge);

        int[] quick = input.clone();
        new QuickSorter(new Metrics()).sort(quick);
        assertArrayEquals(expected, quick);
    }

    @Test
    void randomArray() {
        check(random.ints(1000).toArray());
    }

    @Test
    void smallArray() {
        check(random.ints(10).toArray());
    }

    @Test
    void sortedArray() {
        check(IntStream.range(0, 1000).toArray());
    }

    @Test
    void reverseSortedArray() {
        check(IntStream.range(0, 1000).map(i -> 1000 - i).toArray());
    }

    @Test
    void arrayWithDuplicates() {
        check(random.ints(1000, 0, 5).toArray());
    }

    @Test
    void emptyArray() {
        check(new int[0]);
    }

    @Test
    void singleElement() {
        check(new int[]{42});
    }
}