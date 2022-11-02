package ru.practicum.ewm.controller.event.publiccontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.event.EventFullDto;
import ru.practicum.ewm.dto.event.EventShortDto;
import ru.practicum.ewm.service.event.publicsrv.PublicEventService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Interface describing event controller for Public api.
 *
 * @author Timur Kiyamov
 * @version 1.0
 */
@RestController
@RequestMapping("/events")
public class PublicEventControllerImpl implements PublicEventController {

    private final PublicEventService service;

    @Autowired
    public PublicEventControllerImpl(PublicEventService service) {
        this.service = service;
    }

    /**
     * Endpoint of controller which get full info about event by id
     *
     * @param eventId
     * @param request
     * @return EventFullDto
     */
    @Override
    @GetMapping("/{eventId}")
    public EventFullDto getFullEventInfoById(@PathVariable Long eventId, HttpServletRequest request) {
        return service.getFullEventInfoById(eventId, request);
    }

    /**
     * Endpoint of controller which get filtered events
     *
     * @param text
     * @param categories
     * @param paid
     * @param rangeStart
     * @param rangeEnd
     * @param onlyAvailable
     * @param sort
     * @param from
     * @param size
     * @param request
     * @return
     */
    @Override
    @GetMapping
    public List<EventShortDto> getFilteredEvents(@RequestParam String text,
                                                 @RequestParam List<Long> categories,
                                                 @RequestParam Boolean paid,
                                                 @RequestParam String rangeStart,
                                                 @RequestParam String rangeEnd,
                                                 @RequestParam Boolean onlyAvailable,
                                                 @RequestParam(defaultValue = "EVENT_DATE") String sort,
                                                 @RequestParam Integer from,
                                                 @RequestParam Integer size,
                                                 HttpServletRequest request) {
        return service.getFilteredEvents(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size, request);
    }
}