package com.example.task04;

public class LoggerTest {
    public static void main(String[] args) {
        Logger logger = Logger.getLogger("TestLogger");

        logger.debug("Это сообщение DEBUG");
        logger.info("Приложение запущено");
        logger.warning("Это предупреждение");
        logger.error("Произошла ошибка");

        logger.setLevel(Logger.Level.WARNING);

        logger.debug("Это сообщение не должно появиться");
        logger.info("Это сообщение тоже не должно появиться");
        logger.warning("Это предупреждение должно появиться");
        logger.error("Эта ошибка должна появиться");
    }
}