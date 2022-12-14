package ru.practicum.ewm.mapper.event;

import ru.practicum.ewm.dto.event.EventFullDto;
import ru.practicum.ewm.dto.event.EventShortDto;
import ru.practicum.ewm.dto.event.NewEventDto;
import ru.practicum.ewm.mapper.category.CategoryMapper;
import ru.practicum.ewm.mapper.user.UserMapper;
import ru.practicum.ewm.model.category.Category;
import ru.practicum.ewm.model.event.Event;
import ru.practicum.ewm.model.event.PublicationState;
import ru.practicum.ewm.model.user.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.ewm.util.DefaultValues.DEFAULT_DATE_FORMATTER;

/**
 * Final class which describes event mapping from Event to EventDto
 *
 * @author Timur Kiyamov
 * @version 1.0
 */
public final class EventMapper {


    public static EventFullDto toFullDto(Event event) {
        return EventFullDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(CategoryMapper.toDto(event.getCategory()))
                .confirmedRequests(event.getConfirmedRequests())
                .createdOn(event.getCreatedOn().format(DEFAULT_DATE_FORMATTER))
                .description(event.getDescription())
                .initiator(UserMapper.toShortDto(event.getInitiator()))
                .location(event.getLocation())
                .eventDate(event.getEventDate().format(DEFAULT_DATE_FORMATTER))
                .paid(event.getPaid())
                .participantLimit(event.getParticipantLimit())
                .publishedOn(event.getEventDate().format(DEFAULT_DATE_FORMATTER))
                .requestModeration(event.getRequestModeration())
                .state(event.getState())
                .title(event.getTitle())
                .views(event.getViews())
                .build();
    }

    public static EventShortDto toShortDto(Event event) {
        return EventShortDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(CategoryMapper.toDto(event.getCategory()))
                .confirmedRequests(event.getConfirmedRequests())
                .eventDate(event.getEventDate().format(DEFAULT_DATE_FORMATTER))
                .initiator(UserMapper.toShortDto(event.getInitiator()))
                .paid(event.getPaid())
                .title(event.getTitle())
                .views(event.getViews())
                .build();
    }

    public static Event toEvent(NewEventDto dto, Category category, User user) {
        return Event.builder()
                .annotation(dto.getAnnotation())
                .category(category)
                .confirmedRequests(0)
                .createdOn(LocalDateTime.now())
                .description(dto.getDescription())
                .initiator(user)
                .location(dto.getLocation())
                .eventDate(LocalDateTime.parse(dto.getEventDate(), DEFAULT_DATE_FORMATTER))
                .paid(dto.getPaid())
                .participantLimit(dto.getParticipantLimit())
                .publishedOn(null)
                .requestModeration(dto.getRequestModeration())
                .state(PublicationState.PENDING)
                .title(dto.getTitle())
                .views(0L).build();
    }

    public static List<EventShortDto> toShortDtoList(List<Event> eventList) {
        return eventList.stream()
                .map(EventMapper::toShortDto)
                .collect(Collectors.toList());
    }

    public static List<EventFullDto> toFullDtoList(List<Event> eventList) {
        return eventList.stream()
                .map(EventMapper::toFullDto)
                .collect(Collectors.toList());
    }

    public static List<PublicationState> toState(List<String> states) {
        return states.stream()
                .map(PublicationState::valueOf)
                .collect(Collectors.toList());
    }
}