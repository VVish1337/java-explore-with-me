package ru.practicum.ewm.event.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.dto.*;
import ru.practicum.ewm.event.service.PrivateEventService;

import java.util.List;

import static ru.practicum.ewm.util.DefaultValues.DEFAULT_FROM_VALUE;
import static ru.practicum.ewm.util.DefaultValues.DEFAULT_SIZE_VALUE;

@RestController
@RequestMapping(path = "/users/{userId}/events")
public class PrivateEventController {
    private final PrivateEventService service;

    @Autowired
    public PrivateEventController(PrivateEventService service) {
        this.service = service;
    }

    @PostMapping
    public EventResponseDto addEvent(@PathVariable long userId, @RequestBody NewEventDto dto) {
        return service.addEvent(userId, dto);
    }

    @GetMapping
    public List<EventShortDto> getUserOwnEvents(@PathVariable Long userId,
                                                @RequestParam(defaultValue = DEFAULT_FROM_VALUE) int from,
                                                @RequestParam(defaultValue = DEFAULT_SIZE_VALUE) int size) {
        return service.getUserOwnEvents(userId, from, size);
    }

    @PatchMapping
    public List<EventResponseDto> updateEventByUserOwner(@PathVariable Long userId, @RequestBody UpdateEventDto dto) {
        return null;
    }

    @GetMapping("/{eventId}")
    public EventFullDto getUserFullEventById(@PathVariable Long userId, @PathVariable Long eventId) {
        return null;
    }

    @PatchMapping("{eventId}")
    public EventFullDto cancelEventByUserOwner(@PathVariable Long userId, @PathVariable Long eventId){
        return null;
    }
}
