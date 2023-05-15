package ru.tinkoff.edu.java.bot.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.tinkoff.edu.java.bot.dto.ApiErrorResponse;
import ru.tinkoff.edu.java.bot.exceptions.InvalidParameters;

import java.security.InvalidParameterException;
import java.util.Arrays;

@RestControllerAdvice
public class ControllerExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = InvalidParameterException.class)
    public ApiErrorResponse invalidParameters(InvalidParameters ex) {
        return new ApiErrorResponse("Invalid parameters",
                HttpStatus.BAD_REQUEST.toString(),
                ex.getClass().getName(),
                ex.getMessage(),
                Arrays.stream(ex.getStackTrace()).map(StackTraceElement::toString).toList());
    }
}
