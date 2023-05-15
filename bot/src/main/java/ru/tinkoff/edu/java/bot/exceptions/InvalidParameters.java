package ru.tinkoff.edu.java.bot.exceptions;

public class InvalidParameters extends RuntimeException {
    public InvalidParameters(String msg) {
        super(msg);
    }
}
