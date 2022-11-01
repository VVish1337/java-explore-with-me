package ru.practicum.ewm.dto.event;

import lombok.*;
import ru.practicum.ewm.dto.category.CategoryDto;
import ru.practicum.ewm.dto.user.UserShortDto;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventShortDto {
    private Long id;
    private String annotation;
    private CategoryDto category;
    private Integer confirmedRequests;
    private String eventDate;
    private UserShortDto initiator;
    private Boolean paid;
    private String title;
    private Long views;
}