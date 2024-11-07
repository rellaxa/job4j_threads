package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {

    @Override
    public void run() {
        var process = new char[]{'-', '\\', '|', '/'};
        while (!Thread.currentThread().isInterrupted()) {
            try {
                for (char c : process) {
                    System.out.print("\rLoading: " + c);
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args)  {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        try {
            System.out.println(Thread.currentThread().getName());
            Thread.sleep(5000); /* симулируем выполнение параллельной задачи в течение 5 секунд. */
            progress.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
