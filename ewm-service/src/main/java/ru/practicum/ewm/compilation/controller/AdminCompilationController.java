package ru.practicum.ewm.compilation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.compilation.dto.CompilationDto;
import ru.practicum.ewm.compilation.dto.NewCompilationDto;
import ru.practicum.ewm.compilation.service.AdminCompilationService;

@RestController
@RequestMapping("/admin/compilations")
public class AdminCompilationController {
    private final AdminCompilationService service;

    @Autowired
    public AdminCompilationController(AdminCompilationService service) {
        this.service = service;
    }

    @PostMapping
    public CompilationDto addCompilation(@RequestBody NewCompilationDto dto){
        return service.addCompilation(dto);
    }

    @DeleteMapping("/{compId}")
    public void deleteCompilation(@PathVariable() Long compId){
//        return service.deleteCompilation(compId);
    }

    @DeleteMapping("/{compId}/events/{eventId}")
    public void deleteEventFromCompilation(@PathVariable Long compId,
                                           @PathVariable Long eventId){
//        return service.deleteEventFromCompilation(compId,eventId);
    }

    @PatchMapping("/{compId}/events/{eventId}")
    public void addEventToCompilation(@PathVariable Long compId,
                                      @PathVariable Long eventId){
//        return service.addEventToCompilation(compId, eventId);
    }

    @DeleteMapping("/{compId}/pin")
    public void unpinCompilation(@PathVariable Long compId){
//        return service.unpinCompilation(compId);
    }

    @PatchMapping("/{compId}/pin")
    public void pinCompilation(@PathVariable Long compId){
//        return service.pinCompilation(compId);
    }
}
