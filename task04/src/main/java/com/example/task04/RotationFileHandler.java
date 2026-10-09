
package com.example.task04;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class RotationFileHandler implements MessageHandler, AutoCloseable {
    private final Path directory;
    private final ChronoUnit interval;

    private BufferedWriter writer;
    private String currentPeriod;

    public RotationFileHandler(String directory, ChronoUnit interval) {
        if (interval.isDurationEstimated()
                || interval.isDateBased()
                || interval.getDuration().isZero()
                || interval.getDuration().isNegative()) {
            throw new IllegalArgumentException(
                    "Интервал должен быть положительным и фиксированным"
            );
        }

        this.directory = Path.of(directory);
        this.interval = interval;

        try {
            Files.createDirectories(this.directory);
        } catch (IOException e) {
            throw new IllegalStateException(
                    "Не удалось создать каталог", e
            );
        }
    }

    @Override
    public synchronized void handle(String message) {
        String period = LocalDateTime.now()
                .truncatedTo(interval)
                .toString()
                .replace(":", "-");

        try {
            if (!period.equals(currentPeriod)) {
                if (writer != null) {
                    writer.close();
                }

                Path file = directory.resolve(period + ".log");

                writer = Files.newBufferedWriter(
                        file,
                        StandardOpenOption.CREATE,
                        StandardOpenOption.APPEND
                );

                currentPeriod = period;
            }

            writer.write(message);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            throw new IllegalStateException(
                    "Ошибка записи в файл с ротацией", e
            );
        }
    }

    @Override
    public synchronized void close() {
        if (writer != null) {
            try {
                writer.close();
            } catch (IOException e) {
                throw new IllegalStateException(
                        "Не удалось закрыть файл", e
                );
            }
        }
    }
}