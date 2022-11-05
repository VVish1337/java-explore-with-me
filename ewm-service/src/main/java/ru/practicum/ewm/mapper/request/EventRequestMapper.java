package ru.practicum.ewm.mapper.request;

import ru.practicum.ewm.dto.request.ParticipationRequestDto;
import ru.practicum.ewm.model.request.ParticipationRequest;
import ru.practicum.ewm.model.request.Status;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Final class which describes event request mapping from EventRequest to EventRequestDto
 *
 * @author Timur Kiyamov
 * @version 1.0
 */
public final class EventRequestMapper {
    public static ParticipationRequest toModel(Long userId, Long eventId, Status status) {
        return ParticipationRequest.builder()
                .created(LocalDateTime.now())
                .eventId(eventId)
                .requester(userId)
                .status(status)
                .build();
    }

    public static ParticipationRequestDto toDto(ParticipationRequest request) {
        return ParticipationRequestDto.builder()
                .id(request.getId())
                .created(request.getCreated())
                .event(request.getEventId())
                .requester(request.getRequester())
                .status(request.getStatus())
                .build();
    }

    public static List<ParticipationRequestDto> toDtoList(List<ParticipationRequest> participationRequestList) {
        return participationRequestList.stream()
                .map(EventRequestMapper::toDto)
                .collect(Collectors.toList());
    }
}