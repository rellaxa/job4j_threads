package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public class ParseFile {

    private final File file;

    public ParseFile(final File file) {
        this.file = file;
    }

    public synchronized String getContent() {
        return content((el) -> true);
    }

    public synchronized String getContentWithoutUnicode() {
        return content((el) -> el < 0x80);
    }

    private synchronized String content(Predicate<Character> filter) {
        StringBuilder output = new StringBuilder();
        try (BufferedInputStream input = new BufferedInputStream(new FileInputStream(file))) {
            int data;
            while ((data = input.read()) != -1) {
                char ch = (char) data;
                if (filter.test(ch)) {
                    output.append(ch);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return output.toString();
    }
}
