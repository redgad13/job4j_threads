package ru.job4j.pools;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

class ParallelMergeSortTest {

    @Test
    void sort() {
        int[] array = new int[]{2, 4, 5, 34, 23, 41235, 2, -8574, -3, 0, 77, 234, 1, 11, 3124};
        ParallelMergeSort pms = new ParallelMergeSort(array, 0, array.length - 1);
        int[] rsl = pms.compute();
        System.out.println(Arrays.toString(rsl));
    }
}