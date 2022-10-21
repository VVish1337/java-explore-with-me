package ru.practicum.ewm.event.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.event.dto.EventResponseDto;
import ru.practicum.ewm.event.dto.NewEventDto;
import ru.practicum.ewm.event.repository.EventRepository;

import java.util.List;

@Service
public class PrivateEventService {
    private final EventRepository eventRepository;

    @Autowired
    public PrivateEventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }


    public EventResponseDto addEvent(long userId, NewEventDto dto) {
        return null;
    }

    public List<EventResponseDto> getUserOwnEvents(Long userId, int from, int size) {
        return null;
    }
}
