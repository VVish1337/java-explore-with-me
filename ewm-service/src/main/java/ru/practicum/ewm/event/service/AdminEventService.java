package ru.practicum.ewm.event.service;

import org.springframework.stereotype.Service;
import ru.practicum.ewm.event.repository.EventRepository;

@Service
public class AdminEventService {
    private final EventRepository eventRepository;

    public AdminEventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }
}
