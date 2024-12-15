package ru.job4j.pools.async;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;

public class RowColSum {

    public static class Sums {

        private int rowSum;
        private int colSum;

        public Sums(int rowSum, int colSum) {
            this.rowSum = rowSum;
            this.colSum = colSum;
        }

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

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Sums sums = (Sums) o;
            return rowSum == sums.rowSum && colSum == sums.colSum;
        }

        @Override
        public int hashCode() {
            return Objects.hash(rowSum, colSum);
        }

        @Override
        public String toString() {
            return "Sums{"
                    + "rowSum=" + rowSum
                    + ", colSum=" + colSum
                    + '}';
        }
    }

    public static Sums[] sum(int[][] matrix) {
        return IntStream.range(0, matrix.length)
                .mapToObj(i -> getSums(matrix, i))
                .toArray(Sums[]::new);
    }

    public static Sums[] asyncSums(int[][] matrix) {
        int size = matrix.length;
        CompletableFuture<Sums>[] futures = new CompletableFuture[size];
        IntStream.range(0, size).forEach(i -> futures[i] = CompletableFuture.supplyAsync(() -> getSums(matrix, i)));
        Sums[] sums = new Sums[size];
        IntStream.range(0, size).forEach(i -> {
            try {
                sums[i] = futures[i].get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        });
        return sums;
    }

    private static Sums getSums(int[][] matrix, int i) {
        int rowSum = 0;
        int colSum = 0;
        for (int j = 0; j < matrix.length; j++) {
            rowSum += matrix[i][j];
            colSum += matrix[j][i];
        }
        Sums sums = new Sums(rowSum, colSum);
        System.out.println(Thread.currentThread().getName() + " " + sums);
        return sums;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int[][] matrix = new int[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 6, 8}
        };
        System.out.println(Arrays.toString(asyncSums(matrix)));
    }
}
