package ru.job4j.pool;

import ru.job4j.pool.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {

    private final ExecutorService pool;

    public EmailNotification() {
        this.pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    public void emailTo(User user) {
        String subject = String.format("Notification %s to email %s", user.username(), user.email());
        String body = String.format("Add a new event to %s", user.username());
        pool.submit(() -> send(subject, body, user.email()));
    }

    public void close() {
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void send(String subject, String body, String email) {

    }

    public static void main(String[] args) {
        EmailNotification emailNotification = new EmailNotification();
        User user1 = new User("JohnDoe", "john.doe@example.com");
        User user2 = new User("JaneSmith", "jane.smith@example.com");

        emailNotification.emailTo(user1);
        emailNotification.emailTo(user2);
        emailNotification.close();
    }
}
