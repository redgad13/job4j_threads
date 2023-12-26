package ru.job4j.pools;

import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.*;

class RowsAndColumnsSumTest {

    @Test
    void sum() {
        int[][] matrix = new int[5][5];
        int count = 1;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = count++;
            }
        }
        RowsAndColumnsSum.Sums[] rsl = RowsAndColumnsSum.sum(matrix);
        assertThat(rsl[0].getRowSum()).isEqualTo(15);
        assertThat(rsl[1].getRowSum()).isEqualTo(40);
        assertThat(rsl[2].getRowSum()).isEqualTo(65);
        assertThat(rsl[3].getRowSum()).isEqualTo(90);
        assertThat(rsl[4].getRowSum()).isEqualTo(115);
        assertThat(rsl[0].getColSum()).isEqualTo(55);
        assertThat(rsl[1].getColSum()).isEqualTo(60);
        assertThat(rsl[2].getColSum()).isEqualTo(65);
        assertThat(rsl[3].getColSum()).isEqualTo(70);
        assertThat(rsl[4].getColSum()).isEqualTo(75);
    }

    @Test
    void asyncSum() throws ExecutionException, InterruptedException {
        int[][] matrix = new int[5][5];
        int count = 1;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = count++;
            }
        }
        CompletableFuture<Map<Integer, Integer>> rowsSum = RowsAndColumnsSum.rowsSum(matrix);
        CompletableFuture<Map<Integer, Integer>> columnsSum = RowsAndColumnsSum.columnsSum(matrix);
        RowsAndColumnsSum.Sums[] rsl = RowsAndColumnsSum.asyncSum(matrix, columnsSum, rowsSum);
        assertThat(rsl[0].getRowSum()).isEqualTo(15);
        assertThat(rsl[1].getRowSum()).isEqualTo(40);
        assertThat(rsl[2].getRowSum()).isEqualTo(65);
        assertThat(rsl[3].getRowSum()).isEqualTo(90);
        assertThat(rsl[4].getRowSum()).isEqualTo(115);
        assertThat(rsl[0].getColSum()).isEqualTo(55);
        assertThat(rsl[1].getColSum()).isEqualTo(60);
        assertThat(rsl[2].getColSum()).isEqualTo(65);
        assertThat(rsl[3].getColSum()).isEqualTo(70);
        assertThat(rsl[4].getColSum()).isEqualTo(75);
    }
}