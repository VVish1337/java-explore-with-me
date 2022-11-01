package ru.practicum.ewm.repository.request;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.model.request.ParticipationRequest;

import java.util.List;

@Repository
public interface EventRequestRepository extends JpaRepository<ParticipationRequest, Long> {
    List<ParticipationRequest> findAllByRequester(Long userId);

    List<ParticipationRequest> findAllByEventId(Long eventId);
}