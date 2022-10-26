package ru.practicum.ewm.event.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.event.model.Event;

@Repository
public interface EventRepository extends JpaRepository<Event,Long> {
    Page<Event> findAllByInitiatorId(Long userId, Pageable pageable);

    Event findByInitiatorIdAndId(Long id,Long eventId);
}
