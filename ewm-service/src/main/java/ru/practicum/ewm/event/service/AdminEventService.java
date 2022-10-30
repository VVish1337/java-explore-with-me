package ru.practicum.ewm.event.service;

import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.category.repository.CategoryRepository;
import ru.practicum.ewm.event.dto.AdminUpdateEventDto;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.mapper.EventMapper;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.EventFilterParams;
import ru.practicum.ewm.event.model.PublicationState;
import ru.practicum.ewm.event.model.QEvent;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exception.ForbiddenException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.util.PaginationUtil;
import ru.practicum.ewm.util.QPredicates;

import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.ewm.util.DefaultValues.DEFAULT_DATE_FORMATTER;

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
        Event event = getEvent(eventId);
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
            if (LocalDateTime.parse(dto.getEventDate(), DEFAULT_DATE_FORMATTER)
                    .isBefore(LocalDateTime.now().plusHours(2))) {
                throw new ForbiddenException("the event cannot be earlier than 2 hours from the current time");
            }
            event.setEventDate(LocalDateTime.parse(dto.getEventDate(), DEFAULT_DATE_FORMATTER));
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
        Event event = getEvent(eventId);
        if (!event.getState().equals(PublicationState.PENDING)) {
            throw new ForbiddenException("Only pending events can be changed");
        }
        event.setState(PublicationState.PUBLISHED);
        return EventMapper.toFullDto(eventRepository.save(event));
    }

    public EventFullDto rejectEvent(Long eventId) {
        Event event = getEvent(eventId);
        if (!event.getState().equals(PublicationState.PENDING)) {
            throw new ForbiddenException("Only pending events can be changed");
        }
        event.setState(PublicationState.CANCELED);
        return EventMapper.toFullDto(eventRepository.save(event));
    }

    public List<EventFullDto> getFilteredEvents(List<Long> users, List<String> states,
                                                List<Long> categories, String rangeStart,
                                                String rangeEnd, Integer from, Integer size) {
        EventFilterParams params = new EventFilterParams(
                users, states, categories, rangeStart, rangeEnd, from, size);
        Predicate predicate = QPredicates.builder()
                .add(params.getUsers(), QEvent.event.initiator.id::in)
                .add(params.getStates(), QEvent.event.state::in)
                .add(params.getCategories(), QEvent.event.category.id::in)
                .add(params.getRangeStart(), QEvent.event.eventDate::goe)
                .add(params.getRangeEnd(), QEvent.event.eventDate::loe)
                .buildAnd();
        return EventMapper.toFullDtoList(eventRepository.findAll(predicate,
                PaginationUtil.getPageable(from, size, Sort.unsorted())).toList());
    }

    private Event getEvent(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event not found id:" + eventId));
    }
}