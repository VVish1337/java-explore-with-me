package ru.practicum.ewm.compilation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.compilation.dto.CompilationDto;
import ru.practicum.ewm.compilation.dto.NewCompilationDto;
import ru.practicum.ewm.compilation.repository.CompilationEventRepository;
import ru.practicum.ewm.compilation.repository.CompilationRepository;
import ru.practicum.ewm.event.repository.EventRepository;

@Service
public class AdminCompilationService {

    private final CompilationRepository compilationRepository;
    private final CompilationEventRepository compilationEventRepository;
    private final EventRepository eventRepository;

    @Autowired
    public AdminCompilationService(CompilationRepository compilationRepository,
                                   CompilationEventRepository compilationEventRepository, EventRepository eventRepository) {
        this.compilationRepository = compilationRepository;
        this.compilationEventRepository = compilationEventRepository;
        this.eventRepository = eventRepository;
    }

    public CompilationDto addCompilation(NewCompilationDto dto) {
        return null;
    }
}
