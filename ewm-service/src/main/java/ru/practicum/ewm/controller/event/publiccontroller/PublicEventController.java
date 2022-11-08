package ru.practicum.ewm.controller.event.publiccontroller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.ewm.dto.event.EventFullDto;
import ru.practicum.ewm.dto.event.EventShortDto;
import ru.practicum.ewm.dto.event.EventWithCommentsDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Interface describing event controller for Public api.
 *
 * @author Timur Kiyamov
 * @version 1.0
 */

@RequestMapping("/events")
public interface PublicEventController {
    /**
     * Endpoint of controller which get full info about event by id
     *
     * @param eventId
     * @param request
     * @return EventFullDto
     */
    @GetMapping("/{eventId}")
    EventFullDto getFullEventInfoById(@PathVariable Long eventId, HttpServletRequest request);

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
    @GetMapping
    List<EventShortDto> getFilteredEvents(@RequestParam String text,
                                          @RequestParam List<Long> categories,
                                          @RequestParam Boolean paid,
                                          @RequestParam String rangeStart,
                                          @RequestParam String rangeEnd,
                                          @RequestParam Boolean onlyAvailable,
                                          @RequestParam(defaultValue = "EVENT_DATE") String sort,
                                          @RequestParam Integer from,
                                          @RequestParam Integer size,
                                          HttpServletRequest request);

    @GetMapping("{eventId}/comments")
    EventWithCommentsDto getEventWithComments(@PathVariable Long eventId);

    @GetMapping("/comments")
    List<EventWithCommentsDto> getEventWithCommentsList(@RequestParam String sort);
}