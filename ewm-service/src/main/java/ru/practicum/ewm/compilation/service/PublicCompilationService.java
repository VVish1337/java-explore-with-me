package ru.practicum.ewm.compilation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.compilation.dto.CompilationDto;
import ru.practicum.ewm.compilation.mapper.CompilationMapper;
import ru.practicum.ewm.compilation.repository.CompilationRepository;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.util.PaginationUtil;

import java.util.List;

@Service
public class PublicCompilationService {

    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;

    @Autowired
    public PublicCompilationService(CompilationRepository compilationRepository, EventRepository eventRepository) {
        this.compilationRepository = compilationRepository;
        this.eventRepository = eventRepository;
    }

    public List<CompilationDto> getCompilationList(Boolean pinned, Integer from, Integer size) {
        return CompilationMapper.toDtoList(compilationRepository
                .findAllByPinned(pinned, PaginationUtil.getPageable(from, size, Sort.unsorted())).toList());
    }


    public CompilationDto getCompilationById(Long compId) {
        return CompilationMapper.toDto(compilationRepository.findById(compId)
                .orElseThrow(()->new NotFoundException("Compilation not found id:"+compId)));
    }
}
