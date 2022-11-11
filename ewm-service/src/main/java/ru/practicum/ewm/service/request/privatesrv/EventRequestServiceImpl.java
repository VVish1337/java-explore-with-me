package ru.practicum.ewm.service.request.privatesrv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.request.ParticipationRequestDto;
import ru.practicum.ewm.exception.ForbiddenException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.mapper.request.EventRequestMapper;
import ru.practicum.ewm.model.event.Event;
import ru.practicum.ewm.model.event.PublicationState;
import ru.practicum.ewm.model.request.ParticipationRequest;
import ru.practicum.ewm.model.request.Status;
import ru.practicum.ewm.repository.event.EventRepository;
import ru.practicum.ewm.repository.request.EventRequestRepository;
import ru.practicum.ewm.repository.user.UserRepository;

import java.util.List;
import java.util.Objects;

import static ru.practicum.ewm.util.DefaultValues.*;

/**
 * Class which describes Event Request service of Private api
 *
 * @author Timur Kiyamov
 * @version 1.0
 */
@Service
public class EventRequestServiceImpl implements EventRequestService {

    private final EventRequestRepository eventRequestRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Autowired
    public EventRequestServiceImpl(EventRequestRepository eventRequestRepository,
                                   EventRepository eventRepository, UserRepository userRepository) {
        this.eventRequestRepository = eventRequestRepository;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    /**
     * Method of service which add request to Event from User
     *
     * @param userId
     * @param eventId
     * @return ParticipationRequestDto
     */
    @Override
    public ParticipationRequestDto addRequestToEventFromUser(Long userId, Long eventId) {
        if(eventId ==null) {
            throw new IllegalArgumentException("Event id is required");
        }
        checkUserExist(userId);
        Event event = getEvent(eventId);
        if (event.getInitiator().getId().equals(userId)) {
            throw new ForbiddenException(USER_NOT_OWNER);
        }
        if (!event.getState().equals(PublicationState.PUBLISHED)) {
            throw new ForbiddenException(PENDING_ERROR);
        }
        if (event.getConfirmedRequests() >= event.getParticipantLimit()) {
            throw new ForbiddenException(PARTICIPANT_LIMIT);
        }
        if (!event.getRequestModeration()) {
            return EventRequestMapper.toDto(eventRequestRepository
                    .save(EventRequestMapper.toModel(userId, eventId, Status.CONFIRMED)));
        } else {
            return EventRequestMapper.toDto(eventRequestRepository
                    .save(EventRequestMapper.toModel(userId, eventId, Status.PENDING)));
        }
    }

    /**
     * Method of service which cancel request to Event from event User owner
     *
     * @param userId
     * @param requestId
     * @return ParticipationRequestDto
     */
    @Override
    public ParticipationRequestDto cancelRequestToEventFromUser(Long userId, Long requestId) {
        checkUserExist(userId);
        ParticipationRequest request = getParticipationRequest(requestId);
        if (!Objects.equals(request.getRequester(), userId)) {
            throw new ForbiddenException(USER_NOT_OWNER);
        }
        request.setStatus(Status.CANCELED);
        return EventRequestMapper.toDto(eventRequestRepository.save(request));
    }

    /**
     * Method of service which get User self requests in Events
     *
     * @param userId
     * @return List of ParticipationRequestDto
     */
    @Override
    public List<ParticipationRequestDto> getUserSelfRequestsInEvents(Long userId) {
        checkUserExist(userId);
        return EventRequestMapper.toDtoList(eventRequestRepository.findAllByRequester(userId));
    }

    /**
     * Method of service which get Event owner requests
     *
     * @param userId
     * @param eventId
     * @return List of ParticipationRequestDto
     */
    @Override
    public List<ParticipationRequestDto> getEventOwnerRequests(Long userId, Long eventId) {
        checkUserExist(userId);
        return EventRequestMapper.toDtoList(eventRequestRepository.findAllByEventId(eventId));
    }

    /**
     * Method of service which confirm request to Event by Event owner
     *
     * @param userId
     * @param eventId
     * @param reqId
     * @return ParticipationRequestDto
     */
    @Override
    public ParticipationRequestDto confirmEventOwnerRequest(Long userId, Long eventId, Long reqId) {
        ParticipationRequest request = getParticipationRequest(reqId);
        Event event = getEvent(eventId);
        if (!event.getInitiator().getId().equals(userId)) {
            throw new ForbiddenException(USER_NOT_OWNER);
        }
        request.setStatus(Status.CONFIRMED);
        return EventRequestMapper.toDto(eventRequestRepository.save(request));
    }

    /**
     * Method of service which reject request to Event by Event owner
     *
     * @param userId
     * @param eventId
     * @param reqId
     * @return ParticipationRequestDto
     */
    @Override
    public ParticipationRequestDto rejectEventOwnerRequest(Long userId, Long eventId, Long reqId) {
        ParticipationRequest request = getParticipationRequest(reqId);
        Event event = getEvent(eventId);
        if (!event.getInitiator().getId().equals(userId)) {
            throw new ForbiddenException(USER_NOT_OWNER);
        }
        request.setStatus(Status.REJECTED);
        return EventRequestMapper.toDto(eventRequestRepository.save(request));
    }

    /**
     * Private method of service which check User existence
     *
     * @param userId
     * @return User
     */
    private void checkUserExist(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND + userId));
    }

    /**
     * Method of service which check ParticipantRequest existence and get it
     *
     * @param reqId
     * @return
     */
    private ParticipationRequest getParticipationRequest(Long reqId) {
        return eventRequestRepository.findById(reqId)
                .orElseThrow(() -> new NotFoundException(PARTICIPANT_NOT_FOUND + reqId));
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
}