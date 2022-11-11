package ru.practicum.ewm.controller.event.admin;

import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.event.AdminUpdateEventDto;
import ru.practicum.ewm.dto.event.EventFullDto;
import ru.practicum.ewm.dto.event.comment.CommentReportDto;

import javax.validation.Valid;
import java.util.List;

/**
 * Interface describing event controller for Admin api.
 *
 * @author Timur Kiyamov
 * @version 1.0
 */

@RequestMapping("/admin/events")
public interface AdminEventController {

    /**
     * Endpoint of controller which get filtered events by Admin
     *
     * @param users
     * @param states
     * @param categories
     * @param rangeStart
     * @param rangeEnd
     * @param from
     * @param size
     * @return List of EventFullDto
     */
    @GetMapping
    List<EventFullDto> getFilteredEvents(@RequestParam List<Long> users,
                                         @RequestParam List<String> states,
                                         @RequestParam List<Long> categories,
                                         @RequestParam String rangeStart,
                                         @RequestParam String rangeEnd,
                                         @RequestParam Integer from,
                                         @RequestParam Integer size);

    /**
     * Endpoint of controller which update event by Admin
     *
     * @param eventId
     * @param dto
     * @return EventFullDto
     */
    @PutMapping("/{eventId}")
    EventFullDto updateEventByAdmin(@PathVariable Long eventId,
                                    @Valid @RequestBody AdminUpdateEventDto dto);

    /**
     * Endpoint of controller which publish event
     *
     * @param eventId
     * @return EventFullDto
     */
    @PatchMapping("/{eventId}/publish")
    EventFullDto publishEvent(@PathVariable Long eventId);

    /**
     * Endpoint of controller which reject event to publish
     *
     * @param eventId
     * @return EventFullDto
     */
    @PatchMapping("/{eventId}/reject")
    EventFullDto rejectEvent(@PathVariable Long eventId);

    /**
     * Endpoint of controller which delete comment by administrator or moderator
     *
     * @param eventId id of event
     * @param comId   id of comment
     */
    @DeleteMapping("{eventId}/comments/{comId}")
    void deleteCommentByAdmin(@PathVariable Long eventId, @PathVariable Long comId);

    /**
     * Endpoint of controller which get all reported comments
     *
     * @return List of CommentReportDto
     */
    @GetMapping("comments/reports")
    List<CommentReportDto> getReportedComments();

    /**
     * Endpoint of controller which get all filtered reported comments
     *
     * @param start    start time
     * @param end      end time
     * @param category category of report (check ReportName)
     * @return List of CommentReportDto
     */
    @GetMapping("comments/reports/filter")
    List<CommentReportDto> getFilteredReportedComments(@RequestParam String start,
                                                       @RequestParam String end,
                                                       @RequestParam(defaultValue = "ALL") String category);

    /**
     * Endpoint of controller which get all reported comments by report owner
     *
     * @param userId user id (report owner)
     * @return List of CommentReportDto
     */
    @GetMapping("/comments/reports/owner")
    List<CommentReportDto> getAllReportCommentsOwner(@RequestParam Long userId);

    /**
     * Endpoint of controller which delete report by id
     *
     * @param repId report id
     */
    @DeleteMapping("/comments/reports/{repId}")
    void deleteReportById(@PathVariable Long repId);
}