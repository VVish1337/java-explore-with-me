package ru.practicum.ewm.service.request.privatesrv;

import ru.practicum.ewm.dto.request.ParticipationRequestDto;

import java.util.List;

/**
 * Interface which describes Event Request service of Private api
 *
 * @author Timur Kiyamov
 * @version 1.0
 */
public interface EventRequestService {

    /**
     * Method of service which add request to Event from User
     *
     * @param userId
     * @param eventId
     * @return ParticipationRequestDto
     */
    ParticipationRequestDto addRequestToEventFromUser(Long userId, Long eventId);

    /**
     * Method of service which cancel request to Event from event User owner
     *
     * @param userId
     * @param requestId
     * @return ParticipationRequestDto
     */
    ParticipationRequestDto cancelRequestToEventFromUser(Long userId, Long requestId);

    /**
     * Method of service which get User self requests in Events
     *
     * @param userId
     * @return List of ParticipationRequestDto
     */
    List<ParticipationRequestDto> getUserSelfRequestsInEvents(Long userId);

    /**
     * Method of service which get Event owner requests
     *
     * @param userId
     * @param eventId
     * @return List of ParticipationRequestDto
     */
    List<ParticipationRequestDto> getEventOwnerRequests(Long userId, Long eventId);

    /**
     * Method of service which confirm request to Event by Event owner
     *
     * @param userId
     * @param eventId
     * @param reqId
     * @return ParticipationRequestDto
     */
    ParticipationRequestDto confirmEventOwnerRequest(Long userId, Long eventId, Long reqId);

    /**
     * Method of service which reject request to Event by Event owner
     *
     * @param userId
     * @param eventId
     * @param reqId
     * @return ParticipationRequestDto
     */
    ParticipationRequestDto rejectEventOwnerRequest(Long userId, Long eventId, Long reqId);
}
