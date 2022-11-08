package ru.practicum.ewm.service.event.privatesrv;

import ru.practicum.ewm.dto.event.*;

import java.util.List;

/**
 * Interface which describes Event service of Private api
 *
 * @author Timur Kiyamov
 * @version 1.0
 */
public interface PrivateEventService {
    /**
     * Method of service which add Event by user
     *
     * @param userId
     * @param dto
     * @return EventFullDto
     */
    EventFullDto addEvent(long userId, NewEventDto dto);

    /**
     * Method of service which get list of User owned Events
     *
     * @param userId
     * @param from
     * @param size
     * @return List of EventShortDto
     */
    List<EventShortDto> getUserOwnEvents(Long userId, int from, int size);

    /**
     * Method of service which get full information by User owner of Event by id
     *
     * @param userId
     * @param eventId
     * @return EventFullDto
     */
    EventFullDto getUserFullEventById(Long userId, Long eventId);

    /**
     * Method of service which update Event by User owner
     *
     * @param userId
     * @param dto
     * @return
     */
    EventFullDto updateEventByUserOwner(Long userId, UpdateEventDto dto);

    /**
     * Method of service which cancel Event from publish by User owner
     *
     * @param userId
     * @param eventId
     * @return
     */
    EventFullDto cancelEventByUserOwner(Long userId, Long eventId);

    CommentDto addCommentToEvent(Long userId, Long eventId, String text);

    CommentDto updateCommentByUserOwner(Long userId, Long eventId, Long comId, String text);

    void deleteCommentByUserOwner(Long userId, Long eventId, Long comId);
}
