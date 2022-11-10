package ru.practicum.ewm.service.event.admin;

import ru.practicum.ewm.dto.event.AdminUpdateEventDto;
import ru.practicum.ewm.dto.event.EventFullDto;
import ru.practicum.ewm.dto.event.comment.CommentReportDto;

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

    /**
     * Method of service which delete comment by Administrator
     *
     * @param eventId id of event
     * @param comId   id of comment
     */
    void deleteCommentByAdmin(Long eventId, Long comId);

    /**
     * Method of service which get all reported comments
     *
     * @return List of CommentReportDto
     */
    List<CommentReportDto> getReportedComments();

    /**
     * Method of service which get all filtered reported comments
     *
     * @param start    start time
     * @param end      end time
     * @param category category of report (check ReportName)
     * @return List of CommentReportDto
     */
    List<CommentReportDto> getFilteredReportedComments(String start, String end, String category);

    /**
     * Method of service which get all report comments by reporter
     *
     * @param userId user id (report owner)
     * @return List of CommentReportDto
     */
    List<CommentReportDto> getAllReportCommentsOwner(Long userId);

    /**
     * Method of service which delete comment report by id
     *
     * @param repId id of comment report
     */
    void deleteReportById(Long repId);
}
