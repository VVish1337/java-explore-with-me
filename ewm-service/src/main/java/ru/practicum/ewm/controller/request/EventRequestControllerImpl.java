package ru.practicum.ewm.controller.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.request.ParticipationRequestDto;
import ru.practicum.ewm.service.request.EventRequestService;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}")
public class EventRequestControllerImpl implements EventRequestController {

    private final EventRequestService service;

    @Autowired
    public EventRequestControllerImpl(EventRequestService service) {
        this.service = service;
    }

    @Override
    @GetMapping("/events/{eventId}/requests")
    public List<ParticipationRequestDto> getEventOwnerRequests(@PathVariable Long userId,
                                                               @PathVariable Long eventId) {
        return service.getEventOwnerRequests(userId, eventId);
    }

    @Override
    @PatchMapping("/events/{eventId}/requests/{reqId}/confirm")
    public ParticipationRequestDto confirmEventOwnerRequest(@PathVariable Long userId,
                                                            @PathVariable Long eventId,
                                                            @PathVariable Long reqId) {
        return service.confirmEventOwnerRequest(userId, eventId, reqId);
    }

    @Override
    @PatchMapping("/events/{eventId}/requests/{reqId}/reject")
    public ParticipationRequestDto rejectEventOwnerRequest(@PathVariable Long userId,
                                                           @PathVariable Long eventId,
                                                           @PathVariable Long reqId) {
        return service.rejectEventOwnerRequest(userId, eventId, reqId);
    }

    @Override
    @GetMapping("/requests")
    public List<ParticipationRequestDto> getUserSelfRequestsInEvents(@PathVariable Long userId) {
        return service.getUserSelfRequestsInEvents(userId);
    }

    @Override
    @PostMapping("/requests")
    public ParticipationRequestDto addRequestToEventFromUser(@PathVariable Long userId,
                                                             @RequestParam Long eventId) {
        return service.addRequestToEventFromUser(userId, eventId);
    }

    @Override
    @PatchMapping("/requests/{requestId}/cancel")
    public ParticipationRequestDto cancelRequestToEventFromUser(@PathVariable Long userId,
                                                                @PathVariable Long requestId) {
        return service.cancelRequestToEventFromUser(userId, requestId);
    }
}