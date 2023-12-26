package ru.job4j.pools;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RowsAndColumnsSum {

    public static class Sums {
        private int rowSum;
        private int colSum;

        public int getRowSum() {
            return rowSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }
    }

    public static Sums[] sum(int[][] matrix) {
        int rowSum = 0;
        int colSum = 0;
        Sums[] rsl = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                colSum += matrix[j][i];
            }
            rsl[i] = new Sums();
            rsl[i].setColSum(colSum);
            colSum = 0;
        }
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                rowSum += matrix[i][j];
            }
            rsl[i].setRowSum(rowSum);
            rowSum = 0;
        }
        return rsl;
    }

    public static Sums[] asyncSum(int[][] matrix, CompletableFuture<Map<Integer, Integer>> columns, CompletableFuture<Map<Integer, Integer>> rows) throws ExecutionException, InterruptedException {
        Sums[] rsl = new Sums[matrix.length];
        for (int i = 0; i < columns.get().size(); i++) {
            rsl[i] = new Sums();
            rsl[i].setRowSum(rows.get().get(i));
            rsl[i].setColSum(columns.get().get(i));
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

