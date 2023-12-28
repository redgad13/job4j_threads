package ru.job4j.pools;

import org.junit.jupiter.api.Test;
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
        Sums[] myMatrix = RowsAndColumnsSum.sum(matrix);
        Sums[] rsl = new Sums[myMatrix.length];
        rsl[0] = new Sums(15, 55);
        rsl[1] = new Sums(40, 60);
        rsl[2] = new Sums(65, 65);
        rsl[3] = new Sums(90, 70);
        rsl[4] = new Sums(115, 75);
        assertThat(myMatrix).isEqualTo(rsl);
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
        Sums[] myMatrix = RowsAndColumnsSum.asyncSum(matrix);
        Sums[] rsl = new Sums[myMatrix.length];
        rsl[0] = new Sums(15, 55);
        rsl[1] = new Sums(40, 60);
        rsl[2] = new Sums(65, 65);
        rsl[3] = new Sums(90, 70);
        rsl[4] = new Sums(115, 75);
        assertThat(myMatrix).isEqualTo(rsl);
    }
}