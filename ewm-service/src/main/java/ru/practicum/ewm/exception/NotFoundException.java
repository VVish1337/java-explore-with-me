package ru.practicum.ewm.exception;

/**
 * Custom not found exception class
 *
 * @author Timur Kiyamov
 * @version 1.0
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}