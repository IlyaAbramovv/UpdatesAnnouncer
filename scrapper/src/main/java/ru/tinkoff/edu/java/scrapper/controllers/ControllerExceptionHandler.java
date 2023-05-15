package ru.tinkoff.edu.java.scrapper.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.tinkoff.edu.java.scrapper.dto.ApiErrorResponse;
import ru.tinkoff.edu.java.scrapper.exceptions.ChatMissingException;
import ru.tinkoff.edu.java.scrapper.exceptions.InvalidParametersException;
import ru.tinkoff.edu.java.scrapper.exceptions.LinkMissingException;

import java.util.Arrays;

@RestControllerAdvice
public class ControllerExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidParametersException.class)
    public ApiErrorResponse invalidParameters(InvalidParametersException exception) {
        return new ApiErrorResponse(
                "Invalid parameters",
                HttpStatus.BAD_REQUEST.toString(),
                exception.getClass().getName(),
                exception.getMessage(),
                Arrays.stream(exception.getStackTrace()).map(StackTraceElement::toString).toList()
        );
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(LinkMissingException.class)
    public ApiErrorResponse linkNotFound(LinkMissingException ex) {
        return new ApiErrorResponse(
                "Link not found",
                HttpStatus.NOT_FOUND.toString(),
                ex.getClass().getName(),
                ex.getMessage(),
                Arrays.stream(ex.getStackTrace()).map(StackTraceElement::toString).toList()

        );
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ChatMissingException.class)
    public ApiErrorResponse chatNotFound(ChatMissingException ex) {
        return new ApiErrorResponse(
                "Chat not found",
                HttpStatus.NOT_FOUND.toString(),
                ex.getClass().getName(),
                ex.getMessage(),
                Arrays.stream(ex.getStackTrace()).map(StackTraceElement::toString).toList()
        );
    }
}
