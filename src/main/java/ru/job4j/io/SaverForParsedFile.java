package ru.job4j.io;

import java.io.*;

public final class SaverForParsedFile {
    private final File file;

    public SaverForParsedFile(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public void saveContent(String content) throws IOException {
        try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file))) {
            for (int i = 0; i < content.length(); i++) {
                out.write(content.charAt(i));
            }
        }
    }
}
