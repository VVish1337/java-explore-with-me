package ru.practicum.ewm.controller.request.publiccontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.request.ParticipationRequestDto;
import ru.practicum.ewm.service.request.privatesrv.EventRequestService;

import java.util.List;

/**
 * Class describing event request controller for Public api.
 *
 * @author Timur Kiyamov
 * @version 1.0
 */
@RestController
@RequestMapping("/users/{userId}")
public class EventRequestControllerImpl implements EventRequestController {

    private final EventRequestService service;

    @Autowired
    public EventRequestControllerImpl(EventRequestService service) {
        this.service = service;
    }

    /**
     * Endpoint for controller which get event owner requests to challenge
     *
     * @param userId
     * @param eventId
     * @return List of ParticipationRequestDto
     */
    @Override
    @GetMapping("/events/{eventId}/requests")
    public List<ParticipationRequestDto> getEventOwnerRequests(@PathVariable Long userId,
                                                               @PathVariable Long eventId) {
        return service.getEventOwnerRequests(userId, eventId);
    }

    /**
     * Endpoint of controller which confirm requests to event by event owner
     *
     * @param userId
     * @param eventId
     * @param reqId
     * @return ParticipationRequestDto
     */
    @Override
    @PatchMapping("/events/{eventId}/requests/{reqId}/confirm")
    public ParticipationRequestDto confirmEventOwnerRequest(@PathVariable Long userId,
                                                            @PathVariable Long eventId,
                                                            @PathVariable Long reqId) {
        return service.confirmEventOwnerRequest(userId, eventId, reqId);
    }

    /**
     * Endpoint of controller which reject requests to event by event owner
     *
     * @param userId
     * @param eventId
     * @param reqId
     * @return ParticipationRequestDto
     */
    @Override
    @PatchMapping("/events/{eventId}/requests/{reqId}/reject")
    public ParticipationRequestDto rejectEventOwnerRequest(@PathVariable Long userId,
                                                           @PathVariable Long eventId,
                                                           @PathVariable Long reqId) {
        return service.rejectEventOwnerRequest(userId, eventId, reqId);
    }

    /**
     * Endpoint of controller which get user self requests to event
     *
     * @param userId
     * @return List of ParticipationRequestDto
     */
    @Override
    @GetMapping("/requests")
    public List<ParticipationRequestDto> getUserSelfRequestsInEvents(@PathVariable Long userId) {
        return service.getUserSelfRequestsInEvents(userId);
    }

    /**
     * Endpoint of controller which add request to challenge in event by user
     *
     * @param userId
     * @param eventId
     * @return ParticipationRequestDto
     */
    @Override
    @PostMapping("/requests")
    public ParticipationRequestDto addRequestToEventFromUser(@PathVariable Long userId,
                                                             @RequestParam Long eventId) {
        return service.addRequestToEventFromUser(userId, eventId);
    }

    /**
     * Endpoint of controller which cansel request to challenge in event by user
     *
     * @param userId
     * @param requestId
     * @return ParticipationRequestDto
     */
    @Override
    @PatchMapping("/requests/{requestId}/cancel")
    public ParticipationRequestDto cancelRequestToEventFromUser(@PathVariable Long userId,
                                                                @PathVariable Long requestId) {
        return service.cancelRequestToEventFromUser(userId, requestId);
    }
}