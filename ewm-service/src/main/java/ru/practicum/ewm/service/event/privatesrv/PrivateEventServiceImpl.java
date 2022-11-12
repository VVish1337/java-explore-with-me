package ru.practicum.ewm.service.event.privatesrv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.event.EventFullDto;
import ru.practicum.ewm.dto.event.EventShortDto;
import ru.practicum.ewm.dto.event.NewEventDto;
import ru.practicum.ewm.dto.event.UpdateEventDto;
import ru.practicum.ewm.dto.event.comment.CommentDto;
import ru.practicum.ewm.dto.event.comment.CommentReportDto;
import ru.practicum.ewm.exception.ForbiddenException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.mapper.event.EventMapper;
import ru.practicum.ewm.model.category.Category;
import ru.practicum.ewm.model.event.Event;
import ru.practicum.ewm.model.event.PublicationState;
import ru.practicum.ewm.model.event.comment.Comment;
import ru.practicum.ewm.model.event.comment.ReportName;
import ru.practicum.ewm.model.user.User;
import ru.practicum.ewm.repository.category.CategoryRepository;
import ru.practicum.ewm.repository.event.CommentReportRepository;
import ru.practicum.ewm.repository.event.CommentRepository;
import ru.practicum.ewm.repository.event.EventRepository;
import ru.practicum.ewm.repository.user.UserRepository;
import ru.practicum.ewm.util.DateFormatter;
import ru.practicum.ewm.util.PaginationUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static ru.practicum.ewm.util.DefaultValues.*;

/**
 * Class which describes Event service of Private api
 *
 * @author Timur Kiyamov
 * @version 1.0
 */
@Service
public class PrivateEventServiceImpl implements PrivateEventService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final CommentRepository commentRepository;
    private final CommentReportRepository commentReportRepository;

    @Autowired
    public PrivateEventServiceImpl(EventRepository eventRepository, UserRepository userRepository,
                                   CategoryRepository categoryRepository, CommentRepository commentRepository,
                                   CommentReportRepository commentReportRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.commentRepository = commentRepository;
        this.commentReportRepository = commentReportRepository;
    }

    /**
     * Method of service which add Event by user
     *
     * @param userId
     * @param dto
     * @return EventFullDto
     */
    @Override
    public EventFullDto addEvent(long userId, NewEventDto dto) {
        Objects.requireNonNull(dto);
        if (DateFormatter.stringToDate(dto.getEventDate()).isBefore(LocalDateTime.now().plusHours(2))) {
            throw new DataIntegrityViolationException(WRONG_DATE);
        }
        User user = checkUserExists(userId);
        Category category = getCategory(dto.getCategory());
        return EventMapper.toFullDto(eventRepository.save(EventMapper.toEvent(dto, category, user)));
    }

    /**
     * Method of service which get list of User owned Events
     *
     * @param userId
     * @param from
     * @param size
     * @return List of EventShortDto
     */
    @Override
    public List<EventShortDto> getUserOwnEvents(Long userId, int from, int size) {
        checkUserExists(userId);
        return EventMapper.toShortDtoList(eventRepository
                .findAllByInitiatorId(userId, PaginationUtil.getPageable(from, size, Sort.unsorted()))
                .toList());
    }

    /**
     * Method of service which get full information by User owner of Event by id
     *
     * @param userId
     * @param eventId
     * @return EventFullDto
     */
    @Override
    public EventFullDto getUserFullEventById(Long userId, Long eventId) {
        checkUserExists(userId);
        getEvent(eventId);
        return EventMapper.toFullDto(eventRepository.findByInitiatorIdAndId(userId, eventId));
    }

    /**
     * Method of service which update Event by User owner
     *
     * @param userId
     * @param dto
     * @return
     */
    @Override
    public EventFullDto updateEventByUserOwner(Long userId, UpdateEventDto dto) {
        Objects.requireNonNull(dto);
        Event event = getEvent(dto.getEventId());
        if (!Objects.equals(event.getInitiator().getId(), userId)) {
            throw new NotFoundException(USER_NOT_OWNER);
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
            if (DateFormatter.stringToDate(dto.getEventDate()).isBefore(LocalDateTime.now().plusHours(2))) {
                throw new ForbiddenException(WRONG_DATE);
            }
            event.setEventDate(DateFormatter.stringToDate(dto.getEventDate()));
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

    /**
     * Method of service which cancel Event from publish by User owner
     *
     * @param userId
     * @param eventId
     * @return
     */
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

    /**
     * Method of service which add Comment to Event by user
     *
     * @param userId  id of user (owner of comment)
     * @param eventId id of event
     * @param text    text which will be add as comment
     * @return CommentDto
     */
    @Override
    public CommentDto addCommentToEvent(Long userId, Long eventId, String text) {
        if (text.isEmpty() || text.isBlank()) {
            throw new NotFoundException(DEFAULT_TEXT_EMPTY);
        }
        User user = checkUserExists(userId);
        Event event = getEvent(eventId);
        if (!event.getState().equals(PublicationState.PUBLISHED)) {
            throw new ForbiddenException("Event must be published");
        }
        return EventMapper.toCommentDto(commentRepository.save(EventMapper.toCommentModel(user, event, text)));
    }

    /**
     * Method of service which update added comment by comment user owner
     *
     * @param userId  id of user (owner of comment)
     * @param eventId id of event
     * @param comId   id of comment
     * @param text    text which will update added text in comment
     * @return CommentDto
     */
    @Override
    public CommentDto updateCommentByUserOwner(Long userId, Long eventId, Long comId, String text) {
        if (text.isEmpty() || text.isBlank()) {
            throw new NotFoundException(DEFAULT_TEXT_EMPTY);
        }
        Comment comment = getComment(comId);
        checkUserExists(userId);
        if (!comment.getUser().getId().equals(userId)) {
            throw new ForbiddenException("User not owner of comment");
        }
        getEvent(eventId);
        if (!comment.getEvent().getId().equals(eventId)) {
            throw new ForbiddenException("This comment not in this event");
        }
        return EventMapper.toCommentDto(comment);
    }

    /**
     * Method of service which delete added comment by comment user owner
     *
     * @param userId  id of user (owner of comment)
     * @param eventId id of event
     * @param comId
     */
    @Override
    public void deleteCommentByUserOwner(Long userId, Long eventId, Long comId) {
        Comment comment = getComment(comId);
        checkUserExists(userId);
        if (!comment.getUser().getId().equals(userId)) {
            throw new ForbiddenException("User not owner of comment");
        }
        getEvent(eventId);
        if (!comment.getEvent().getId().equals(eventId)) {
            throw new ForbiddenException("This comment not in this event");
        }
        commentRepository.deleteById(comId);
    }

    /**
     * Method of service which add report to bad comment
     *
     * @param userId     id of user (not owner of comment)
     * @param eventId    id of event
     * @param comId      id of comment
     * @param reportName category of report
     * @return CommentReportDto
     */
    @Override
    public CommentReportDto addReportToComment(Long userId, Long eventId, Long comId, String reportName) {
        if (reportName.isEmpty() || reportName.isBlank()) {
            throw new NotFoundException(DEFAULT_REPORT_NAME_EMPTY);
        }
        Comment comment = getComment(comId);
        User reporter = checkUserExists(userId);
        if (comment.getUser().getId().equals(userId)) {
            throw new ForbiddenException("User owner of comment");
        }
        getEvent(eventId);
        if (!comment.getEvent().getId().equals(eventId)) {
            throw new ForbiddenException("This comment not in this event");
        }
        return EventMapper.toCommentReportDto(commentReportRepository
                .save(EventMapper.toCommentReport(comment, parseReportName(reportName), reporter)));
    }

    /**
     * Private method of service which check User existence
     *
     * @param userId
     * @return User
     */
    private User checkUserExists(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND + userId));
    }

    /**
     * Private method of service which check Event existence
     *
     * @param eventId
     * @return Event
     */
    private Event getEvent(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(EVENT_NOT_FOUND + eventId));
    }

    /**
     * Private method of service which check Category existence
     *
     * @param categoryId
     * @return Category
     */
    private Category getCategory(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException(CATEGORY_NOT_FOUND + categoryId));
    }

    /**
     * Private method of service which chech Comment existence
     *
     * @param comId
     * @return Comment
     */
    private Comment getComment(Long comId) {
        return commentRepository.findById(comId)
                .orElseThrow(() -> new NotFoundException(COMMENT_NOT_FOUND + comId));
    }

    /**
     * Private method of service which parse string of category report to enum type
     *
     * @param reportName
     * @return
     */
    private ReportName parseReportName(String reportName) {
        return ReportName.valueOf(reportName);
    }
}