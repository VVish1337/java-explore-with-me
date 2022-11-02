package ru.practicum.ewm.service.compilation.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.dto.compilation.CompilationDto;
import ru.practicum.ewm.dto.compilation.NewCompilationDto;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.mapper.compilation.CompilationMapper;
import ru.practicum.ewm.model.compilation.Compilation;
import ru.practicum.ewm.model.event.Event;
import ru.practicum.ewm.repository.compilation.CompilationEventRepository;
import ru.practicum.ewm.repository.compilation.CompilationRepository;
import ru.practicum.ewm.repository.event.EventRepository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static ru.practicum.ewm.util.DefaultValues.COMPILATION_NOT_FOUND;
import static ru.practicum.ewm.util.DefaultValues.EVENT_NOT_FOUND;

/**
 * Class which describes Compilation service of Admin api
 *
 * @author Timur Kiyamov
 * @version 1.0
 */
@Service
@Transactional
public class AdminCompilationServiceImpl implements AdminCompilationService {

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

    /**
     * Method of service which adding compilation
     *
     * @param dto
     * @return CompilationDto
     */
    @Override
    public CompilationDto addCompilation(NewCompilationDto dto) {
        Objects.requireNonNull(dto);
        List<Event> eventList = dto.getEvents().stream()
                .map(a -> eventRepository.findById(a)
                        .orElseThrow(() -> new NotFoundException(EVENT_NOT_FOUND + a)))
                .collect(Collectors.toList());
        return CompilationMapper.toDto(compilationRepository.save(CompilationMapper.toModel(dto, eventList)));
    }

    /**
     * Method of service which deleting compilation
     *
     * @param compId
     */
    @Override
    @Transactional
    public void deleteCompilation(Long compId) {
        compilationRepository.deleteById(compId);
    }

    /**
     * Method of service which deleting Event from Compilation
     *
     * @param compId
     * @param eventId
     */
    @Override
    @Transactional
    public void deleteEventFromCompilation(Long compId, Long eventId) {
        checkCompilationExists(compId);
        checkEventExists(eventId);
        compilationEventRepository.deleteByCompilationIdAndEventId(compId, eventId);
    }

    /**
     * Method of service which adding Event ot Compilation
     *
     * @param compId
     * @param eventId
     */
    @Override
    public void addEventToCompilation(Long compId, Long eventId) {
        checkCompilationExists(compId);
        checkEventExists(eventId);
        compilationEventRepository.save(CompilationMapper.toModel(compId, eventId));
    }

    /**
     * Method of service which unpin Compilation from main page (frontend)
     *
     * @param compId
     */
    @Override
    public void unpinCompilation(Long compId) {
        Compilation compilation = checkCompilationExists(compId);
        if (compilation.getPinned()) {
            compilation.setPinned(false);
        }
        compilationRepository.save(compilation);
    }

    /**
     * Method of service which pin Compilation to main page (frontend)
     *
     * @param compId
     */
    @Override
    public void pinCompilation(Long compId) {
        Compilation compilation = checkCompilationExists(compId);
        if (!compilation.getPinned()) {
            compilation.setPinned(true);
        }
        compilationRepository.save(compilation);
    }

    /**
     * Method of service which check Compilation existence
     *
     * @param compId
     * @return Compilation
     */
    private Compilation checkCompilationExists(Long compId) {
        return compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException(COMPILATION_NOT_FOUND + compId));
    }

    /**
     * Method of service which check Event existence
     *
     * @param eventId
     */
    private void checkEventExists(Long eventId) {
        eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(EVENT_NOT_FOUND + eventId));
    }
}