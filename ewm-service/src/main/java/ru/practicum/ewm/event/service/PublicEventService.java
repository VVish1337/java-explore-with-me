package ru.practicum.ewm.event.service;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.client.dto.HitDto;
import ru.practicum.ewm.client.event.EventClient;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.mapper.EventMapper;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.EventFilterParams;
import ru.practicum.ewm.event.model.PublicationState;
import ru.practicum.ewm.event.model.QEvent;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.util.PaginationUtil;
import ru.practicum.ewm.util.QPredicates;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static ru.practicum.ewm.util.DefaultValues.DEFAULT_DATE_FORMATTER;

@Service
@Transactional
public class PublicEventService {
    private final EventRepository eventRepository;

    @Autowired
    public PublicEventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public EventFullDto getFullEventInfoById(Long eventId, HttpServletRequest request) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event not found" + eventId));
        saveStat(request);
        addViewToEvent(event);
        return EventMapper.toFullDto(event);
    }


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

    private LocalDateTime checkDate(EventFilterParams params) {
        if (params.getRangeStart() == null ||
                params.getRangeEnd() == null) {
            return LocalDateTime.now();
        } else {
            return params.getRangeStart();
        }
    }

    private void saveStat(HttpServletRequest request) {
        EventClient client = new EventClient("http://localhost:9090", new RestTemplateBuilder());
        client.createHit(HitDto.builder()
                .app("ewm-main-service")
                .uri(request.getRequestURI())
                .ip(request.getRemoteAddr())
                .timestamp(LocalDateTime.now().format(DEFAULT_DATE_FORMATTER))
                .build());
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
}