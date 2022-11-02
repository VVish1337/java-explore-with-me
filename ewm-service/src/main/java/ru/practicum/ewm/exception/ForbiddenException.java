package ru.practicum.ewm.exception;

/**
 * Custom forbidden exception class
 *
 * @author Timur Kiyamov
 * @version 1.0
 */
public class ForbiddenException extends RuntimeException {
    public ForbiddenException(String message) {
        super(message);
    }
}