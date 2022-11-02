package ru.practicum.ewm.repository.request;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.model.request.ParticipationRequest;

import java.util.List;

/**
 * Class describing jpa event request repository
 *
 * @author Timur Kiyamov
 * @version 1.0
 */
@Repository
public interface EventRequestRepository extends JpaRepository<ParticipationRequest, Long> {
    /**
     * Method which find list of ParticipationRequest by User (requester) ID
     *
     * @param userId
     * @return List of ParticipationRequest
     */
    List<ParticipationRequest> findAllByRequester(Long userId);

    /**
     * Method which find list of ParticipationRequest by Event ID
     *
     * @param eventId
     * @return List of ParticipationRequest
     */
    List<ParticipationRequest> findAllByEventId(Long eventId);
}