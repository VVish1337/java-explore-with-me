package ru.practicum.ewm.exception.model;

import lombok.*;

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