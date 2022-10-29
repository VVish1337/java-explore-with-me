package ru.practicum.ewm.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import static ru.practicum.ewm.util.DefaultValues.DEFAULT_DATE_TIME_PATTERN;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_PATTERN);

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handle(IllegalArgumentException ex) {
        log.warn("Bad Request"+ex.getMessage());
        return ApiError.builder()
                .message(ex.getMessage())
                .reason("For the requested operation the conditions are not met.")
                .status(Status.FORBIDDEN)
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handle(NotFoundException ex) {
        log.warn("Not Found Exception"+ex.getMessage());
        return ApiError.builder()
                .message(ex.getMessage())
                .reason("The required object was not found.")
                .status(Status.NOT_FOUND)
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiError handle(ForbiddenException ex) {
        log.warn("Forbidden Exception"+ex.getMessage());
        return ApiError.builder()
                .message(ex.getMessage())
                .reason("For the requested operation the conditions are not met.")
                .status(Status.FORBIDDEN)
                .timestamp(LocalDateTime.now().format(formatter))
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handle(Throwable e) {
        log.warn("Internal server error:"+e.getMessage()+" stacktrace:"+ Arrays.toString(e.getStackTrace()));
        return ApiError.builder()
                .message("could not execute statement; SQL [n/a]; constraint [uq_category_name];" +
                        " nested exception is org.hibernate.exception.ConstraintViolationException: " +
                        "could not execute statement")
                .reason("Error occurred")
                .status(Status.INTERNAL_SERVER_ERROR)
                .timestamp(LocalDateTime.now().format(formatter))
                .build();
    }
}
