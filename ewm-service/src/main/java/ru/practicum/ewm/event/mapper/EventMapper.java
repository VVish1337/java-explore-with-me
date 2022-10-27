package ru.practicum.ewm.event.mapper;

import ru.practicum.ewm.category.mapper.CategoryMapper;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.dto.NewEventDto;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.PublicationState;
import ru.practicum.ewm.user.mapper.UserMapper;
import ru.practicum.ewm.user.model.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.ewm.util.DefaultValues.DEFAULT_DATE_FORMATTER;
import static ru.practicum.ewm.util.DefaultValues.DEFAULT_DATE_TIME_PATTERN;

public class EventMapper {


    public static EventFullDto toFullDto(Event event){
//        if(event.getPublishedOn()!=null){
//            event.getEventDate().format(DEFAULT_DATE_FORMATTER);
//        }
        return EventFullDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(CategoryMapper.toDto(event.getCategory()))
                .confirmedRequests(event.getConfirmedRequests())
                .createdOn(event.getCreatedOn().format(DEFAULT_DATE_FORMATTER))
                .description(event.getDescription())
                .initiator(UserMapper.userToShortDto(event.getInitiator()))
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

    public static EventShortDto toShortDto(Event event){
        return EventShortDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(CategoryMapper.toDto(event.getCategory()))
                .confirmedRequests(event.getConfirmedRequests())
                .eventDate(event.getEventDate().format(DEFAULT_DATE_FORMATTER))
                .initiator(UserMapper.userToShortDto(event.getInitiator()))
                .paid(event.getPaid())
                .title(event.getTitle())
                .views(event.getViews())
                .build();
    }

    public static Event toEvent(NewEventDto dto, Category category, User user){
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

    public static List<EventShortDto> toShortDtoList(List<Event> eventList){
        return eventList.stream()
                .map(EventMapper::toShortDto)
                .collect(Collectors.toList());
    }
}
