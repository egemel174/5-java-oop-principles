
package com.example.task04;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileHandler implements MessageHandler, AutoCloseable {
    private final BufferedWriter writer;

    public FileHandler(String filename) {
        try {
            writer = new BufferedWriter(
                    new FileWriter(filename, true)
            );
        } catch (IOException e) {
            throw new IllegalStateException(
                    "Не удалось открыть файл: " + filename, e
            );
        }
    }

    @Override
    public synchronized void handle(String message) {
        try {
            writer.write(message);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            throw new IllegalStateException(
                    "Не удалось записать сообщение в файл", e
            );
        }
    }

    @Override
    public void close() {
        try {
            writer.close();
        } catch (IOException e) {
            throw new IllegalStateException(
                    "Не удалось закрыть файл", e
            );
        }
    }
}