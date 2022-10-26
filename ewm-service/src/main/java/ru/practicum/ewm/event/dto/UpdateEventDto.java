package ru.practicum.ewm.event.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEventDto {
    private String annotation;
    private Long category;
    private String description;
    private LocalDateTime eventDate;
    private Long eventId;
    private Boolean paid;
    private Integer participantLimit;
    private String title;
}
