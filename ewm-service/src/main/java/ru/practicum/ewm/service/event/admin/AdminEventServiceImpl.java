package ru.practicum.ewm.service.event.admin;

import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.event.AdminUpdateEventDto;
import ru.practicum.ewm.dto.event.CommentDto;
import ru.practicum.ewm.dto.event.EventFullDto;
import ru.practicum.ewm.exception.ForbiddenException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.mapper.event.EventMapper;
import ru.practicum.ewm.model.category.Category;
import ru.practicum.ewm.model.event.*;
import ru.practicum.ewm.repository.category.CategoryRepository;
import ru.practicum.ewm.repository.event.CommentRepository;
import ru.practicum.ewm.repository.event.EventRepository;
import ru.practicum.ewm.util.DateFormatter;
import ru.practicum.ewm.util.PaginationUtil;
import ru.practicum.ewm.util.QPredicates;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static ru.practicum.ewm.util.DefaultValues.*;

/**
 * Class which describes Event service of Admin api
 *
 * @author Timur Kiyamov
 * @version 1.0
 */
@Service
public class AdminEventServiceImpl implements AdminEventService {
    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public AdminEventServiceImpl(EventRepository eventRepository, CategoryRepository categoryRepository, CommentRepository commentRepository) {
        this.eventRepository = eventRepository;
        this.categoryRepository = categoryRepository;
        this.commentRepository = commentRepository;
    }

    /**
     * Method of service which update Event by administrator user
     *
     * @param eventId
     * @param dto
     * @return EventFullDto
     */
    @Override
    public EventFullDto updateEventByAdmin(Long eventId, AdminUpdateEventDto dto) {
        Objects.requireNonNull(dto);
        Event event = getEvent(eventId);
        if (dto.getAnnotation() != null) {
            event.setAnnotation(dto.getAnnotation());
        }
        if (dto.getCategory() != null) {
            Category category = categoryRepository.findById(dto.getCategory())
                    .orElseThrow(() -> new NotFoundException(CATEGORY_NOT_FOUND + dto.getCategory()));
            event.setCategory(category);
        }
        if (dto.getDescription() != null) {
            event.setDescription(dto.getDescription());
        }
        if (dto.getEventDate() != null) {
            if (DateFormatter.stringToDate(dto.getEventDate()).isBefore(LocalDateTime.now().plusHours(2))) {
                throw new ForbiddenException(WRONG_DATE);
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


    /**
     * Method of service which publish Event by administrator user
     *
     * @param eventId
     * @return EventFullDto
     */
    @Override
    public EventFullDto publishEvent(Long eventId) {
        Event event = getEvent(eventId);
        if (!event.getState().equals(PublicationState.PENDING)) {
            throw new ForbiddenException(PENDING_ERROR);
        }
        event.setState(PublicationState.PUBLISHED);
        return EventMapper.toFullDto(eventRepository.save(event));
    }

    /**
     * Method of service which reject Event publish by administrator user
     *
     * @param eventId
     * @return EventFullDto
     */
    @Override
    public EventFullDto rejectEvent(Long eventId) {
        Event event = getEvent(eventId);
        if (!event.getState().equals(PublicationState.PENDING)) {
            throw new ForbiddenException(PENDING_ERROR);
        }
        event.setState(PublicationState.CANCELED);
        return EventMapper.toFullDto(eventRepository.save(event));
    }

    /**
     * Method of service which get filtered Events by administrator user
     *
     * @param users
     * @param states
     * @param categories
     * @param rangeStart
     * @param rangeEnd
     * @param from
     * @param size
     * @return
     */
    @Override
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

    @Override
    public void deleteCommentByAdmin(Long eventId, Long comId) {
        Event event = getEvent(eventId);
        Comment comment = commentRepository.findById(comId)
                .orElseThrow(()->new NotFoundException("Comment not found id:"+comId));
        if(!comment.getEvent().getId().equals(eventId)){
            throw new ForbiddenException("Wrong eventId comment eventId:"+comment.getEvent().getId()
                    +" eventId:"+eventId);
        }
        commentRepository.deleteById(comId);
    }

    /**
     * Private method of service which check existence of event
     *
     * @param eventId
     * @return Event
     */
    private Event getEvent(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event not found id:" + eventId));
    }
}