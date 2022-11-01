package ru.practicum.ewm.controller.event.privatecontroller;

import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.event.EventFullDto;
import ru.practicum.ewm.dto.event.EventShortDto;
import ru.practicum.ewm.dto.event.NewEventDto;
import ru.practicum.ewm.dto.event.UpdateEventDto;

import javax.validation.constraints.Min;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

import static ru.practicum.ewm.util.DefaultValues.DEFAULT_FROM_VALUE;
import static ru.practicum.ewm.util.DefaultValues.DEFAULT_SIZE_VALUE;

@RequestMapping(path = "/users/{userId}/events")
public interface PrivateEventController {
    @PostMapping
    EventFullDto addEvent(@PathVariable Long userId, @RequestBody NewEventDto dto);

    @GetMapping
    List<EventShortDto> getUserOwnEvents(@PathVariable Long userId,
                                         @RequestParam(defaultValue = DEFAULT_FROM_VALUE) @Min(0) int from,
                                         @PositiveOrZero @RequestParam(defaultValue = DEFAULT_SIZE_VALUE)
                                         int size);

    @PatchMapping
    EventFullDto updateEventByUserOwner(@PathVariable Long userId, @RequestBody UpdateEventDto dto);

    @GetMapping("/{eventId}")
    EventFullDto getUserFullEventById(@PathVariable Long userId, @PathVariable Long eventId);

    @PatchMapping("{eventId}")
    EventFullDto cancelEventByUserOwner(@PathVariable Long userId, @PathVariable Long eventId);
}