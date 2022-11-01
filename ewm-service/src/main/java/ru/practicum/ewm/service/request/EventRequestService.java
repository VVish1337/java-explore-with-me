package ru.practicum.ewm.service.request;

import ru.practicum.ewm.dto.request.ParticipationRequestDto;

import java.util.List;

public interface EventRequestService {
    ParticipationRequestDto addRequestToEventFromUser(Long userId, Long eventId);

    ParticipationRequestDto cancelRequestToEventFromUser(Long userId, Long requestId);

    List<ParticipationRequestDto> getUserSelfRequestsInEvents(Long userId);

    List<ParticipationRequestDto> getEventOwnerRequests(Long userId, Long eventId);

    ParticipationRequestDto confirmEventOwnerRequest(Long userId, Long eventId, Long reqId);

    ParticipationRequestDto rejectEventOwnerRequest(Long userId, Long eventId, Long reqId);
}
