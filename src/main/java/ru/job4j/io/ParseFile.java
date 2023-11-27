package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public final class ParseFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public String getContent(Predicate<Character> filter) throws IOException {
        StringBuilder output = new StringBuilder();
        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(file))) {
            int data;
            while ((data = in.available()) > 0) {
                if (filter.test((char) data)) {
                    output.append((char) data);
                }
            }
        }
        return output.toString();
    }
}
