package ru.practicum.ewm.service.event.privatesrv.admin;

import ru.practicum.ewm.dto.event.AdminUpdateEventDto;
import ru.practicum.ewm.dto.event.EventFullDto;

import java.util.List;

public interface AdminEventService {
    EventFullDto updateEventByAdmin(Long eventId, AdminUpdateEventDto dto);

    EventFullDto publishEvent(Long eventId);

    EventFullDto rejectEvent(Long eventId);

    List<EventFullDto> getFilteredEvents(List<Long> users, List<String> states,
                                         List<Long> categories, String rangeStart,
                                         String rangeEnd, Integer from, Integer size);
}
