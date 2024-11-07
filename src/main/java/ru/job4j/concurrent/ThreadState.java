package ru.job4j.concurrent;

public class ThreadState {

    public static void main(String[] args) {
        Thread first = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        Thread second = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        System.out.println(first.getState());
        System.out.println(second.getState());
        first.start();
        second.start();
        while (first.getState() != Thread.State.TERMINATED
                || second.getState() != Thread.State.TERMINATED) {
            System.out.println("1 thread state: " + first.getState());
            System.out.println("2 thread state: " + second.getState());
        }

        System.out.println("After completion 1 state: " + first.getState());
        System.out.println("After completion 2 state: " + second.getState());

        System.out.println(Thread.currentThread().getName() + " Completed");
    }
}