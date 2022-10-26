package ru.practicum.ewm.compilation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.compilation.model.CompilationEvent;

@Repository
public interface CompilationEventRepository extends JpaRepository<CompilationEvent,Long> {
}
