package ru.job4j.pools;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RowsAndColumnsSum {

    public static Sums[] sum(int[][] matrix) {
        int rows = matrix.length;
        int columns = matrix[0].length;
        Sums[] rsl = new Sums[rows];
        for (int i = 0; i < rows; i++) {
            rsl[i] = new Sums();
            for (int j = 0; j < columns; j++) {
                rsl[i].setRowSum(rsl[i].getRowSum() + matrix[i][j]);
                rsl[i].setColSum(rsl[i].getColSum() + matrix[j][i]);
            }
        }
        return rsl;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Sums[] rsl = new Sums[matrix.length];
        for (int i = 0; i < rsl.length; i++) {
            rsl[i] = new Sums();
            rsl[i].setRowSum(rowsSum(matrix).get().get(i));
            rsl[i].setColSum(columnsSum(matrix).get().get(i));
        }
        return rsl;
    }

    public static CompletableFuture<Map<Integer, Integer>> rowsSum(int[][] matrix) {
        return CompletableFuture.supplyAsync(() -> {
            Map<Integer, Integer> rsl = new HashMap<>();
            int sum = 0;
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[i].length; j++) {
                    sum += matrix[i][j];
                }
                rsl.put(i, sum);
                sum = 0;
            }
            return rsl;
        });
    }

    public static CompletableFuture<Map<Integer, Integer>> columnsSum(int[][] matrix) {
        return CompletableFuture.supplyAsync(() -> {
            Map<Integer, Integer> rsl = new HashMap<>();
            int sum = 0;
            for (int j = 0; j < matrix.length; j++) {
                for (int i = 0; i < matrix[j].length; i++) {
                    sum += matrix[i][j];
                }
                rsl.put(j, sum);
                sum = 0;
            }
            return rsl;
        });
    }
}

