package ru.practicum.ewm.repository.event;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.dto.event.EventWithCommentsDto;
import ru.practicum.ewm.model.event.Event;

import java.util.List;

/**
 * Class describing jpa event repository
 *
 * @author Timur Kiyamov
 * @version 1.0
 */
@Repository
public interface EventRepository extends JpaRepository<Event, Long>, QuerydslPredicateExecutor<Event> {
    /**
     * Method of repository which find pages of Event by User (initiator) ID
     *
     * @param userId
     * @param pageable
     * @return Pages of Event
     */
    Page<Event> findAllByInitiatorId(Long userId, Pageable pageable);

    /**
     * Method of repository which find Event by User (initiator) ID and Event ID
     *
     * @param id
     * @param eventId
     * @return Event
     */
    Event findByInitiatorIdAndId(Long id, Long eventId);
}