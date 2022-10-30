package ru.practicum.ewm.request.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.PublicationState;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exception.ForbiddenException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.request.dto.ParticipationRequestDto;
import ru.practicum.ewm.request.mapper.EventRequestMapper;
import ru.practicum.ewm.request.model.ParticipationRequest;
import ru.practicum.ewm.request.model.Status;
import ru.practicum.ewm.request.repository.EventRequestRepository;
import ru.practicum.ewm.user.repository.UserRepository;

import java.util.List;
import java.util.Objects;

@Service
public class EventRequestService {

    private final EventRequestRepository eventRequestRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Autowired
    public EventRequestService(EventRequestRepository eventRequestRepository, EventRepository eventRepository, UserRepository userRepository) {
        this.eventRequestRepository = eventRequestRepository;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

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

    public ParticipationRequestDto cancelRequestToEventFromUser(Long userId, Long requestId) {
        checkUserExist(userId);
        ParticipationRequest request = getParticipationRequest(requestId);
        if (!Objects.equals(request.getRequester(), userId)) {
            throw new ForbiddenException("User not owner of this request");
        }
        request.setStatus(Status.CANCELED);
        return EventRequestMapper.toDto(eventRequestRepository.save(request));
    }


    public List<ParticipationRequestDto> getUserSelfRequestsInEvents(Long userId) {
        checkUserExist(userId);
        return EventRequestMapper.toDtoList(eventRequestRepository.findAllByRequester(userId));
    }


    public List<ParticipationRequestDto> getEventOwnerRequests(Long userId, Long eventId) {
        checkUserExist(userId);
        return EventRequestMapper.toDtoList(eventRequestRepository.findAllByEventId(eventId));
    }

    public ParticipationRequestDto confirmEventOwnerRequest(Long userId, Long eventId, Long reqId) {
        ParticipationRequest request = getParticipationRequest(reqId);
        Event event = getEvent(eventId);
        if (!event.getInitiator().getId().equals(userId)) {
            throw new ForbiddenException("user not owner of event");
        }
        request.setStatus(Status.CONFIRMED);
        return EventRequestMapper.toDto(eventRequestRepository.save(request));
    }

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