package ru.practicum.ewm.event.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.category.repository.CategoryRepository;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.dto.NewEventDto;
import ru.practicum.ewm.event.dto.UpdateEventDto;
import ru.practicum.ewm.event.mapper.EventMapper;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.PublicationState;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exception.ForbiddenException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.repository.UserRepository;
import ru.practicum.ewm.util.PaginationUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static ru.practicum.ewm.util.DefaultValues.DEFAULT_DATE_FORMATTER;

@Service
public class PrivateEventService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public PrivateEventService(EventRepository eventRepository, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }


    public EventFullDto addEvent(long userId, NewEventDto dto) {
        if (LocalDateTime.parse(dto.getEventDate(),DEFAULT_DATE_FORMATTER).isBefore(LocalDateTime.now().plusHours(2))) {
            throw new ForbiddenException("the event cannot be earlier than 2 hours from the current time");
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found+" + userId));
        Category category = categoryRepository.findById(dto.getCategory())
                .orElseThrow(() -> new NotFoundException("Category not found" + dto.getCategory()));
        return EventMapper.toFullDto(eventRepository.save(EventMapper.toEvent(dto, category, user)));
    }

    public List<EventShortDto> getUserOwnEvents(Long userId, int from, int size) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found+" + userId));
        return EventMapper.toShortDtoList(eventRepository
                .findAllByInitiatorId(userId, PaginationUtil.getPageable(from, size, Sort.unsorted()))
                .toList());
    }

    public EventFullDto getUserFullEventById(Long userId, Long eventId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found+" + userId));
        eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event not found" + eventId));
        return EventMapper.toFullDto(eventRepository.findByInitiatorIdAndId(userId, eventId));
    }


    public EventFullDto updateEventByUserOwner(Long userId, UpdateEventDto dto) {
        Event event = eventRepository.findById(dto.getEventId())
                .orElseThrow(() -> new NotFoundException("Event not found" + dto.getEventId()));
        if (!Objects.equals(event.getInitiator().getId(), userId)) {
            throw new NotFoundException("User " + userId + " not owner of this event" + event.getId());
        }
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
            if (LocalDateTime.parse(dto.getEventDate(),DEFAULT_DATE_FORMATTER).isBefore(LocalDateTime.now().plusHours(2))) {
                throw new ForbiddenException("the event cannot be earlier than 2 hours from the current time");
            }
            event.setEventDate(LocalDateTime.parse(dto.getEventDate(),DEFAULT_DATE_FORMATTER));
        }
        if (dto.getPaid() != null) {
            event.setPaid(dto.getPaid());
        }
        if (dto.getParticipantLimit() != null) {
            event.setParticipantLimit(dto.getParticipantLimit());
        }
        if (dto.getTitle() != null) {
            event.setTitle(dto.getTitle());
        }
        return EventMapper.toFullDto(eventRepository.save(event));
    }

    public EventFullDto cancelEventByUserOwner(Long userId, Long eventId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found+" + userId));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event not found" + eventId));
        if(!event.getInitiator().getId().equals(userId)){
            throw new ForbiddenException("For the requested operation the conditions are not met.");
        }
        if (event.getState().equals(PublicationState.PUBLISHED)) {
            throw new ForbiddenException("For the requested operation the conditions are not met.");
        }else {
            event.setState(PublicationState.CANCELED);
        }
        return EventMapper.toFullDto(eventRepository.save(event));
    }
}
