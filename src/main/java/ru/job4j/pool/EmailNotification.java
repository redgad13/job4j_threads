package ru.job4j.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
    ExecutorService pool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors()
    );

    public void emailTo(User user) {
        String subject = String.format("subject = Notification %s to email %s.", user.getUserName(), user.geteMail());
        String body = String.format("body = Add a new event to %s", user.getUserName());
        pool.submit(() -> send(subject, body, user.eMail));
    }

    public void send(String subject, String body, String email) {

    }

    public void close() {
        pool.close();
    }
}
