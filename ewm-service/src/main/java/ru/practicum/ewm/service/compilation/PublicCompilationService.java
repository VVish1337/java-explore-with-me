package ru.practicum.ewm.service.compilation;

import ru.practicum.ewm.dto.compilation.CompilationDto;

import java.util.List;

public interface PublicCompilationService {
    List<CompilationDto> getCompilationList(Boolean pinned, Integer from, Integer size);

    CompilationDto getCompilationById(Long compId);
}