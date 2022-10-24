package ru.practicum.ewm.request.controller;

import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.request.dto.ParticipationRequestDto;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}")
public class EventRequestController {
    @GetMapping("/events/{eventId}/requests")
    public List<ParticipationRequestDto> getEventOwnerRequests(@PathVariable Long userId,
                                                               @PathVariable Long eventId) {
        return null;
    }

    @PatchMapping("/events/{eventId}/requests/{reqId}/confirm")
    public ParticipationRequestDto confirmEventOwnerRequest(@PathVariable Long userId,
                                                            @PathVariable Long eventId,
                                                            @PathVariable Long reqId) {
        return null;
    }

    @PatchMapping("/events/{eventId}/requests/{reqId}/reject")
    public ParticipationRequestDto rejectEventOwnerRequest(@PathVariable Long userId,
                                                           @PathVariable Long eventId,
                                                           @PathVariable Long reqId) {
        return null;
    }

    @GetMapping("/requests")
    public List<ParticipationRequestDto> getUserSelfRequestsInEvents(@PathVariable Long userId) {
        return null;
    }

    @PostMapping("/requests")
    public ParticipationRequestDto addRequestToEventFromUser(@PathVariable Long userId) {
        return null;
    }

    @PatchMapping("/requests/{requestId}/cancel")
    public ParticipationRequestDto cancelRequestToEventFromUser(@PathVariable Long userId,
                                                                @PathVariable Long requestId) {
        return null;
    }
}