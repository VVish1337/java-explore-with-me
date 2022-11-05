package ru.practicum.ewm.repository.compilation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.model.compilation.CompilationEvent;

/**
 * Class describing jpa compilation event repository
 *
 * @author Timur Kiyamov
 * @version 1.0
 */
@Repository
public interface CompilationEventRepository extends JpaRepository<CompilationEvent, Long> {
    /**
     * Method of repository which delete event from compilation
     *
     * @param compilationId
     * @param eventId
     */
    void deleteByCompilationIdAndEventId(Long compilationId, Long eventId);
}