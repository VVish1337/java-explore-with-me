package ru.practicum.ewm.controller.compilation.admin;

import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.compilation.CompilationDto;
import ru.practicum.ewm.dto.compilation.NewCompilationDto;

@RequestMapping("/admin/compilations")
public interface AdminCompilationController {
    @PostMapping
    CompilationDto addCompilation(@RequestBody NewCompilationDto dto);

    @DeleteMapping("/{compId}")
    void deleteCompilation(@PathVariable() Long compId);

    @DeleteMapping("/{compId}/events/{eventId}")
    void deleteEventFromCompilation(@PathVariable Long compId,
                                    @PathVariable Long eventId);

    @PatchMapping("/{compId}/events/{eventId}")
    void addEventToCompilation(@PathVariable Long compId,
                               @PathVariable Long eventId);

    @DeleteMapping("/{compId}/pin")
    void unpinCompilation(@PathVariable Long compId);

    @PatchMapping("/{compId}/pin")
    void pinCompilation(@PathVariable Long compId);
}