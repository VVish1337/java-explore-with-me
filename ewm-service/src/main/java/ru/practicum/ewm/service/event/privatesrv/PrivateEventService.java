package ru.practicum.ewm.service.event.privatesrv;

import ru.practicum.ewm.dto.event.EventFullDto;
import ru.practicum.ewm.dto.event.EventShortDto;
import ru.practicum.ewm.dto.event.NewEventDto;
import ru.practicum.ewm.dto.event.UpdateEventDto;

import java.util.List;

public interface PrivateEventService {
    EventFullDto addEvent(long userId, NewEventDto dto);

    List<EventShortDto> getUserOwnEvents(Long userId, int from, int size);

    EventFullDto getUserFullEventById(Long userId, Long eventId);

    EventFullDto updateEventByUserOwner(Long userId, UpdateEventDto dto);

    EventFullDto cancelEventByUserOwner(Long userId, Long eventId);
}
