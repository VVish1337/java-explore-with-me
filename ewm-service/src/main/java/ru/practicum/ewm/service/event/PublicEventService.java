package ru.practicum.ewm.service.event;

import ru.practicum.ewm.dto.event.EventFullDto;
import ru.practicum.ewm.dto.event.EventShortDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface PublicEventService {
    EventFullDto getFullEventInfoById(Long eventId, HttpServletRequest request);

    List<EventShortDto> getFilteredEvents(String text, List<Long> categories,
                                          Boolean paid, String rangeStart,
                                          String rangeEnd, Boolean onlyAvailable,
                                          String sort, Integer from, Integer size, HttpServletRequest request);
}
