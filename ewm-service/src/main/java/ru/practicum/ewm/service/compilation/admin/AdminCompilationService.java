package ru.practicum.ewm.service.compilation.admin;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.dto.compilation.CompilationDto;
import ru.practicum.ewm.dto.compilation.NewCompilationDto;

/**
 * Interface which describes Compilation service of Admin api
 *
 * @author Timur Kiyamov
 * @version 1.0
 */
public interface AdminCompilationService {
    /**
     * Method of service which adding compilation
     *
     * @param dto
     * @return CompilationDto
     */
    CompilationDto addCompilation(NewCompilationDto dto);

    /**
     * Method of service which deleting compilation
     *
     * @param compId
     */
    @Transactional
    void deleteCompilation(Long compId);

    /**
     * Method of service which deleting Event from Compilation
     *
     * @param compId
     * @param eventId
     */
    @Transactional
    void deleteEventFromCompilation(Long compId, Long eventId);

    /**
     * Method of service which adding Event ot Compilation
     *
     * @param compId
     * @param eventId
     */
    void addEventToCompilation(Long compId, Long eventId);

    /**
     * Method of service which unpin Compilation from main page (frontend)
     *
     * @param compId
     */
    void unpinCompilation(Long compId);

    /**
     * Method of service which pin Compilation to main page (frontend)
     *
     * @param compId
     */
    void pinCompilation(Long compId);
}
