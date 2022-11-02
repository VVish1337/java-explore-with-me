package ru.practicum.ewm.service.compilation.publicsrv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.compilation.CompilationDto;
import ru.practicum.ewm.mapper.compilation.CompilationMapper;
import ru.practicum.ewm.repository.compilation.CompilationRepository;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.service.compilation.publicsrv.PublicCompilationService;
import ru.practicum.ewm.util.PaginationUtil;

import java.util.List;
/**
 * Class which describes Compilation service of Public api
 *
 * @author Timur Kiyamov
 * @version 1.0
 */
@Service
public class PublicCompilationServiceImpl implements PublicCompilationService {

    private final CompilationRepository compilationRepository;

    @Autowired
    public PublicCompilationServiceImpl(CompilationRepository compilationRepository) {
        this.compilationRepository = compilationRepository;
    }

    /**
     * Method of service which get Compilation List
     * @param pinned
     * @param from
     * @param size
     * @return List of CompilationDto
     */
    @Override
    public List<CompilationDto> getCompilationList(Boolean pinned, Integer from, Integer size) {
        return CompilationMapper.toDtoList(compilationRepository
                .findAllByPinned(pinned, PaginationUtil.getPageable(from, size, Sort.unsorted())).toList());
    }

    /**
     * Method of service which get Compilation by ID
     * @param compId
     * @return
     */
    @Override
    public CompilationDto getCompilationById(Long compId) {
        return CompilationMapper.toDto(compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException("Compilation not found id:" + compId)));
    }
}