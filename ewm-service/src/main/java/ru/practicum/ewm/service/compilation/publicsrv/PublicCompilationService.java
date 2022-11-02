package ru.practicum.ewm.service.compilation.publicsrv;

import ru.practicum.ewm.dto.compilation.CompilationDto;

import java.util.List;
/**
 * Interface which describes Compilation service of Public api
 *
 * @author Timur Kiyamov
 * @version 1.0
 */
public interface PublicCompilationService {
    /**
     * Method of service which get Compilation List
     * @param pinned
     * @param from
     * @param size
     * @return List of CompilationDto
     */
    List<CompilationDto> getCompilationList(Boolean pinned, Integer from, Integer size);

    /**
     * Method of service which get Compilation by ID
     * @param compId
     * @return
     */
    CompilationDto getCompilationById(Long compId);
}