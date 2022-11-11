package ru.practicum.ewm.service.event.publicsrv;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.client.EventClient;
import ru.practicum.ewm.dto.HitDto;
import ru.practicum.ewm.dto.event.EventFullDto;
import ru.practicum.ewm.dto.event.EventShortDto;
import ru.practicum.ewm.dto.event.EventWithCommentsDto;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.mapper.event.EventMapper;
import ru.practicum.ewm.model.event.Event;
import ru.practicum.ewm.model.event.EventFilterParams;
import ru.practicum.ewm.model.event.PublicationState;
import ru.practicum.ewm.model.event.QEvent;
import ru.practicum.ewm.model.event.comment.Comment;
import ru.practicum.ewm.repository.event.CommentRepository;
import ru.practicum.ewm.repository.event.EventRepository;
import ru.practicum.ewm.util.DateFormatter;
import ru.practicum.ewm.util.PaginationUtil;
import ru.practicum.ewm.util.QPredicates;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.ewm.util.DefaultValues.EVENT_NOT_FOUND;

/**
 * Class which describes Event service of Public api
 *
 * @author Timur Kiyamov
 * @version 1.0
 */
@Slf4j
@Service
@Transactional
public class PublicEventServiceImpl implements PublicEventService {
    private final EventRepository eventRepository;
    private final CommentRepository commentRepository;
    private Sort sortType;

    @Autowired
    public PublicEventServiceImpl(EventRepository eventRepository, CommentRepository commentRepository) {
        this.eventRepository = eventRepository;
        this.commentRepository = commentRepository;
    }

    /**
     * Method of service which get full information about Event by ID
     *
     * @param eventId
     * @param request
     * @return EventFullDto
     */
    @Override
    public EventFullDto getFullEventInfoById(Long eventId, HttpServletRequest request) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(EVENT_NOT_FOUND + eventId));
        saveStat(request);
        addViewToEvent(event);
        return EventMapper.toFullDto(event);
    }

    /**
     * Method of service which get filtered Event list
     *
     * @param text
     * @param categories
     * @param paid
     * @param rangeStart
     * @param rangeEnd
     * @param onlyAvailable
     * @param sort
     * @param from
     * @param size
     * @param request
     * @return List of EventShortDto
     */
    @Override
    public List<EventShortDto> getFilteredEvents(String text, List<Long> categories,
                                                 Boolean paid, String rangeStart,
                                                 String rangeEnd, Boolean onlyAvailable,
                                                 String sort, Integer from, Integer size, HttpServletRequest request) {
        EventFilterParams params = new EventFilterParams(text, categories,
                paid, rangeStart, rangeEnd, onlyAvailable, from, size);
        Predicate predicate = getPredicates(params);
        if (sort.equals("EVENT_DATE")) {
            sort = "eventDate";
        }
        saveStat(request);
        List<Event> eventList = eventRepository
                .findAll(predicate, PaginationUtil.getPageable(from, size, Sort.by(sort))).toList();
        addViewToEventList(eventList);
        return EventMapper.toShortDtoList(eventList);
    }

    /**
     * Method of service which get Event with comments
     *
     * @param eventId id of event
     * @return EventWithCommentsDto
     */
    @Override
    public EventWithCommentsDto getEventWithComments(Long eventId) {
        Event event = getEvent(eventId);
        List<Comment> commentList = commentRepository.findAllByEventId(eventId);
        return EventMapper.toEventWithCommentsDto(event, commentList);
    }

    /**
     * Method of service which get Event with comments list
     *
     * @param sort param which describes how to sort ascending,descending
     * @return List of EventWithCommentsDto
     */
    @Override
    public List<EventWithCommentsDto> getEventWithCommentsList(String sort) {
        if (sort.equals("asc")) {
            sortType = Sort.by("createdOn").ascending();
        }
        if (sort.equals("desc")) {
            sortType = Sort.by("createdOn").descending();
        }
        List<Event> eventList = eventRepository.findAll();
        List<Long> ids = eventList.stream()
                .map(Event::getId)
                .collect(Collectors.toList());
        List<Comment> comments = commentRepository.findByEventIds(ids, sortType);
        return EventMapper.toShortDtoWithCommentList(eventList, comments);
    }

    /**
     * Private method of service which get predicates from EventFilterParams
     *
     * @param params
     * @return EventFilterParams
     */
    private Predicate getPredicates(EventFilterParams params) {
        LocalDateTime timeNow = checkDate(params);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(QPredicates.builder()
                .add(params.getText(), QEvent.event.annotation::likeIgnoreCase)
                .add(params.getText(), QEvent.event.description::likeIgnoreCase)
                .buildOr());

        predicates.add(QPredicates.builder()
                .add(params.getCategories(), QEvent.event.category.id::in)
                .add(params.getPaid(), QEvent.event.paid::eq)
                .add(timeNow, QEvent.event.eventDate::goe)
                .add(params.getRangeEnd(), QEvent.event.eventDate::loe)
                .add(PublicationState.PUBLISHED, QEvent.event.state::eq)
                .buildAnd());
        return ExpressionUtils.allOf(predicates);
    }

    /**
     * Private method of service which check filter param date of Event on null
     *
     * @param params
     * @return LocalDateTime
     */
    private LocalDateTime checkDate(EventFilterParams params) {
        if (params.getRangeStart() == null ||
                params.getRangeEnd() == null) {
            return LocalDateTime.now();
        } else {
            return params.getRangeStart();
        }
    }

    /**
     * Private method of service Client which send statistic of viewer to statistic service
     *
     * @param request
     */
    private void saveStat(HttpServletRequest request) {
        try {
            EventClient client = new EventClient("http://localhost:9090", new RestTemplateBuilder());
            client.createHit(HitDto.builder()
                    .app("ewm-main-service")
                    .uri(request.getRequestURI())
                    .ip(request.getRemoteAddr())
                    .timestamp(DateFormatter.formatDate(LocalDateTime.now()))
                    .build());
        } catch (Exception e) {
            log.warn("Stat server offline.Stacktrace:{}", Arrays.toString(e.getStackTrace()));
        }
    }

    private void addViewToEventList(List<Event> eventList) {
        for (Event event : eventList) {
            event.setViews(event.getViews() + 1);
            eventRepository.save(event);
        }
    }

    private void addViewToEvent(Event event) {
        event.setViews(event.getViews() + 1);
        eventRepository.save(event);
    }

    private Event getEvent(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(EVENT_NOT_FOUND));
    }
}