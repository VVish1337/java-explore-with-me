package ru.practicum.ewm.service.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.model.event.Event;
import ru.practicum.ewm.model.event.PublicationState;
import ru.practicum.ewm.repository.event.EventRepository;
import ru.practicum.ewm.exception.ForbiddenException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.dto.request.ParticipationRequestDto;
import ru.practicum.ewm.mapper.request.EventRequestMapper;
import ru.practicum.ewm.model.request.ParticipationRequest;
import ru.practicum.ewm.model.request.Status;
import ru.practicum.ewm.repository.request.EventRequestRepository;
import ru.practicum.ewm.repository.user.UserRepository;

import java.util.List;
import java.util.Objects;

@Service
public class EventRequestServiceImpl implements EventRequestService {

    private final EventRequestRepository eventRequestRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Autowired
    public EventRequestServiceImpl(EventRequestRepository eventRequestRepository, EventRepository eventRepository, UserRepository userRepository) {
        this.eventRequestRepository = eventRequestRepository;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ParticipationRequestDto addRequestToEventFromUser(Long userId, Long eventId) {
        checkUserExist(userId);
        Event event = getEvent(eventId);
        if (event.getInitiator().getId().equals(userId)) {
            throw new ForbiddenException("Owner can't sent request to event");
        }
        if (!event.getState().equals(PublicationState.PUBLISHED)) {
            throw new ForbiddenException("Only pending or canceled events can be changed");
        }
        if (event.getConfirmedRequests() >= event.getParticipantLimit()) {
            throw new ForbiddenException("Participant limit is reached");
        }
        if (!event.getRequestModeration()) {
            return EventRequestMapper.toDto(eventRequestRepository
                    .save(EventRequestMapper.toModel(userId, eventId, Status.CONFIRMED)));
        } else {
            return EventRequestMapper.toDto(eventRequestRepository
                    .save(EventRequestMapper.toModel(userId, eventId, Status.PENDING)));
        }
    }

    @Override
    public ParticipationRequestDto cancelRequestToEventFromUser(Long userId, Long requestId) {
        checkUserExist(userId);
        ParticipationRequest request = getParticipationRequest(requestId);
        if (!Objects.equals(request.getRequester(), userId)) {
            throw new ForbiddenException("User not owner of this request");
        }
        request.setStatus(Status.CANCELED);
        return EventRequestMapper.toDto(eventRequestRepository.save(request));
    }

    @Override
    public List<ParticipationRequestDto> getUserSelfRequestsInEvents(Long userId) {
        checkUserExist(userId);
        return EventRequestMapper.toDtoList(eventRequestRepository.findAllByRequester(userId));
    }

    @Override
    public List<ParticipationRequestDto> getEventOwnerRequests(Long userId, Long eventId) {
        checkUserExist(userId);
        return EventRequestMapper.toDtoList(eventRequestRepository.findAllByEventId(eventId));
    }

    @Override
    public ParticipationRequestDto confirmEventOwnerRequest(Long userId, Long eventId, Long reqId) {
        ParticipationRequest request = getParticipationRequest(reqId);
        Event event = getEvent(eventId);
        if (!event.getInitiator().getId().equals(userId)) {
            throw new ForbiddenException("user not owner of event");
        }
        request.setStatus(Status.CONFIRMED);
        return EventRequestMapper.toDto(eventRequestRepository.save(request));
    }

    @Override
    public ParticipationRequestDto rejectEventOwnerRequest(Long userId, Long eventId, Long reqId) {
        ParticipationRequest request = getParticipationRequest(reqId);
        Event event = getEvent(eventId);
        if (!event.getInitiator().getId().equals(userId)) {
            throw new ForbiddenException("user not owner of event");
        }
        request.setStatus(Status.REJECTED);
        return EventRequestMapper.toDto(eventRequestRepository.save(request));
    }

    private void checkUserExist(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found id:" + userId));
    }

    private ParticipationRequest getParticipationRequest(Long reqId) {
        return eventRequestRepository.findById(reqId)
                .orElseThrow(() -> new NotFoundException("Not found participation request id:" + reqId));
    }

    private Event getEvent(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event not found id:" + eventId));
    }
}