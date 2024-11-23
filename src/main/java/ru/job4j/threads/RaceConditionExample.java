package ru.job4j.threads;

public class RaceConditionExample {

    private int number = 0;

    public synchronized void increment() {
        for (int i = 0; i < 99999; i++) {
            int current = number;
            int next = ++number;
            if (current + 1 != next) {
                throw new IllegalArgumentException("Некорректное сравнение: " + current + " + 1 = " + next);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        RaceConditionExample raceConditionExample = new RaceConditionExample();
        Thread one = new Thread(raceConditionExample::increment);
        one.start();
        Thread two = new Thread(raceConditionExample::increment);
        two.start();
        one.join();
        two.join();
    }
}
