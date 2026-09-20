# Assignment 1: Divide-and-Conquer Algorithm Analysis

## A. Project Overview

The goal is to implement four divide-and-conquer algorithms in Java, measure them on different inputs and compare the results with the theory.

Algorithms: **MergeSort**, **QuickSort**, **Deterministic Select** (median of medians) and **Closest Pair of Points**.

## B. Algorithm Analysis

**MergeSort.** The array is split in half until a part has at most 16 elements (then insertion sort). Halves are merged through one reusable buffer.
Recurrence: T(n) = 2T(n/2) + Θ(n). Master Theorem, case 2 → **Θ(n log n)**. Space: O(n) for the buffer.

**QuickSort.** Random pivot, in-place partition into three parts (smaller, equal, greater). The algorithm recurses into the smaller part and loops over the larger one.
Recurrence: T(n) = T(k) + T(n−k−1) + Θ(n); for a balanced split T(n) = 2T(n/2) + Θ(n) → expected **Θ(n log n)**. Worst case T(n) = T(n−1) + Θ(n) = O(n²), but it is very unlikely with a random pivot. Space: O(log n).

**Deterministic Select.** Split into groups of 5, take the median of each group, find the median of these medians recursively (this is the pivot), partition in place and continue only in the part that contains k.
Recurrence: T(n) ≤ T(n/5) + T(7n/10) + Θ(n). By Akra–Bazzi intuition, 1/5 + 7/10 = 0.9 < 1, so the exponent p is less than 1 and T(n) = **Θ(n)**. Space: O(n) (array of medians).

**Closest Pair.** Sort points by x. Recursively solve both halves and merge them by y. Build the strip of points with |x − dividing line| < best (already sorted by y) and compare each point only with the next ones whose y-difference is less than best.
Recurrence: T(n) = 2T(n/2) + Θ(n) (merge + strip). Master Theorem, case 2 → **Θ(n log n)**. Space: O(n).

## C. Experimental Results

- Sizes: 1 000 to 1 000 000. Input types: `random`, `sorted`, `reverse`, `duplicates` (10 different values; for points: many equal points). Select looks for the median.
- Time is measured with `System.nanoTime()`: one warm-up run, then the best of 5 runs. Recursion depth is the maximum number of nested calls. The additional metric is the number of comparisons (see `results/results.csv`).
- Machine: MacBook Air (Apple Silicon), OpenJDK 25.0.1.

### Execution time (ms)

| algorithm           | input      |   1 000 |   10 000 |   50 000 |   100 000 |   500 000 |   1 000 000 |
|:--------------------|:-----------|--------:|---------:|---------:|----------:|----------:|------------:|
| MergeSort           | random     |   0.057 |    0.895 |    3.193 |     6.714 |    37.837 |      81.609 |
| MergeSort           | sorted     |   0.025 |    0.459 |    0.923 |     1.836 |    10.562 |      23.229 |
| MergeSort           | reverse    |   0.043 |    1.201 |    1.229 |     2.357 |    15.055 |      29.539 |
| MergeSort           | duplicates |   0.041 |    0.339 |    1.695 |     3.522 |    18.849 |      40.189 |
| QuickSort           | random     |   0.104 |    0.621 |    3.539 |     6.996 |    37.639 |      79.384 |
| QuickSort           | sorted     |   0.031 |    0.334 |    1.756 |     3.325 |    18.241 |      41.790 |
| QuickSort           | reverse    |   0.029 |    0.321 |    1.720 |     3.327 |    18.183 |      38.606 |
| QuickSort           | duplicates |   0.012 |    0.106 |    0.509 |     0.998 |     4.989 |       9.866 |
| DeterministicSelect | random     |   0.062 |    0.379 |    1.329 |     2.365 |    12.058 |      24.142 |
| DeterministicSelect | sorted     |   0.100 |    0.086 |    0.435 |     0.826 |     4.171 |       8.438 |
| DeterministicSelect | reverse    |   0.164 |    0.101 |    0.505 |     0.967 |     4.853 |       9.850 |
| DeterministicSelect | duplicates |   0.067 |    0.108 |    0.505 |     1.001 |     5.003 |      10.393 |
| ClosestPair         | random     |   0.500 |    3.247 |   27.370 |    29.133 |   177.734 |     426.119 |
| ClosestPair         | sorted     |   0.162 |    1.356 |    7.626 |    14.852 |    87.635 |     198.635 |
| ClosestPair         | reverse    |   0.155 |    1.474 |    7.172 |    15.637 |    89.399 |     204.484 |
| ClosestPair         | duplicates |   0.361 |    3.343 |    9.432 |    18.167 |   101.632 |     251.273 |

### Max recursion depth

| algorithm           | input      |   1 000 |   10 000 |   50 000 |   100 000 |   500 000 |   1 000 000 |
|:--------------------|:-----------|--------:|---------:|---------:|----------:|----------:|------------:|
| MergeSort           | random     |       7 |       11 |       13 |        14 |        16 |          17 |
| MergeSort           | sorted     |       7 |       11 |       13 |        14 |        16 |          17 |
| MergeSort           | reverse    |       7 |       11 |       13 |        14 |        16 |          17 |
| MergeSort           | duplicates |       7 |       11 |       13 |        14 |        16 |          17 |
| QuickSort           | random     |       6 |        9 |       11 |        12 |        14 |          14 |
| QuickSort           | sorted     |       7 |        9 |       11 |        12 |        13 |          13 |
| QuickSort           | reverse    |       8 |        9 |       11 |        12 |        13 |          14 |
| QuickSort           | duplicates |       3 |        3 |        3 |         3 |         3 |           3 |
| DeterministicSelect | random     |       9 |       11 |       15 |        16 |        18 |          19 |
| DeterministicSelect | sorted     |       8 |       12 |       14 |        15 |        17 |          18 |
| DeterministicSelect | reverse    |       8 |       12 |       14 |        15 |        17 |          18 |
| DeterministicSelect | duplicates |       5 |        7 |        8 |         8 |         9 |           9 |
| ClosestPair         | random     |      11 |       15 |       17 |        18 |        20 |          21 |
| ClosestPair         | sorted     |      11 |       15 |       17 |        18 |        20 |          21 |
| ClosestPair         | reverse    |      11 |       15 |       17 |        18 |        20 |          21 |
| ClosestPair         | duplicates |      11 |       15 |       17 |        18 |        20 |          21 |


## D. Discussion

**Do the results match the theory?** Yes. When n grows 10 times (100 000 → 1 000 000, random input), time grows 12.2× for MergeSort (theory 12.0×), 11.4× for QuickSort, 10.2× for Select (theory 10×) and 14.6× for Closest Pair (theory 12×). Comparisons divided by n·log₂n stay almost constant, and Select needs about 8 comparisons per element for every n. Recursion depth grows like log n (17, 14, 19 and 21 for n = 1 000 000).

**How does input structure affect performance?** MergeSort is faster on sorted data (23 ms vs 82 ms on random) because merges need fewer comparisons. QuickSort has no O(n²) problem on sorted or reverse input thanks to the random pivot (about 40 ms vs 79 ms on random) and is fastest on duplicates (10 ms, depth 3) thanks to the three-way partition. Select is faster on structured input (8–10 ms vs 24 ms). Closest Pair is about 2× faster on sorted points, most likely because `Arrays.sort` by x is fast on sorted data.

**Why does smaller-first recursion help QuickSort?** The part we recurse into has at most half of the elements, so the depth is at most log₂n. The larger part is processed by a loop and needs no new stack frame. Without this, a bad pivot could give depth close to n and a `StackOverflowError`. We measured depth 14 for n = 1 000 000.

**Why does Median-of-Medians guarantee O(n)?** The pivot has at least 3n/10 elements on each side, so each side has at most 7n/10 elements. Then T(n) ≤ T(n/5) + T(7n/10) + cn. Since 1/5 + 7/10 < 1, the work shrinks on every level: by induction T(n) ≤ 10cn.

**Why is divide-and-conquer Closest Pair faster than O(n²)?** Brute force checks n(n−1)/2 pairs (about 5·10¹¹ for n = 1 000 000). Here the problem is halved and combining costs O(n), because a strip point has to be compared with only a constant number of neighbors. We measured 18.7 million comparisons for 1 000 000 points, about 27 000 times fewer.

**What practical factors affect performance?** JVM warm-up (JIT): small inputs are noisy, e.g. Select on sorted input took 0.100 ms for n = 1 000 but 0.086 ms for n = 10 000. Garbage collection (arrays and `Point` objects), CPU cache (an `int[]` is contiguous, `Point` objects are not, which is probably why Closest Pair is the slowest), branch prediction on sorted data, and other programs running on the computer.

## E. Reflection

I learned that small implementation details decide whether the theory really applies: the three-way partition keeps QuickSort fast on duplicates, merging by y keeps Closest Pair at n log n, and recursing into the smaller part keeps the stack small. The recurrence for Median of medians also became clear only after I wrote the 7n/10 argument.

The main challenges were noisy timings for small inputs (solved with a warm-up run and the best of 5 runs), choosing the comparison metric for Closest Pair (strip checks alone are very few, so I also count the merge comparisons) and counting recursion depth in QuickSort, where the larger part is a loop and not a recursive call.

## F. Screenshots

All screenshots are stored in src/docs
