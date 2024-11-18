package ru.job4j.buffer;

public class Buffer {

    private StringBuilder buffer = new StringBuilder();

    /*
    Критчичекая область: тела add() и toString()
    Монитор - this объект
     */
    public synchronized void add(int value) {
        System.out.println(value);
        buffer.append(value);
    }

    @Override
    public synchronized String toString() {
        return buffer.toString();
    }
}
