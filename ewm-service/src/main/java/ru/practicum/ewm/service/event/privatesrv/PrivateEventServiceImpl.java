package ru.practicum.ewm.service.event.privatesrv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.event.*;
import ru.practicum.ewm.exception.ForbiddenException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.mapper.event.EventMapper;
import ru.practicum.ewm.model.category.Category;
import ru.practicum.ewm.model.event.Comment;
import ru.practicum.ewm.model.event.Event;
import ru.practicum.ewm.model.event.PublicationState;
import ru.practicum.ewm.model.user.User;
import ru.practicum.ewm.repository.category.CategoryRepository;
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

    @Autowired
    public PrivateEventServiceImpl(EventRepository eventRepository, UserRepository userRepository,
                                   CategoryRepository categoryRepository, CommentRepository commentRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.commentRepository = commentRepository;
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

    @Override
    public CommentDto addCommentToEvent(Long userId, Long eventId, String text) {
        if (text.isEmpty() || text.isBlank()) {
            throw new NotFoundException(DEFAULT_TEXT_EMPTY);
        }
        User user = checkUserExists(userId);
        Event event = getEvent(eventId);
        return EventMapper.toCommentDto(commentRepository.save(EventMapper.toCommentModel(user, event, text)));
    }

    @Override
    public CommentDto updateCommentByUserOwner(Long userId, Long eventId, Long comId, String text) {
        if (text.isEmpty() || text.isBlank()) {
            throw new NotFoundException(DEFAULT_TEXT_EMPTY);
        }
        Comment comment = commentRepository.findById(comId)
                .orElseThrow(()->new NotFoundException("Comment not found id:"+comId));
        User user = checkUserExists(userId);
        if(!comment.getUser().getId().equals(userId)){
            throw new ForbiddenException("User not owner of comment");
        }
        Event event = getEvent(eventId);
        if(!comment.getEvent().getId().equals(eventId)){
            throw new ForbiddenException("This comment not in this event");
        }
        return EventMapper.toCommentDto(comment);
    }

    @Override
    public void deleteCommentByUserOwner(Long userId, Long eventId, Long comId) {
        Comment comment = commentRepository.findById(comId)
                .orElseThrow(()->new NotFoundException("Comment not found id:"+comId));
        User user = checkUserExists(userId);
        if(!comment.getUser().getId().equals(userId)){
            throw new ForbiddenException("User not owner of comment");
        }
        Event event = getEvent(eventId);
        if(!comment.getEvent().getId().equals(eventId)){
            throw new ForbiddenException("This comment not in this event");
        }
        commentRepository.deleteById(comId);
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
}