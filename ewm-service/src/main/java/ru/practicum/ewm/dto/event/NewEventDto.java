package ru.practicum.ewm.dto.event;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import ru.practicum.ewm.model.event.Location;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Class which describes new event dto
 *
 * @author Timur Kiyamov
 * @version 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewEventDto {
    @NotNull
    @NotBlank
    @Length(min = 20, max = 2000)
    private String annotation;
    private Long category;
    @Length(min = 20, max = 7000)
    private String description;
    private String eventDate;
    private Location location;
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;
    @Length(min = 3, max = 120)
    private String title;
}