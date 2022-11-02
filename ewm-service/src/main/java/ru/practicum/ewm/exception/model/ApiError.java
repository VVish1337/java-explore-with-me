package ru.practicum.ewm.exception.model;

import lombok.*;

/**
 * Class which describes custom exception model
 *
 * @author Timur Kiyamov
 * @version 1.0
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiError {
    private Error[] errors;
    private String message;
    private String reason;
    private Integer status;
    private String timestamp;
}