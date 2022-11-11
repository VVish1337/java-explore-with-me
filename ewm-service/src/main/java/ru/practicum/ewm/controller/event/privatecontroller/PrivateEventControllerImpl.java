package ru.practicum.ewm.controller.event.privatecontroller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.event.EventFullDto;
import ru.practicum.ewm.dto.event.EventShortDto;
import ru.practicum.ewm.dto.event.NewEventDto;
import ru.practicum.ewm.dto.event.UpdateEventDto;
import ru.practicum.ewm.dto.event.comment.CommentDto;
import ru.practicum.ewm.dto.event.comment.CommentReportDto;
import ru.practicum.ewm.service.event.privatesrv.PrivateEventService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

import static ru.practicum.ewm.util.DefaultValues.DEFAULT_FROM_VALUE;
import static ru.practicum.ewm.util.DefaultValues.DEFAULT_SIZE_VALUE;

/**
 * Class describing event controller for Private api.
 *
 * @author Timur Kiyamov
 * @version 1.0
 */

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

    /**
     * Endpoint of controller which add Event by user
     *
     * @param userId
     * @param dto
     * @return EventFullDto
     */
    @Override
    @PostMapping
    public EventFullDto addEvent(@PathVariable Long userId, @Valid @RequestBody NewEventDto dto) {
        log.info("post event {}, owner id:{}", dto, userId);
        return service.addEvent(userId, dto);
    }

    /**
     * Endpoint of controller which get Events by user owner
     *
     * @param userId
     * @param from
     * @param size
     * @return List of EventShortDto
     */
    @Override
    @GetMapping
    public List<EventShortDto> getUserOwnEvents(@PathVariable Long userId,
                                                @RequestParam(defaultValue = DEFAULT_FROM_VALUE) @Min(0) int from,
                                                @PositiveOrZero @RequestParam(defaultValue = DEFAULT_SIZE_VALUE)
                                                int size) {
        log.info("get owner list event owner id:{}", userId);
        return service.getUserOwnEvents(userId, from, size);
    }

    /**
     * Endpoint of controller which update Events by user owner
     *
     * @param userId
     * @param dto
     * @return EventFullDto
     */
    @Override
    @PatchMapping
    public EventFullDto updateEventByUserOwner(@PathVariable Long userId, @RequestBody UpdateEventDto dto) {
        log.info("update event {}, owner id:{}", dto, userId);
        return service.updateEventByUserOwner(userId, dto);
    }

    /**
     * Endpoint of controller which get event by id by user owner
     *
     * @param userId
     * @param eventId
     * @return EventFullDto
     */
    @Override
    @GetMapping("/{eventId}")
    public EventFullDto getUserFullEventById(@PathVariable Long userId, @PathVariable Long eventId) {
        log.info("get full event {}, owner id:{}", eventId, userId);
        return service.getUserFullEventById(userId, eventId);
    }

    /**
     * Endpoint of controller which cancel request to publish by user owner
     *
     * @param userId
     * @param eventId
     * @return EventFullDto
     */
    @Override
    @PatchMapping("{eventId}")
    public EventFullDto cancelEventByUserOwner(@PathVariable Long userId, @PathVariable Long eventId) {
        log.info("cancel event by id:{} and owner id:{}", eventId, userId);
        return service.cancelEventByUserOwner(userId, eventId);
    }

    /**
     * Endpoint of controller which add comment event
     *
     * @param userId  id of user
     * @param eventId id of event
     * @param text    text of comment
     * @return CommentDto
     */
    @Override
    @PostMapping("{eventId}/comments")
    public CommentDto addCommentToEvent(@PathVariable Long userId, @PathVariable Long eventId,
                                        @RequestParam String text) {
        log.info("Add comment to event event id:{},user id:{},text:{}", eventId, userId, text);
        return service.addCommentToEvent(userId, eventId, text);
    }

    /**
     * Endpoint of controller which update added comment
     *
     * @param userId  id of user
     * @param eventId id of event
     * @param comId   id of comment
     * @param text    text of comment which will update old text
     * @return CommentDto
     */
    @Override
    @PatchMapping("/{eventId}/comments/{comId}")
    public CommentDto updateCommentByUserOwner(@PathVariable Long userId, @PathVariable Long eventId,
                                               @PathVariable Long comId, @RequestParam String text) {
        return service.updateCommentByUserOwner(userId, eventId, comId, text);
    }

    /**
     * Endpoint of controller which delete comment by owner
     *
     * @param userId  id of user (owner of comment)
     * @param eventId id of event
     * @param comId   id of comment
     */
    @Override
    @DeleteMapping("/{eventId}/comments/{comId}/delete")
    public void deleteCommentByUserOwner(@PathVariable Long userId, @PathVariable Long eventId,
                                         @PathVariable Long comId) {
        service.deleteCommentByUserOwner(userId, eventId, comId);
    }

    /**
     * Endpoint of controller which add report to bad comment
     *
     * @param userId   id of user
     * @param eventId  id of event
     * @param comId    id of comment
     * @param category category of report (check ReportName)
     * @return CommentReportDto
     */
    @Override
    @PostMapping("/{eventId}/comments/{comId}/report")
    public CommentReportDto addReportToComment(@PathVariable Long userId, @PathVariable Long eventId,
                                               @PathVariable Long comId, @RequestParam String category) {
        return service.addReportToComment(userId, eventId, comId, category);
    }
}