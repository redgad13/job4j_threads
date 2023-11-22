package ru.job4j.concurrent;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Wget implements Runnable {
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        try (var in = new URL(url).openStream();
             var out = new FileOutputStream("tmp")) {
            var dataBuffer = new byte[1024];
            int bytesRead;
            long elapsedTime;
            var startAt = System.currentTimeMillis();
            while ((bytesRead = in.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                out.write(dataBuffer, 0, bytesRead);
                elapsedTime = startAt - System.currentTimeMillis();
                var currentSpeed = (1024 / elapsedTime) * 1_000_000;
                if (speed < currentSpeed) {
                    Thread.sleep(currentSpeed - speed);
                }
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean checkUrl(String url) {
        String regex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(url);
        return matcher.matches();
    }

    public static void main(String[] args) throws InterruptedException {
        if (!checkUrl(args[0])) {
            throw new IllegalArgumentException("введите ссылку, начиная с http");
        }
        if (Integer.parseInt(args[1]) <= 0) {
            throw new IllegalArgumentException("значение должно быть больше нуля");
        }
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}