package ru.job4j.thread;

import org.apache.commons.validator.routines.UrlValidator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {

    private final String url;
    private final int speed;
    private final String path;

    public Wget(String url, int speed, String path) {
        this.url = url;
        this.speed = speed;
        this.path = path;
    }

    @Override
    public void run() {
        var startAt = System.currentTimeMillis();
        File file = new File(path);
        try (var input = new URL(url).openStream();
             var output = new FileOutputStream(file)) {
            System.out.println("Open connection : " + (System.currentTimeMillis() - startAt) + " ms");
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            int downloadData = 0;
            long start = System.currentTimeMillis();
            while ((bytesRead = input.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                downloadData += bytesRead;
                if (downloadData >= speed) {
                    long interval = System.currentTimeMillis() - start;
                    if (interval < 1000) {
                        Thread.sleep(1000 - interval);
                    }
                    downloadData = 0;
                    start = System.currentTimeMillis();
                }
                output.write(dataBuffer, 0, bytesRead);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static void validateArgs(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("Should be 3 arguments");
        }
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        UrlValidator validator = new UrlValidator();
        if (!validator.isValid(url)) {
            throw new IllegalArgumentException("Invalid url: " + url);
        }
        if (speed < 1) {
            throw new IllegalArgumentException("The speed must be greater than 1");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        validateArgs(args);
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        String path = args[2];
        Thread wget = new Thread(new Wget(url, speed, path));
        wget.start();
        wget.join();
    }
}
