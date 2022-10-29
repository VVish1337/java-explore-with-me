package ru.practicum.ewm.compilation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.compilation.dto.CompilationDto;
import ru.practicum.ewm.compilation.dto.NewCompilationDto;
import ru.practicum.ewm.compilation.mapper.CompilationMapper;
import ru.practicum.ewm.compilation.model.Compilation;
import ru.practicum.ewm.compilation.repository.CompilationEventRepository;
import ru.practicum.ewm.compilation.repository.CompilationRepository;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exception.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
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

        List<Event> eventList = dto.getEvents().stream()
                .map(a -> eventRepository.findById(a)
                        .orElseThrow(() -> new NotFoundException("Event not found id" + a)))
                .collect(Collectors.toList());

        return CompilationMapper.toDto(compilationRepository.save(CompilationMapper.toModel(dto, eventList)));
    }

    @Transactional
    public void deleteCompilation(Long compId) {
        compilationRepository.deleteById(compId);
    }

    @Transactional
    public void deleteEventFromCompilation(Long compId, Long eventId) {
        compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException("Compilation not found id:" + compId));
        eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event not found id:" + eventId));
        compilationEventRepository.deleteByCompilationIdAndEventId(compId, eventId);
    }

    public void addEventToCompilation(Long compId, Long eventId) {
        compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException("Compilation not found id:" + compId));
        eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event not found id:" + eventId));
        compilationEventRepository.save(CompilationMapper.toModel(compId, eventId));
    }

    public void unpinCompilation(Long compId) {
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException("Compilation not found id:" + compId));
        if(compilation.getPinned()){
            compilation.setPinned(false);
        }
        compilationRepository.save(compilation);
    }

    public void pinCompilation(Long compId) {
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException("Compilation not found id:" + compId));
        if(!compilation.getPinned()){
            compilation.setPinned(true);
        }
        compilationRepository.save(compilation);
    }
}
