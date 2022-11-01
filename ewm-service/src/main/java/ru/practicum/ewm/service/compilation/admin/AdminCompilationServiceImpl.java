package ru.practicum.ewm.service.compilation.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.dto.compilation.CompilationDto;
import ru.practicum.ewm.dto.compilation.NewCompilationDto;
import ru.practicum.ewm.mapper.compilation.CompilationMapper;
import ru.practicum.ewm.model.compilation.Compilation;
import ru.practicum.ewm.repository.compilation.CompilationEventRepository;
import ru.practicum.ewm.repository.compilation.CompilationRepository;
import ru.practicum.ewm.model.event.Event;
import ru.practicum.ewm.repository.event.EventRepository;
import ru.practicum.ewm.exception.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class AdminCompilationServiceImpl implements AdminCompilationService{

    private final CompilationRepository compilationRepository;
    private final CompilationEventRepository compilationEventRepository;
    private final EventRepository eventRepository;

    @Autowired
    public AdminCompilationServiceImpl(CompilationRepository compilationRepository,
                                       CompilationEventRepository compilationEventRepository, EventRepository eventRepository) {
        this.compilationRepository = compilationRepository;
        this.compilationEventRepository = compilationEventRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public CompilationDto addCompilation(NewCompilationDto dto) {

        List<Event> eventList = dto.getEvents().stream()
                .map(a -> eventRepository.findById(a)
                        .orElseThrow(() -> new NotFoundException("Event not found id:" + a)))
                .collect(Collectors.toList());

        return CompilationMapper.toDto(compilationRepository.save(CompilationMapper.toModel(dto, eventList)));
    }

    @Override
    @Transactional
    public void deleteCompilation(Long compId) {
        compilationRepository.deleteById(compId);
    }

    @Override
    @Transactional
    public void deleteEventFromCompilation(Long compId, Long eventId) {
        checkCompilationExists(compId);
        checkEventExists(eventId);
        compilationEventRepository.deleteByCompilationIdAndEventId(compId, eventId);
    }

    @Override
    public void addEventToCompilation(Long compId, Long eventId) {
        checkCompilationExists(compId);
        checkEventExists(eventId);
        compilationEventRepository.save(CompilationMapper.toModel(compId, eventId));
    }

    @Override
    public void unpinCompilation(Long compId) {
        Compilation compilation = checkCompilationExists(compId);
        if (compilation.getPinned()) {
            compilation.setPinned(false);
        }
        compilationRepository.save(compilation);
    }

    @Override
    public void pinCompilation(Long compId) {
        Compilation compilation = checkCompilationExists(compId);
        if (!compilation.getPinned()) {
            compilation.setPinned(true);
        }
        compilationRepository.save(compilation);
    }

    private Compilation checkCompilationExists(Long compId) {
        return compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException("Compilation not found id:" + compId));
    }

    private void checkEventExists(Long eventId) {
        eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event not found id:" + eventId));
    }
}