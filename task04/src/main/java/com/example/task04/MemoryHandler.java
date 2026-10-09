
package com.example.task04;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MemoryHandler implements MessageHandler, AutoCloseable {
    private final MessageHandler target;
    private final int capacity;
    private final List<String> messages = new ArrayList<>();

    public MemoryHandler(MessageHandler target, int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException(
                    "Размер буфера должен быть больше нуля"
            );
        }

        this.target = Objects.requireNonNull(target);
        this.capacity = capacity;
    }

    @Override
    public synchronized void handle(String message) {
        messages.add(message);

        if (messages.size() >= capacity) {
            flush();
        }
    }

    public synchronized void flush() {
        for (String message : messages) {
            target.handle(message);
        }

        messages.clear();
    }

    @Override
    public synchronized void close() {
        flush();

        if (target instanceof AutoCloseable) {
            try {
                ((AutoCloseable) target).close();
            } catch (Exception e) {
                throw new IllegalStateException(
                        "Не удалось закрыть целевой обработчик", e
                );
            }
        }
    }
}