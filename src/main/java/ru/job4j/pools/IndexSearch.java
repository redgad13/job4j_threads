package ru.job4j.pools;

import java.util.concurrent.RecursiveTask;

public class IndexSearch<T> extends RecursiveTask<Integer> {
    private final T[] array;
    private final T lookingFor;
    private final int from;
    private final int to;

    public IndexSearch(T[] array, T lookingFor, int from, int to) {
        this.array = array;
        this.lookingFor = lookingFor;
        this.from = from;
        this.to = to;
    }


    @Override
    protected Integer compute() {
        Integer rsl = -1;
        if (to - from <= 10) {
            rsl = linearSearch();
        } else {
            int middle = (from + to) / 2;
            IndexSearch<T> leftSearch = new IndexSearch<>(array, lookingFor, from, middle);
            IndexSearch<T> rightSearch = new IndexSearch<>(array, lookingFor, middle + 1, to);
            leftSearch.fork();
            rightSearch.fork();
            Integer leftResult = leftSearch.join();
            Integer rightResult = rightSearch.join();
            if (leftResult != -1) {
                rsl = leftResult;
            } else if (rightResult != -1) {
                rsl = rightResult;
            }
        }
        return rsl;
    }

    private Integer linearSearch() {
        int rsl = -1;
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(lookingFor)) {
                rsl = i;
                break;
            }
        }
        return rsl;
    }
}