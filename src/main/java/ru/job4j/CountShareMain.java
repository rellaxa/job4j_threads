package ru.job4j;

public class CountShareMain {

    /**
     * инкремент - упрощенная запись трех операций:
     * 1. чтение переменной.
     * 2. увеличение локальной переменной на единицу.
     * 3. запись локальной переменной в общий ресурс.
     * Может возникнуть ситуация, что обе нити выполнят
     * чтение переменной одновременно. В результате этого
     * общий ресурс обновится на единицу, а не на два.
     */
    public static void main(String[] args) throws InterruptedException {
        Count count = new Count();
        Thread first = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                count.increment();
            }
        });
        Thread second = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                count.increment();
            }
        });
        first.start();
        second.start();
        first.join();
        second.join();
        System.out.println(count.get());
    }
}