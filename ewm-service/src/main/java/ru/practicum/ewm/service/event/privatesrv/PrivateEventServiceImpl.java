package ru.practicum.ewm.service.event.privatesrv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.model.category.Category;
import ru.practicum.ewm.repository.category.CategoryRepository;
import ru.practicum.ewm.dto.event.EventFullDto;
import ru.practicum.ewm.dto.event.EventShortDto;
import ru.practicum.ewm.dto.event.NewEventDto;
import ru.practicum.ewm.dto.event.UpdateEventDto;
import ru.practicum.ewm.mapper.event.EventMapper;
import ru.practicum.ewm.model.event.Event;
import ru.practicum.ewm.model.event.PublicationState;
import ru.practicum.ewm.repository.event.EventRepository;
import ru.practicum.ewm.exception.ForbiddenException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.model.user.User;
import ru.practicum.ewm.repository.user.UserRepository;
import ru.practicum.ewm.util.PaginationUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static ru.practicum.ewm.util.DefaultValues.DEFAULT_DATE_FORMATTER;

@Service
public class PrivateEventServiceImpl implements PrivateEventService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public PrivateEventServiceImpl(EventRepository eventRepository, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public EventFullDto addEvent(long userId, NewEventDto dto) {
        if (LocalDateTime.parse(dto.getEventDate(), DEFAULT_DATE_FORMATTER).isBefore(LocalDateTime.now().plusHours(2))) {
            throw new ForbiddenException("the event cannot be earlier than 2 hours from the current time");
        }
        User user = checkUserExists(userId);
        Category category = getCategory(dto.getCategory());
        return EventMapper.toFullDto(eventRepository.save(EventMapper.toEvent(dto, category, user)));
    }

    @Override
    public List<EventShortDto> getUserOwnEvents(Long userId, int from, int size) {
        checkUserExists(userId);
        return EventMapper.toShortDtoList(eventRepository
                .findAllByInitiatorId(userId, PaginationUtil.getPageable(from, size, Sort.unsorted()))
                .toList());
    }

    @Override
    public EventFullDto getUserFullEventById(Long userId, Long eventId) {
        checkUserExists(userId);
        getEvent(eventId);
        return EventMapper.toFullDto(eventRepository.findByInitiatorIdAndId(userId, eventId));
    }

    @Override
    public EventFullDto updateEventByUserOwner(Long userId, UpdateEventDto dto) {
        Event event = getEvent(dto.getEventId());
        if (!Objects.equals(event.getInitiator().getId(), userId)) {
            throw new NotFoundException("User " + userId + " not owner of this event" + event.getId());
        }
        if (dto.getAnnotation() != null) {
            event.setAnnotation(dto.getAnnotation());
        }
        if (dto.getCategory() != null) {
            Category category = getCategory(dto.getCategory());
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

    @Override
    public EventFullDto cancelEventByUserOwner(Long userId, Long eventId) {
        checkUserExists(userId);
        Event event = getEvent(eventId);
        if (!event.getInitiator().getId().equals(userId)) {
            throw new ForbiddenException("For the requested operation the conditions are not met.");
        }
        if (event.getState().equals(PublicationState.PUBLISHED)) {
            throw new ForbiddenException("For the requested operation the conditions are not met.");
        } else {
            event.setState(PublicationState.CANCELED);
        }
        return EventMapper.toFullDto(eventRepository.save(event));
    }

    private User checkUserExists(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found id:" + userId));
    }

    private Event getEvent(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event not found id:" + eventId));
    }

    private Category getCategory(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Category not found id:" + categoryId));
    }
}