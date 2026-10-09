package com.example.task04;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class Logger {

    private final String name;
    private Level level = Level.DEBUG;

    private static final Map<String, Logger> loggers = new HashMap<>();

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("yyyy.MM.dd");

    private static final DateTimeFormatter TIME_FORMAT =
            DateTimeFormatter.ofPattern("HH:mm:ss");

    public enum Level {
        DEBUG,
        INFO,
        WARNING,
        ERROR
    }

    private Logger(String name) {
        this.name = name;
    }

    public static Logger getLogger(String name) {
        if (!loggers.containsKey(name)) {
            loggers.put(name, new Logger(name));
        }

        return loggers.get(name);
    }

    public String getName() {
        return name;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Level getLevel() {
        return level;
    }

    public void debug(String message) {
        log(Level.DEBUG, message);
    }

    public void debug(String format, Object... args) {
        log(Level.DEBUG, String.format(format, args));
    }

    public void info(String message) {
        log(Level.INFO, message);
    }

    public void info(String format, Object... args) {
        log(Level.INFO, String.format(format, args));
    }

    public void warning(String message) {
        log(Level.WARNING, message);
    }

    public void warning(String format, Object... args) {
        log(Level.WARNING, String.format(format, args));
    }

    public void error(String message) {
        log(Level.ERROR, message);
    }

    public void error(String format, Object... args) {
        log(Level.ERROR, String.format(format, args));
    }

    public void log(Level level, String message) {
        if (level.ordinal() < this.level.ordinal()) {
            return;
        }

        LocalDateTime now = LocalDateTime.now();

        String date = now.format(DATE_FORMAT);
        String time = now.format(TIME_FORMAT);

        System.out.println(
                "[" + level + "] " + date + " " + time
                        + " " + name + " - " + message
        );
    }

    public void log(Level level, String format, Object... args) {
        log(level, String.format(format, args));
    }
}