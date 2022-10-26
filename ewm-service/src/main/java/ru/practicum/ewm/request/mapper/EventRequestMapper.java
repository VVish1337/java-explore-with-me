package ru.practicum.ewm.request.mapper;

import ru.practicum.ewm.request.dto.ParticipationRequestDto;
import ru.practicum.ewm.request.model.ParticipationRequest;
import ru.practicum.ewm.request.model.Status;

import java.time.LocalDateTime;

public class EventRequestMapper {
    public static ParticipationRequest toModel(Long userId, Long eventId, Status status){
        return ParticipationRequest.builder()
                .created(LocalDateTime.now())
                .eventId(eventId)
                .requester(userId)
                .status(status)
                .build();
    }

    public static ParticipationRequestDto toDto(ParticipationRequest request){
        return ParticipationRequestDto.builder()
                .id(request.getId())
                .created(request.getCreated())
                .eventId(request.getId())
                .requester(request.getRequester())
                .status(request.getStatus())
                .build();
    }
}
