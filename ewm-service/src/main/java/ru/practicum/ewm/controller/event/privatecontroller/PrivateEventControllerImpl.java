package ru.practicum.ewm.controller.event.privatecontroller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.event.EventFullDto;
import ru.practicum.ewm.dto.event.EventShortDto;
import ru.practicum.ewm.dto.event.NewEventDto;
import ru.practicum.ewm.dto.event.UpdateEventDto;
import ru.practicum.ewm.service.event.privatesrv.PrivateEventService;

import javax.validation.constraints.Min;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

import static ru.practicum.ewm.util.DefaultValues.DEFAULT_FROM_VALUE;
import static ru.practicum.ewm.util.DefaultValues.DEFAULT_SIZE_VALUE;

@Slf4j
@Validated
@RestController
@RequestMapping(path = "/users/{userId}/events")
public class PrivateEventControllerImpl implements PrivateEventController {
    private final PrivateEventService service;

    @Autowired
    public PrivateEventControllerImpl(PrivateEventService service) {
        this.service = service;
    }

    @Override
    @PostMapping
    public EventFullDto addEvent(@PathVariable Long userId, @RequestBody NewEventDto dto) {
        log.info("post event {}, owner id:{}", dto, userId);
        return service.addEvent(userId, dto);
    }

    @Override
    @GetMapping
    public List<EventShortDto> getUserOwnEvents(@PathVariable Long userId,
                                                @RequestParam(defaultValue = DEFAULT_FROM_VALUE) @Min(0) int from,
                                                @PositiveOrZero @RequestParam(defaultValue = DEFAULT_SIZE_VALUE)
                                                int size) {
        log.info("get owner list event owner id:{}", userId);
        return service.getUserOwnEvents(userId, from, size);
    }

    @Override
    @PatchMapping
    public EventFullDto updateEventByUserOwner(@PathVariable Long userId, @RequestBody UpdateEventDto dto) {
        log.info("update event {}, owner id:{}", dto, userId);
        return service.updateEventByUserOwner(userId, dto);
    }

    @Override
    @GetMapping("/{eventId}")
    public EventFullDto getUserFullEventById(@PathVariable Long userId, @PathVariable Long eventId) {
        log.info("get full event {}, owner id:{}", eventId, userId);
        return service.getUserFullEventById(userId, eventId);
    }

    @Override
    @PatchMapping("{eventId}")
    public EventFullDto cancelEventByUserOwner(@PathVariable Long userId, @PathVariable Long eventId) {
        log.info("cancel event by id:{} and owner id:{}", eventId, userId);
        return service.cancelEventByUserOwner(userId, eventId);
    }
}