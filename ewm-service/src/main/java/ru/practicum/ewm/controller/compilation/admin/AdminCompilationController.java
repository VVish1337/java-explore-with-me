package ru.practicum.ewm.controller.compilation.admin;

import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.compilation.CompilationDto;
import ru.practicum.ewm.dto.compilation.NewCompilationDto;

import javax.validation.Valid;

/**
 * Interface describing compilation controller for Admin api.
 *
 * @author Timur Kiyamov
 * @version 1.0
 */

@RequestMapping("/admin/compilations")
public interface AdminCompilationController {
    /**
     * Endpoint of controller which add compilations
     *
     * @param dto
     * @return CompilationDto
     */
    @PostMapping
    CompilationDto addCompilation(@Valid @RequestBody NewCompilationDto dto);

    /**
     * Endpoint of controller which delete compilations
     *
     * @param compId
     */
    @DeleteMapping("/{compId}")
    void deleteCompilation(@PathVariable() Long compId);

    /**
     * Endpoint of controller which delete events from compilation
     *
     * @param compId
     * @param eventId
     */
    @DeleteMapping("/{compId}/events/{eventId}")
    void deleteEventFromCompilation(@PathVariable Long compId,
                                    @PathVariable Long eventId);

    /**
     * Endpoint of controller which adding events to compilations
     *
     * @param compId
     * @param eventId
     */
    @PatchMapping("/{compId}/events/{eventId}")
    void addEventToCompilation(@PathVariable Long compId,
                               @PathVariable Long eventId);

    /**
     * Endpoint of controller which unpin compilations from main page of frontend
     *
     * @param compId
     */
    @DeleteMapping("/{compId}/pin")
    void unpinCompilation(@PathVariable Long compId);

    /**
     * Endpoint of controller which pin compilations to main page of frontend
     *
     * @param compId
     */
    @PatchMapping("/{compId}/pin")
    void pinCompilation(@PathVariable Long compId);
}