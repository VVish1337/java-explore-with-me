package ru.practicum.ewm.controller.request;

import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.request.ParticipationRequestDto;

import java.util.List;

@RequestMapping("/users/{userId}")
public interface EventRequestController {
    @GetMapping("/events/{eventId}/requests")
    List<ParticipationRequestDto> getEventOwnerRequests(@PathVariable Long userId,
                                                        @PathVariable Long eventId);

    @PatchMapping("/events/{eventId}/requests/{reqId}/confirm")
    ParticipationRequestDto confirmEventOwnerRequest(@PathVariable Long userId,
                                                     @PathVariable Long eventId,
                                                     @PathVariable Long reqId);

    @PatchMapping("/events/{eventId}/requests/{reqId}/reject")
    ParticipationRequestDto rejectEventOwnerRequest(@PathVariable Long userId,
                                                    @PathVariable Long eventId,
                                                    @PathVariable Long reqId);

    @GetMapping("/requests")
    List<ParticipationRequestDto> getUserSelfRequestsInEvents(@PathVariable Long userId);

    @PostMapping("/requests")
    ParticipationRequestDto addRequestToEventFromUser(@PathVariable Long userId,
                                                      @RequestParam Long eventId);

    @PatchMapping("/requests/{requestId}/cancel")
    ParticipationRequestDto cancelRequestToEventFromUser(@PathVariable Long userId,
                                                         @PathVariable Long requestId);
}