package ru.job4j.io;

import java.io.*;

public class FileWriter {

    private final File file;

    public FileWriter(final File file) {
        this.file = file;
    }

    public synchronized void saveContent(String content) {
        try (OutputStream output = new BufferedOutputStream(new FileOutputStream(file))) {
            output.write(content.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
