package ru.practicum.ewm.service.event.publicsrv;

import ru.practicum.ewm.dto.event.EventFullDto;
import ru.practicum.ewm.dto.event.EventShortDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Interface which describes Event service of Public api
 *
 * @author Timur Kiyamov
 * @version 1.0
 */
public interface PublicEventService {
    /**
     * Method of service which get full information about Event by ID
     *
     * @param eventId
     * @param request
     * @return EventFullDto
     */
    EventFullDto getFullEventInfoById(Long eventId, HttpServletRequest request);

    /**
     * Method of service which get filtered Event list
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
     * @return List of EventShortDto
     */
    List<EventShortDto> getFilteredEvents(String text, List<Long> categories,
                                          Boolean paid, String rangeStart,
                                          String rangeEnd, Boolean onlyAvailable,
                                          String sort, Integer from, Integer size, HttpServletRequest request);
}
