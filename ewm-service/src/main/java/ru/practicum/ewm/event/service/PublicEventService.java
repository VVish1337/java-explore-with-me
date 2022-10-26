package ru.practicum.ewm.event.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.mapper.EventMapper;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exception.NotFoundException;

@Service
public class PublicEventService {
    private final EventRepository eventRepository;

    @Autowired
    public PublicEventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public EventFullDto getFullEventInfoById(Long eventId) {
        return EventMapper.toFullDto(eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event not found" + eventId)));
    }


}
