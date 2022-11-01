package ru.practicum.ewm.dto.event;

import lombok.*;
import ru.practicum.ewm.dto.category.CategoryDto;
import ru.practicum.ewm.model.event.Location;
import ru.practicum.ewm.model.event.PublicationState;
import ru.practicum.ewm.dto.user.UserShortDto;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventFullDto {
    private Long id;
    private String annotation;
    private CategoryDto category;
    private Integer confirmedRequests;
    private String createdOn;
    private String description;
    private String eventDate;
    private UserShortDto initiator;
    private Location location;
    private Boolean paid;
    private Integer participantLimit;
    private String publishedOn;
    private Boolean requestModeration;
    private PublicationState state;
    private String title;
    private Long views;
}