package ru.practicum.ewm.compilation.mapper;

import ru.practicum.ewm.compilation.dto.CompilationDto;
import ru.practicum.ewm.compilation.dto.NewCompilationDto;
import ru.practicum.ewm.compilation.model.Compilation;
import ru.practicum.ewm.compilation.model.CompilationEvent;
import ru.practicum.ewm.event.mapper.EventMapper;
import ru.practicum.ewm.event.model.Event;

import java.util.List;
import java.util.stream.Collectors;

public class CompilationMapper {
    public static Compilation toModel(NewCompilationDto dto, List<Event> eventList) {
        return Compilation.builder()
                .events(eventList)
                .pinned(dto.getPinned())
                .title(dto.getTitle())
                .build();
    }

    public static CompilationDto toDto(Compilation comp) {
        return CompilationDto.builder()
                .id(comp.getId())
                .events(EventMapper.toShortDtoList(comp.getEvents()))
                .title(comp.getTitle())
                .pinned(comp.getPinned())
                .build();
    }

    public static CompilationEvent toModel(Long compId, Long eventId) {
        return new CompilationEvent(compId, eventId);
    }

    public static List<CompilationDto> toDtoList(List<Compilation> compilationList) {
        return compilationList.stream()
                .map(CompilationMapper::toDto)
                .collect(Collectors.toList());
    }
}