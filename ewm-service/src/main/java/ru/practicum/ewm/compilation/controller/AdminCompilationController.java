package ru.practicum.ewm.compilation.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.compilation.dto.CompilationDto;
import ru.practicum.ewm.compilation.dto.NewCompilationDto;
import ru.practicum.ewm.compilation.service.AdminCompilationService;

@Slf4j
@RestController
@RequestMapping("/admin/compilations")
public class AdminCompilationController {
    private final AdminCompilationService service;

    @Autowired
    public AdminCompilationController(AdminCompilationService service) {
        this.service = service;
    }

    @PostMapping
    public CompilationDto addCompilation(@RequestBody NewCompilationDto dto) {
        log.info("add compilation dto:{}", dto);
        return service.addCompilation(dto);
    }

    @DeleteMapping("/{compId}")
    public void deleteCompilation(@PathVariable() Long compId) {
        log.info("delete compilation id:{}", compId);
        service.deleteCompilation(compId);
    }

    @DeleteMapping("/{compId}/events/{eventId}")
    public void deleteEventFromCompilation(@PathVariable Long compId,
                                           @PathVariable Long eventId) {
        log.info("delete compilation from event compId:{},eventId:{}", compId, eventId);
        service.deleteEventFromCompilation(compId, eventId);
    }

    @PatchMapping("/{compId}/events/{eventId}")
    public void addEventToCompilation(@PathVariable Long compId,
                                      @PathVariable Long eventId) {
        log.info("add event to compilation compId:{},eventId:{}", compId, eventId);
        service.addEventToCompilation(compId, eventId);
    }

    @DeleteMapping("/{compId}/pin")
    public void unpinCompilation(@PathVariable Long compId) {
        log.info("Unpin compilation id:{}", compId);
        service.unpinCompilation(compId);
    }

    @PatchMapping("/{compId}/pin")
    public void pinCompilation(@PathVariable Long compId) {
        log.info("Pin compilation id:{}", compId);
        service.pinCompilation(compId);
    }
}