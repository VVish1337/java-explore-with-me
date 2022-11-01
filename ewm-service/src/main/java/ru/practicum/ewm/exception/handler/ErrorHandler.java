package ru.practicum.ewm.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.ewm.exception.ForbiddenException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.exception.Status;
import ru.practicum.ewm.exception.model.ApiError;
import ru.practicum.ewm.util.DateFormatter;

import java.net.HttpURLConnection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import static ru.practicum.ewm.util.DefaultValues.DEFAULT_DATE_FORMATTER;
import static ru.practicum.ewm.util.DefaultValues.DEFAULT_DATE_TIME_PATTERN;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

   // private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_PATTERN);

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handle(IllegalArgumentException ex) {
        log.warn("Bad Request" + ex.getMessage());
        return ApiError.builder()
                .message(ex.getMessage())
                .reason("For the requested operation the conditions are not met.")
                .status(HttpURLConnection.HTTP_FORBIDDEN)
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handle(NotFoundException ex) {
        log.warn("Not Found Exception" + ex.getMessage());
        return ApiError.builder()
                .message(ex.getMessage())
                .reason("The required object was not found.")
                .status(HttpURLConnection.HTTP_NOT_FOUND)
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiError handle(ForbiddenException ex) {
        log.warn("Forbidden Exception" + ex.getMessage());
        return ApiError.builder()
                .message(ex.getMessage())
                .reason("For the requested operation the conditions are not met.")
                .status(HttpURLConnection.HTTP_FORBIDDEN)
                .timestamp(LocalDateTime.now().format(DEFAULT_DATE_FORMATTER))
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handle(Throwable e) {
        log.warn("Internal server error:" + e.getMessage() + " stacktrace:" + Arrays.toString(e.getStackTrace()));
        return ApiError.builder()
                .message("could not execute statement; SQL [n/a]; constraint [uq_category_name];" +
                        " nested exception is org.hibernate.exception.ConstraintViolationException: " +
                        "could not execute statement")
                .reason("Error occurred")
                .status(HttpURLConnection.HTTP_INTERNAL_ERROR)
                .timestamp(DateFormatter.formatDate(LocalDateTime.now()))
                .build();
    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handle(DataIntegrityViolationException e) {
        log.warn("Internal server error:" + e.getMessage() + " stacktrace:" + Arrays.toString(e.getStackTrace()));
        return ApiError.builder()
                .message("could not execute statement; SQL [n/a]; constraint [uq_category_name];" +
                        " nested exception is org.hibernate.exception.ConstraintViolationException: " +
                        "could not execute statement")
                .reason("Error occurred")
                .status(HttpURLConnection.HTTP_CONFLICT)
                .timestamp(DateFormatter.formatDate(LocalDateTime.now()))
                .build();
    }
}