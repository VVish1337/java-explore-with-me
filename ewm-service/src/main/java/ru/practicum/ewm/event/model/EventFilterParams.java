package ru.practicum.ewm.event.model;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.ewm.event.mapper.EventMapper;

import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.ewm.util.DefaultValues.DEFAULT_DATE_FORMATTER;

@Getter
@Setter
public class EventFilterParams {
    private List<Long> users;
    private String text;
    private List<PublicationState> states;
    private List<Long> categories;
    private LocalDateTime rangeStart;
    private LocalDateTime rangeEnd;
    private Boolean paid;
    private Boolean onlyAvailable;
    private Integer from;
    private Integer size;
    private String sort;

    public EventFilterParams(String text, List<Long> categories, Boolean paid,
                             String rangeStart, String rangeEnd, Boolean onlyAvailable,
                             Integer from, Integer size) {
        if (text != null) {
            this.text = text.toLowerCase();
        }
        this.categories = categories;
        this.paid = paid;
        if (rangeStart != null) {
            this.rangeStart = LocalDateTime.parse(rangeStart, DEFAULT_DATE_FORMATTER);
        }
        if (rangeEnd != null) {
            this.rangeEnd = LocalDateTime.parse(rangeEnd, DEFAULT_DATE_FORMATTER);
        }
        this.onlyAvailable = onlyAvailable;
        this.from = from;
        this.size = size;
    }

    public EventFilterParams(List<Long> users, List<String> states,
                             List<Long> categories, String rangeStart,
                             String rangeEnd, Integer from, Integer size) {

        this.users = users;
        this.states = EventMapper.toState(states);
        this.categories = categories;
        if (rangeStart != null) {
            this.rangeStart = LocalDateTime.parse(rangeStart, DEFAULT_DATE_FORMATTER);
        }
        if (rangeEnd != null) {
            this.rangeEnd = LocalDateTime.parse(rangeEnd, DEFAULT_DATE_FORMATTER);
        }
        this.from = from;
        this.size = size;
    }
}