package ru.practicum.ewm.controller.request.publiccontroller;

import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.request.ParticipationRequestDto;

import java.util.List;

/**
 * Interface describing event request controller for Public api.
 *
 * @author Timur Kiyamov
 * @version 1.0
 */

@RequestMapping("/users/{userId}")
public interface EventRequestController {
    /**
     * Endpoint for controller which get event owner requests to challenge
     *
     * @param userId
     * @param eventId
     * @return List of ParticipationRequestDto
     */
    @GetMapping("/events/{eventId}/requests")
    List<ParticipationRequestDto> getEventOwnerRequests(@PathVariable Long userId,
                                                        @PathVariable Long eventId);

    /**
     * Endpoint of controller which confirm requests to event by event owner
     *
     * @param userId
     * @param eventId
     * @param reqId
     * @return ParticipationRequestDto
     */
    @PatchMapping("/events/{eventId}/requests/{reqId}/confirm")
    ParticipationRequestDto confirmEventOwnerRequest(@PathVariable Long userId,
                                                     @PathVariable Long eventId,
                                                     @PathVariable Long reqId);

    /**
     * Endpoint of controller which reject requests to event by event owner
     *
     * @param userId
     * @param eventId
     * @param reqId
     * @return ParticipationRequestDto
     */
    @PatchMapping("/events/{eventId}/requests/{reqId}/reject")
    ParticipationRequestDto rejectEventOwnerRequest(@PathVariable Long userId,
                                                    @PathVariable Long eventId,
                                                    @PathVariable Long reqId);

    /**
     * Endpoint of controller which get user self requests to event
     *
     * @param userId
     * @return List of ParticipationRequestDto
     */
    @GetMapping("/requests")
    List<ParticipationRequestDto> getUserSelfRequestsInEvents(@PathVariable Long userId);

    /**
     * Endpoint of controller which add request to challenge in event by user
     *
     * @param userId
     * @param eventId
     * @return ParticipationRequestDto
     */
    @PostMapping("/requests")
    ParticipationRequestDto addRequestToEventFromUser(@PathVariable Long userId,
                                                      @RequestParam Long eventId);

    /**
     * Endpoint of controller which cansel request to challenge in event by user
     *
     * @param userId
     * @param requestId
     * @return ParticipationRequestDto
     */
    @PatchMapping("/requests/{requestId}/cancel")
    ParticipationRequestDto cancelRequestToEventFromUser(@PathVariable Long userId,
                                                         @PathVariable Long requestId);
}