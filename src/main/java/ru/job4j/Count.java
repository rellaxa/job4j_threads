package ru.job4j;

public class Count {

    private int value;

    /*
     * this - монитор (объект Count)
     * synchronized {} - критическая секция
     */
    public void increment() {
        synchronized (this) {
            value++;
        }
    }

    public synchronized int get() {
        synchronized (this) {
            return value;
        }
    }
}
