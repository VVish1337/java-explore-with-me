package ru.practicum.ewm.dto.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEventDto {
    private String annotation;
    private Long category;
    private String description;
    private String eventDate;
    private Long eventId;
    private Boolean paid;
    private Integer participantLimit;
    private String title;
}