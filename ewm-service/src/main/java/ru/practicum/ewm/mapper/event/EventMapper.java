package ru.practicum.ewm.mapper.event;

import ru.practicum.ewm.dto.event.EventFullDto;
import ru.practicum.ewm.dto.event.EventShortDto;
import ru.practicum.ewm.dto.event.EventWithCommentsDto;
import ru.practicum.ewm.dto.event.NewEventDto;
import ru.practicum.ewm.dto.event.comment.CommentDto;
import ru.practicum.ewm.dto.event.comment.CommentInEventDto;
import ru.practicum.ewm.dto.event.comment.CommentReportDto;
import ru.practicum.ewm.mapper.category.CategoryMapper;
import ru.practicum.ewm.mapper.user.UserMapper;
import ru.practicum.ewm.model.category.Category;
import ru.practicum.ewm.model.event.Event;
import ru.practicum.ewm.model.event.PublicationState;
import ru.practicum.ewm.model.event.comment.Comment;
import ru.practicum.ewm.model.event.comment.CommentReport;
import ru.practicum.ewm.model.event.comment.ReportName;
import ru.practicum.ewm.model.user.User;
import ru.practicum.ewm.util.DateFormatter;

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

    public static Comment toCommentModel(User user, Event event, String text) {
        return Comment.builder()
                .event(event)
                .user(user)
                .text(text)
                .createdOn(LocalDateTime.now())
                .build();
    }

    public static CommentDto toCommentDto(Comment comment) {
        return CommentDto.builder()
                .eventId(comment.getEvent().getId())
                .userId(comment.getUser().getId())
                .text(comment.getText())
                .createdOn(DateFormatter.formatDate(comment.getCreatedOn()))
                .build();
    }

    public static CommentInEventDto toCommentInEventDto(Comment comment) {
        return CommentInEventDto.builder()
                .userEmail(comment.getUser().getEmail())
                .text(comment.getText())
                .createdOn(DateFormatter.formatDate(comment.getCreatedOn()))
                .build();
    }

    public static List<CommentInEventDto> toCommentInEventListDto(List<Comment> comments) {
        return comments.stream()
                .map(EventMapper::toCommentInEventDto)
                .collect(Collectors.toList());
    }

    public static EventWithCommentsDto toEventWithCommentsDto(Event event, List<Comment> comments) {
        return EventWithCommentsDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(CategoryMapper.toDto(event.getCategory()))
                .confirmedRequests(event.getConfirmedRequests())
                .eventDate(event.getEventDate().format(DEFAULT_DATE_FORMATTER))
                .initiator(UserMapper.toShortDto(event.getInitiator()))
                .paid(event.getPaid())
                .title(event.getTitle())
                .commentsCount(comments.size())
                .comments(toCommentInEventListDto(comments))
                .build();
    }

    public static List<EventWithCommentsDto> toShortDtoWithCommentList(List<Event> eventList, List<Comment> comments) {
        return eventList.stream()
                .map(a -> toEventWithCommentsDto(a,
                        comments.stream()
                                .filter(b -> b.getEvent().getId().equals(a.getId()))
                                .collect(Collectors.toList())))
                .collect(Collectors.toList());
    }

    public static CommentReport toCommentReport(Comment comment, ReportName reportName,User reporter) {
        return CommentReport.builder()
                .comment(comment)
                .reporter(reporter)
                .reportName(reportName)
                .createdOn(LocalDateTime.now())
                .build();
    }

    public static CommentReportDto toCommentReportDto(CommentReport commentReport) {
        return CommentReportDto.builder()
                .id(commentReport.getId())
                .reportName(commentReport.getReportName())
                .reporterId(commentReport.getReporter().getId())
                .comment(toCommentDto(commentReport.getComment()))
                .createdOn(DateFormatter.formatDate(commentReport.getCreatedOn()))
                .build();
    }

    public static List<CommentReportDto> toCommentReportDtoList(List<CommentReport> commentReportList) {
        return commentReportList.stream()
                .map(EventMapper::toCommentReportDto)
                .collect(Collectors.toList());
    }
}