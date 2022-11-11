package ru.practicum.ewm.service.event.admin;

import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.event.AdminUpdateEventDto;
import ru.practicum.ewm.dto.event.EventFullDto;
import ru.practicum.ewm.dto.event.comment.CommentReportDto;
import ru.practicum.ewm.exception.ForbiddenException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.mapper.event.EventMapper;
import ru.practicum.ewm.model.category.Category;
import ru.practicum.ewm.model.event.Event;
import ru.practicum.ewm.model.event.EventFilterParams;
import ru.practicum.ewm.model.event.PublicationState;
import ru.practicum.ewm.model.event.QEvent;
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
import ru.practicum.ewm.util.QPredicates;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

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
    private final CommentReportRepository commentReportRepository;
    private final UserRepository userRepository;

    @Autowired
    public AdminEventServiceImpl(EventRepository eventRepository, CategoryRepository categoryRepository, CommentRepository commentRepository, CommentReportRepository commentReportRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.categoryRepository = categoryRepository;
        this.commentRepository = commentRepository;
        this.commentReportRepository = commentReportRepository;
        this.userRepository = userRepository;
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

    /**
     * Method of service which delete comment by Administrator,Moderator
     *
     * @param eventId id of Event
     * @param comId   id of comment
     */
    @Override
    public void deleteCommentByAdmin(Long eventId, Long comId) {
        getEvent(eventId);
        Comment comment = commentRepository.findById(comId)
                .orElseThrow(() -> new NotFoundException("Comment not found id:" + comId));
        if (!comment.getEvent().getId().equals(eventId)) {
            throw new ForbiddenException("Wrong eventId comment eventId:" + comment.getEvent().getId()
                    + " eventId:" + eventId);
        }
        commentRepository.deleteById(comId);
    }

    /**
     * Method of service which get all reported comments
     *
     * @return List of CommentReportDto
     */
    @Override
    public List<CommentReportDto> getReportedComments() {
        return EventMapper.toCommentReportDtoList(commentReportRepository.findAll());
    }

    /**
     * Method of service which get filtered reported comments
     *
     * @param start    start time
     * @param end      end time
     * @param category category of report (check enum ReportName) default value ALL
     * @return
     */
    @Override
    public List<CommentReportDto> getFilteredReportedComments(String start, String end, String category) {
        checkCategoryExists(category);
        LocalDateTime startTime;
        LocalDateTime endTime;
        if (start.isBlank() || start.isEmpty()) {
            startTime = LocalDateTime.now().minusYears(1);
        } else {
            startTime = DateFormatter.stringToDate(start);
        }
        if (end.isBlank() || end.isEmpty()) {
            endTime = LocalDateTime.now();
        } else {
            endTime = DateFormatter.stringToDate(end);
        }
        if (startTime.isAfter(endTime)) {
            throw new DataIntegrityViolationException("Date start time is after end time");
        }
        if (category.equals("ALL")) {
            return EventMapper.toCommentReportDtoList(commentReportRepository
                    .findAllFilteredWithoutCategory(startTime, endTime));
        } else {
            return EventMapper.toCommentReportDtoList(commentReportRepository
                    .findAllFiltered(startTime, endTime, ReportName.valueOf(category)));
        }
    }

    /**
     * Method of service which get all reported comments by reporter
     *
     * @param userId user id (owner of report)
     * @return List of CommentReportDto
     */
    @Override
    public List<CommentReportDto> getAllReportCommentsOwner(Long userId) {
        checkUserExists(userId);
        return EventMapper.toCommentReportDtoList(commentReportRepository.findAllByReporterId(userId));
    }

    /**
     * Method of service which delete reported comments by id
     *
     * @param repId
     */
    @Override
    public void deleteReportById(Long repId) {
        commentReportRepository.deleteById(repId);
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

    private String checkCategoryExists(String category) {
        if (Stream.of(ReportName.values()).anyMatch(v -> v.name().equals(category))) {
            return category;
        } else {
            throw new NotFoundException("This is category not found :" + category);
        }
    }
}