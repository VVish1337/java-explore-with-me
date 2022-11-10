package ru.practicum.ewm.controller.event.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.event.AdminUpdateEventDto;
import ru.practicum.ewm.dto.event.EventFullDto;
import ru.practicum.ewm.dto.event.comment.CommentReportDto;
import ru.practicum.ewm.service.event.admin.AdminEventService;

import java.util.List;

/**
 * Class describing event controller for Admin api.
 *
 * @author Timur Kiyamov
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/admin/events")
public class AdminEventControllerImpl implements AdminEventController {

    private final AdminEventService service;

    @Autowired
    public AdminEventControllerImpl(AdminEventService service) {
        this.service = service;
    }

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
    @Override
    @GetMapping
    public List<EventFullDto> getFilteredEvents(@RequestParam List<Long> users,
                                                @RequestParam List<String> states,
                                                @RequestParam List<Long> categories,
                                                @RequestParam String rangeStart,
                                                @RequestParam String rangeEnd,
                                                @RequestParam Integer from,
                                                @RequestParam Integer size) {
        log.info("get Filtered events ");
        return service.getFilteredEvents(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    /**
     * Endpoint of controller which update event by Admin
     *
     * @param eventId
     * @param dto
     * @return EventFullDto
     */
    @Override
    @PutMapping("/{eventId}")
    public EventFullDto updateEventByAdmin(@PathVariable Long eventId,
                                           @RequestBody AdminUpdateEventDto dto) {
        log.info("update event by admin id:{},dto:{}", eventId, dto);
        return service.updateEventByAdmin(eventId, dto);
    }

    /**
     * Endpoint of controller which publish event
     *
     * @param eventId
     * @return EventFullDto
     */
    @Override
    @PatchMapping("/{eventId}/publish")
    public EventFullDto publishEvent(@PathVariable Long eventId) {
        log.info("publish event id:" + eventId);
        return service.publishEvent(eventId);
    }

    /**
     * Endpoint of controller which reject event to publish
     *
     * @param eventId
     * @return EventFullDto
     */
    @Override
    @PatchMapping("/{eventId}/reject")
    public EventFullDto rejectEvent(@PathVariable Long eventId) {
        log.info("reject event id:" + eventId);
        return service.rejectEvent(eventId);
    }

    /**
     * Endpoint of controller which delete comment by administrator or moderator
     *
     * @param eventId id of event
     * @param comId   id of comment
     */
    @Override
    @DeleteMapping("{eventId}/comments/{comId}")
    public void deleteCommentByAdmin(@PathVariable Long eventId, @PathVariable Long comId) {
        log.info("Delete comment by admin eventId:{},comId{}", eventId, comId);
        service.deleteCommentByAdmin(eventId, comId);
    }

    /**
     * Endpoint of controller which get all reported comments
     *
     * @return List of CommentReportDto
     */
    @Override
    @GetMapping("/comments/reports")
    public List<CommentReportDto> getReportedComments() {
        log.info("Get reported comments");
        return service.getReportedComments();
    }

    /**
     * Endpoint of controller which get all filtered reported comments
     *
     * @param start    start time
     * @param end      end time
     * @param category category of report (check ReportName)
     * @return List of CommentReportDto
     */
    @Override
    @GetMapping("/comments/reports/filter")
    public List<CommentReportDto> getFilteredReportedComments(@RequestParam String start,
                                                              @RequestParam String end,
                                                              @RequestParam String category) {
        log.info("Get filtered reported comments start:{},end:{},category:{}", start, end, category);
        return service.getFilteredReportedComments(start, end, category);
    }

    /**
     * Endpoint of controller which get all reported comments by report owner
     *
     * @param userId user id (report owner)
     * @return List of CommentReportDto
     */
    @Override
    @GetMapping("/comments/reports/owner")
    public List<CommentReportDto> getAllReportCommentsOwner(@RequestParam Long userId) {
        log.info("Get all report comments owner userId:{}", userId);
        return service.getAllReportCommentsOwner(userId);
    }

    /**
     * Endpoint of controller which delete report by id
     *
     * @param repId report id
     */
    @Override
    @DeleteMapping("/comments/reports/{repId}")
    public void deleteReportById(@PathVariable Long repId) {
        log.info("Delete report by id");
        service.deleteReportById(repId);
    }
}