package ru.practicum.ewm.request.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.request.dto.ParticipationRequestDto;
import ru.practicum.ewm.request.service.EventRequestService;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}")
public class EventRequestController {

    private final EventRequestService service;

    @Autowired
    public EventRequestController(EventRequestService service) {
        this.service = service;
    }

    @GetMapping("/events/{eventId}/requests")
    public List<ParticipationRequestDto> getEventOwnerRequests(@PathVariable Long userId,
                                                               @PathVariable Long eventId) {
        return service.getEventOwnerRequests(userId, eventId);
    }

    @PatchMapping("/events/{eventId}/requests/{reqId}/confirm")
    public ParticipationRequestDto confirmEventOwnerRequest(@PathVariable Long userId,
                                                            @PathVariable Long eventId,
                                                            @PathVariable Long reqId) {
        return service.confirmEventOwnerRequest(userId,eventId,reqId);
    }

    @PatchMapping("/events/{eventId}/requests/{reqId}/reject")
    public ParticipationRequestDto rejectEventOwnerRequest(@PathVariable Long userId,
                                                           @PathVariable Long eventId,
                                                           @PathVariable Long reqId) {
        return service.rejectEventOwnerRequest(userId, eventId, reqId);
    }

    @GetMapping("/requests")
    public List<ParticipationRequestDto> getUserSelfRequestsInEvents(@PathVariable Long userId) {
        return service.getUserSelfRequestsInEvents(userId);
    }

    @PostMapping("/requests")
    public ParticipationRequestDto addRequestToEventFromUser(@PathVariable Long userId,
                                                             @RequestParam Long eventId) {
        return service.addRequestToEventFromUser(userId,eventId);
    }

    @PatchMapping("/requests/{requestId}/cancel")
    public ParticipationRequestDto cancelRequestToEventFromUser(@PathVariable Long userId,
                                                                @PathVariable Long requestId) {
        return service.cancelRequestToEventFromUser(userId,requestId);
    }
}