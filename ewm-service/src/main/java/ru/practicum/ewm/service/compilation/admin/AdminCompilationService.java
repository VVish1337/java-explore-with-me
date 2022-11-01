package ru.practicum.ewm.service.compilation.admin;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.dto.compilation.CompilationDto;
import ru.practicum.ewm.dto.compilation.NewCompilationDto;

public interface AdminCompilationService {
    CompilationDto addCompilation(NewCompilationDto dto);

    @Transactional
    void deleteCompilation(Long compId);

    @Transactional
    void deleteEventFromCompilation(Long compId, Long eventId);

    void addEventToCompilation(Long compId, Long eventId);

    void unpinCompilation(Long compId);

    void pinCompilation(Long compId);
}
