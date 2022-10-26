package ru.practicum.ewm.event.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.category.repository.CategoryRepository;
import ru.practicum.ewm.event.dto.AdminUpdateEventDto;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.mapper.EventMapper;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.PublicationState;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exception.ForbiddenException;
import ru.practicum.ewm.exception.NotFoundException;

import java.time.LocalDateTime;

@Service
public class AdminEventService {
    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public AdminEventService(EventRepository eventRepository, CategoryRepository categoryRepository) {
        this.eventRepository = eventRepository;
        this.categoryRepository = categoryRepository;
    }

    public EventFullDto updateEventByAdmin(Long eventId, AdminUpdateEventDto dto) {
      Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event not found" + eventId));
        if (dto.getAnnotation() != null) {
            event.setAnnotation(dto.getAnnotation());
        }
        if (dto.getCategory() != null) {
            Category category = categoryRepository.findById(dto.getCategory())
                    .orElseThrow(() -> new NotFoundException("Category not found" + dto.getCategory()));
            event.setCategory(category);
        }
        if (dto.getDescription() != null) {
            event.setDescription(dto.getDescription());
        }
        if (dto.getEventDate() != null) {
            if (dto.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
                throw new ForbiddenException("the event cannot be earlier than 2 hours from the current time");
            }
            event.setEventDate(dto.getEventDate());
        }
        if (dto.getLocation() != null) {
            event.setLocation(dto.getLocation());
        }
        if (dto.getParticipantLimit() != null) {
            event.setParticipantLimit(dto.getParticipantLimit());
        }
        if (dto.getTitle() != null) {
            event.setTitle(dto.getTitle());
        }
        if (dto.getPaid() != null) {
            event.setPaid(dto.getPaid());
        }
        if (dto.getRequestModeration() != null) {
            event.setRequestModeration(dto.getRequestModeration());
        }
        return EventMapper.toFullDto(eventRepository.save(event));
    }


    public EventFullDto publishEvent(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event not found" + eventId));
        if(!event.getState().equals(PublicationState.PENDING)){
            throw new ForbiddenException("Only pending events can be changed");
        }
        event.setState(PublicationState.PUBLISHED);
        return EventMapper.toFullDto(eventRepository.save(event));
    }

    public EventFullDto rejectEvent(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event not found" + eventId));
        if(!event.getState().equals(PublicationState.PENDING)){
            throw new ForbiddenException("Only pending events can be changed");
        }
        event.setState(PublicationState.CANCELED);
        return EventMapper.toFullDto(eventRepository.save(event));
    }
}
