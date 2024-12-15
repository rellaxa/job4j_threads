package ru.job4j.pools.async;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RowColSumTest {

    @Test
    public void whenOneThreadSums() {
        int[][] matrix = new int[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 6, 8}
        };
        RowColSum.Sums[] expected = new RowColSum.Sums[]{
                new RowColSum.Sums(6, 12),
                new RowColSum.Sums(15, 13),
                new RowColSum.Sums(21, 17)
        };
        assertThat(RowColSum.sum(matrix)).isEqualTo(expected);
    }

    @Test
    public void whenAsyncSums() {
        int[][] matrix = new int[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 6, 8}
        };
        RowColSum.Sums[] result = RowColSum.asyncSums(matrix);
        assertThat(result[0].getRowSum()).isEqualTo(6);
        assertThat(result[1].getRowSum()).isEqualTo(15);
        assertThat(result[2].getRowSum()).isEqualTo(21);

        assertThat(result[0].getColSum()).isEqualTo(12);
        assertThat(result[1].getColSum()).isEqualTo(13);
        assertThat(result[2].getColSum()).isEqualTo(17);
    }
}