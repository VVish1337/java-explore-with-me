package ru.practicum.ewm.service.event.admin;

import ru.practicum.ewm.dto.event.AdminUpdateEventDto;
import ru.practicum.ewm.dto.event.CommentDto;
import ru.practicum.ewm.dto.event.EventFullDto;

import java.util.List;

/**
 * Interface which describes Event service of Admin api
 *
 * @author Timur Kiyamov
 * @version 1.0
 */

public interface AdminEventService {
    /**
     * Method of service which update Event by administrator user
     *
     * @param eventId
     * @param dto
     * @return EventFullDto
     */
    EventFullDto updateEventByAdmin(Long eventId, AdminUpdateEventDto dto);

    /**
     * Method of service which publish Event by administrator user
     *
     * @param eventId
     * @return EventFullDto
     */
    EventFullDto publishEvent(Long eventId);

    /**
     * Method of service which reject Event publish by administrator user
     *
     * @param eventId
     * @return EventFullDto
     */
    EventFullDto rejectEvent(Long eventId);

    /**
     * Method of service which get filtered Events by administrator user
     *
     * @param users
     * @param states
     * @param categories
     * @param rangeStart
     * @param rangeEnd
     * @param from
     * @param size
     * @return
     */
    List<EventFullDto> getFilteredEvents(List<Long> users, List<String> states,
                                         List<Long> categories, String rangeStart,
                                         String rangeEnd, Integer from, Integer size);

    void deleteCommentByAdmin(Long eventId, Long comId);
}
