package ru.practicum.ewm.controller.event.publiccontroller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.event.EventFullDto;
import ru.practicum.ewm.dto.event.EventShortDto;
import ru.practicum.ewm.dto.event.EventWithCommentsDto;
import ru.practicum.ewm.service.event.publicsrv.PublicEventService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Interface describing event controller for Public api.
 *
 * @author Timur Kiyamov
 * @version 1.0
 */
@Slf4j
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
    public List<EventShortDto> getFilteredEvents(@RequestParam(required = false) String text,
                                                 @RequestParam(required = false) List<Long> categories,
                                                 @RequestParam(required = false) Boolean paid,
                                                 @RequestParam(required = false) String rangeStart,
                                                 @RequestParam(required = false) String rangeEnd,
                                                 @RequestParam(required = false) Boolean onlyAvailable,
                                                 @RequestParam(required = false, defaultValue = "EVENT_DATE")
                                                 String sort,
                                                 @RequestParam(required = false, defaultValue = "0") Integer from,
                                                 @RequestParam(required = false, defaultValue = "10") Integer size,
                                                 HttpServletRequest request) {
        return service.getFilteredEvents(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort,
                from, size, request);
    }

    /**
     * Endpoint of controller which get Event with comments
     *
     * @param eventId id of event
     * @return EventWithCommentsDto
     */
    @Override
    @GetMapping("{eventId}/comments")
    public EventWithCommentsDto getEventWithComments(@PathVariable Long eventId) {
        log.info("Get event with comments event ID:{}", eventId);
        return service.getEventWithComments(eventId);
    }

    /**
     * Endpoint of controller which get Event with comments list
     *
     * @param sort param which describes how to sort ascending,descending
     * @return List of EventWithCommentsDto
     */
    @Override
    @GetMapping("/comments")
    public List<EventWithCommentsDto> getEventWithCommentsList(@RequestParam(defaultValue = "asc") String sort) {
        log.info("Get event with comments list sort:{}", sort);
        return service.getEventWithCommentsList(sort);
    }
}