package ru.practicum.ewm.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.request.model.ParticipationRequest;

@Repository
public interface EventRequestRepository extends JpaRepository<ParticipationRequest,Long> {
}
