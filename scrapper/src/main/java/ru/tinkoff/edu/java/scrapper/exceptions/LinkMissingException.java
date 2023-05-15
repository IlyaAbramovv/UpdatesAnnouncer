package ru.tinkoff.edu.java.scrapper.exceptions;

public class LinkMissingException extends RuntimeException {
    public LinkMissingException(String msg) {
        super(msg);
    }
}
