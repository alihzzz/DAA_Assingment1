package org.example;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SelectTest {
    private static final Random random = new Random(1);

    @RepeatedTest(100)
    void randomArray() {
        int n = 1 + random.nextInt(500);
        int[] a = random.ints(n, 0, 1000).toArray();
        int k = random.nextInt(n);

        int[] sorted = a.clone();
        Arrays.sort(sorted);

        int result = new DeterministicSelector(new Metrics()).select(a, k);
        assertEquals(sorted[k], result);
    }

    @Test
    void manyDuplicates() {
        int[] a = random.ints(200, 0, 5).toArray();
        int[] sorted = a.clone();
        Arrays.sort(sorted);

        for (int k = 0; k < a.length; k++) {
            int result = new DeterministicSelector(new Metrics()).select(a.clone(), k);
            assertEquals(sorted[k], result);
        }
    }

    @Test
    void singleElement() {
        assertEquals(5, new DeterministicSelector(new Metrics()).select(new int[]{5}, 0));
    }
}