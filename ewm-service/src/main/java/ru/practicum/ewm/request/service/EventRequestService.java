package ru.practicum.ewm.request.service;

import org.apache.catalina.User;
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

import java.util.List;
import java.util.Objects;

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

    public ParticipationRequestDto cancelRequestToEventFromUser(Long userId, Long requestId) {
        ParticipationRequest request = eventRequestRepository.findById(requestId)
                .orElseThrow(()->new NotFoundException("Not found request with this id"+requestId));
        if(!Objects.equals(request.getRequester(), userId)){
            throw new ForbiddenException("User not owner of this request");
        }
        request.setStatus(Status.CANCELED);
        return EventRequestMapper.toDto(eventRequestRepository.save(request));
    }


    public List<ParticipationRequestDto> getUserSelfRequestsInEvents(Long userId) {
        return EventRequestMapper.toDtoList(eventRequestRepository.findAllByRequester(userId));
    }


    public List<ParticipationRequestDto> getEventOwnerRequests(Long userId, Long eventId) {
        //тут надо подумать не очень понял что именно требуется
        return EventRequestMapper.toDtoList(eventRequestRepository.findAllByEventId(eventId));
    }

    public ParticipationRequestDto confirmEventOwnerRequest(Long userId, Long eventId, Long reqId) {
        ParticipationRequest request = eventRequestRepository.findById(reqId)
                .orElseThrow(()->new NotFoundException("Not found request with this id"+reqId));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(()->new NotFoundException("Event not found"+eventId));
        if(!event.getInitiator().getId().equals(userId)){
            throw new  ForbiddenException ("user not owner of event");
        }
        request.setStatus(Status.CONFIRMED);
        return EventRequestMapper.toDto(eventRequestRepository.save(request));
    }

    public ParticipationRequestDto rejectEventOwnerRequest(Long userId, Long eventId, Long reqId) {
        ParticipationRequest request = eventRequestRepository.findById(reqId)
                .orElseThrow(()->new NotFoundException("Not found request with this id"+reqId));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(()->new NotFoundException("Event not found"+eventId));
        if(!event.getInitiator().getId().equals(userId)){
            throw new  ForbiddenException ("user not owner of event");
        }
        request.setStatus(Status.REJECTED);
        return EventRequestMapper.toDto(eventRequestRepository.save(request));
    }
}
