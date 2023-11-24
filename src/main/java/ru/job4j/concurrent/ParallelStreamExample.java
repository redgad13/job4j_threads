package ru.job4j.concurrent;

import java.util.stream.IntStream;

public class ParallelStreamExample {
    public static void main(String[] args) {
        IntStream parallel = IntStream.range(1, 100).parallel();
        System.out.println(parallel.isParallel());
        IntStream seq = parallel.sequential();
        System.out.println(seq.isParallel());
    }
}
