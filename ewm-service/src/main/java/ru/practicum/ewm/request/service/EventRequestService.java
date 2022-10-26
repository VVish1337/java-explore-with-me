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
import ru.practicum.ewm.request.model.Status;
import ru.practicum.ewm.request.repository.EventRequestRepository;

@Service
public class EventRequestService {

    private final EventRequestRepository eventRequestRepository;
    private final EventRepository eventRepository;

    @Autowired
    public EventRequestService(EventRequestRepository eventRequestRepository, EventRepository eventRepository) {
        this.eventRequestRepository = eventRequestRepository;
        this.eventRepository = eventRepository;
    }

    public ParticipationRequestDto addRequestToEventFromUser(Long userId, Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(()->new NotFoundException("Event not found"+eventId));
        if(event.getInitiator().getId().equals(userId)){
            throw new ForbiddenException("Owner can't sent request to event");
        }
        if(!event.getState().equals(PublicationState.PUBLISHED)){
            throw new ForbiddenException("Only pending or canceled events can be changed");
        }
        if(event.getConfirmedRequests()==Long.parseLong(event.getParticipantLimit().toString())){
            throw new ForbiddenException("Participant limit is reached");
        }
        if(!event.getRequestModeration()){
            return EventRequestMapper.toDto(eventRequestRepository
                    .save(EventRequestMapper.toModel(userId,eventId, Status.CONFIRMED)));
        }else {
            return EventRequestMapper.toDto(eventRequestRepository
                    .save(EventRequestMapper.toModel(userId,eventId, Status.PENDING)));
        }
    }
}
