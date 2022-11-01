package ru.practicum.ewm.dto.request;

import lombok.*;
import ru.practicum.ewm.model.request.Status;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParticipationRequestDto {
    private Long id;
    private LocalDateTime created;
    private Long event;
    private Long requester;
    private Status status;
}