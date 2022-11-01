package ru.practicum.ewm.repository.compilation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.model.compilation.CompilationEvent;

@Repository
public interface CompilationEventRepository extends JpaRepository<CompilationEvent, Long> {
    void deleteByCompilationIdAndEventId(Long compilationId, Long eventId);
}