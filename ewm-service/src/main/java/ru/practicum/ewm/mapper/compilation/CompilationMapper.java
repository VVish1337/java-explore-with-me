package ru.practicum.ewm.mapper.compilation;

import ru.practicum.ewm.dto.compilation.CompilationDto;
import ru.practicum.ewm.dto.compilation.NewCompilationDto;
import ru.practicum.ewm.model.compilation.Compilation;
import ru.practicum.ewm.model.compilation.CompilationEvent;
import ru.practicum.ewm.mapper.event.EventMapper;
import ru.practicum.ewm.model.event.Event;

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