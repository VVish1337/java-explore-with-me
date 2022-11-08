package ru.practicum.ewm.controller.event.privatecontroller;

import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.event.EventFullDto;
import ru.practicum.ewm.dto.event.EventShortDto;
import ru.practicum.ewm.dto.event.NewEventDto;
import ru.practicum.ewm.dto.event.CommentDto;
import ru.practicum.ewm.dto.event.UpdateEventDto;

import javax.validation.constraints.Min;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

import static ru.practicum.ewm.util.DefaultValues.DEFAULT_FROM_VALUE;
import static ru.practicum.ewm.util.DefaultValues.DEFAULT_SIZE_VALUE;

/**
 * Interface describing event controller for Private api.
 * @author Timur Kiyamov
 * @version 1.0
 */

@RequestMapping(path = "/users/{userId}/events")
public interface PrivateEventController {

    /**
     * Endpoint of controller which add Event by user
     * @param userId
     * @param dto
     * @return EventFullDto
     */
    @PostMapping
    EventFullDto addEvent(@PathVariable Long userId, @RequestBody NewEventDto dto);

    /**
     * Endpoint of controller which get Events by user owner
     * @param userId
     * @param from
     * @param size
     * @return List of EventShortDto
     */
    @GetMapping
    List<EventShortDto> getUserOwnEvents(@PathVariable Long userId,
                                         @RequestParam(defaultValue = DEFAULT_FROM_VALUE) @Min(0) int from,
                                         @PositiveOrZero @RequestParam(defaultValue = DEFAULT_SIZE_VALUE)
                                         int size);

    /**
     * Endpoint of controller which update Events by user owner
     * @param userId
     * @param dto
     * @return EventFullDto
     */
    @PatchMapping
    EventFullDto updateEventByUserOwner(@PathVariable Long userId, @RequestBody UpdateEventDto dto);

    /**
     * Endpoint of controller which get event by id by user owner
     * @param userId
     * @param eventId
     * @return EventFullDto
     */
    @GetMapping("/{eventId}")
    EventFullDto getUserFullEventById(@PathVariable Long userId, @PathVariable Long eventId);

    /**
     * Endpoint of controller which cancel request to publish by user owner
     * @param userId
     * @param eventId
     * @return EventFullDto
     */
    @PatchMapping("{eventId}")
    EventFullDto cancelEventByUserOwner(@PathVariable Long userId, @PathVariable Long eventId);

    @PostMapping("{eventId}/comments")
    CommentDto addCommentToEvent(@PathVariable Long userId, @PathVariable Long eventId,
                                 @RequestParam String text);

    @PatchMapping("{eventId}/comments/{comId}")
    CommentDto updateCommentByUserOwner(@PathVariable Long userId, @PathVariable Long eventId,
                                        @PathVariable Long comId, @RequestParam String text);

    @DeleteMapping("/{eventId}/comments/{comId}/delete")
    void deleteCommentByUserOwner(@PathVariable Long userId, @PathVariable Long eventId,
                                  @PathVariable Long comId);
}