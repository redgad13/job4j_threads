package ru.job4j.concurrent;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        var startAt = System.currentTimeMillis();
        try (var in = new URL(url).openStream();
             var out = new FileOutputStream("tmp")) {
            var elapsedTime = System.currentTimeMillis() - startAt;
            if (elapsedTime < speed) {
                Thread.sleep(speed - elapsedTime);
            }
            var dataBuffer = new byte[512];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                out.write(dataBuffer, 0, bytesRead);
            }

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        if (!args[0].startsWith("http")) {
            throw new IllegalArgumentException("введите ссылку, начиная с http");
        }
        if (Integer.parseInt(args[1]) < 0) {
            throw new IllegalArgumentException("значение должно быть больше нуля");
        }
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}