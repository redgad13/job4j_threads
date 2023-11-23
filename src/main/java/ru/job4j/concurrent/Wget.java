package ru.job4j.concurrent;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Wget implements Runnable {
    private final String url;
    private final String destFolder;
    private final int speed;
    private static final String REGEX = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
    private static final Pattern PATTERN = Pattern.compile(REGEX);

    public Wget(String url, String destFolder, int speed) {
        this.url = url;
        this.destFolder = destFolder;
        this.speed = speed;
    }

    @Override
    public void run() {
        try (var in = new URL(url).openStream();
             var out = new FileOutputStream(destFolder)) {
            var dataBuffer = new byte[1024];
            int bytesRead;
            long totalDownloaded = 0;
            var startTime = System.currentTimeMillis();
            while ((bytesRead = in.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                out.write(dataBuffer, 0, bytesRead);
                totalDownloaded += bytesRead;
                if (totalDownloaded >= speed) {
                    var elapsedTime = System.currentTimeMillis() - startTime;
                    if (elapsedTime < 1000) {
                        Thread.sleep(1000 - elapsedTime);
                    }
                }

            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean checkUrl(String url) {
        Matcher matcher = PATTERN.matcher(url);
        return matcher.matches();
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length < 2) {
            throw new IllegalArgumentException("проверьте количество введенных аргументов");
        }
        if (!checkUrl(args[0])) {
            throw new IllegalArgumentException("введите ссылку, начиная с http");
        }
        if (args[1].isEmpty()) {
            throw new IllegalArgumentException("введите имя файла для сохранения");
        }
        if (Integer.parseInt(args[2]) <= 0) {
            throw new IllegalArgumentException("значение должно быть больше нуля");
        }
        String url = args[0];
        String destFolder = args[1];
        int speed = Integer.parseInt(args[2]);
        Thread wget = new Thread(new Wget(url, destFolder, speed));
        wget.start();
        wget.join();
    }
}