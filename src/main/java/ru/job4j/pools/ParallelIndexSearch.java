package ru.job4j.pools;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelIndexSearch<T> extends RecursiveTask<Integer> {

    private final T[] array;
    private final int from;
    private final int to;
    private final T value;

    public ParallelIndexSearch(T[] array, int from, int to, T value) {
        this.array = array;
        this.from = from;
        this.to = to;
        this.value = value;
    }

    @Override
    protected Integer compute() {
        if ((to - from) < 10) {
            int index = -1;
            for (int i = from; i <= to; i++) {
                if (array[i].equals(value)) {
                    index = i;
                    break;
                }
            }
            return index;
        }
        int middle = (from + to) / 2;
        ParallelIndexSearch<T> leftTask = new ParallelIndexSearch<>(array, from, middle, value);
        ParallelIndexSearch<T> rightTask = new ParallelIndexSearch<>(array, middle + 1, to, value);
        leftTask.fork();
        rightTask.fork();
        int leftRsl = leftTask.join();
        int rightRsl = rightTask.join();
        return leftRsl == -1 ? rightRsl : leftRsl;
    }

    public static <T> int search(T[] array, T value) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParallelIndexSearch<>(array, 0, array.length - 1, value));
    }

    public static void main(String[] args) {
        Integer val = 1;
        Integer[] array = new Integer[20];
        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }
        System.out.println(search(array, val));
    }
}
