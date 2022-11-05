package ru.practicum.ewm.controller.compilation.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.compilation.CompilationDto;
import ru.practicum.ewm.dto.compilation.NewCompilationDto;
import ru.practicum.ewm.service.compilation.admin.AdminCompilationService;

/**
 * Class describing compilation controller for Admin api.
 *
 * @author Timur Kiyamov
 * @version 1.0
 */

@Slf4j
@RestController
@RequestMapping("/admin/compilations")
public class AdminCompilationControllerImpl implements AdminCompilationController {
    private final AdminCompilationService service;

    @Autowired
    public AdminCompilationControllerImpl(AdminCompilationService service) {
        this.service = service;
    }


    /**
     * Endpoint of controller which add compilations
     *
     * @param dto
     * @return CompilationDto
     */
    @Override
    @PostMapping
    public CompilationDto addCompilation(@RequestBody NewCompilationDto dto) {
        log.info("add compilation dto:{}", dto);
        return service.addCompilation(dto);
    }

    /**
     * Endpoint of controller which delete compilations
     *
     * @param compId
     */
    @Override
    @DeleteMapping("/{compId}")
    public void deleteCompilation(@PathVariable() Long compId) {
        log.info("delete compilation id:{}", compId);
        service.deleteCompilation(compId);
    }

    /**
     * Endpoint of controller which delete events from compilation
     *
     * @param compId
     * @param eventId
     */
    @Override
    @DeleteMapping("/{compId}/events/{eventId}")
    public void deleteEventFromCompilation(@PathVariable Long compId,
                                           @PathVariable Long eventId) {
        log.info("delete compilation from event compId:{},eventId:{}", compId, eventId);
        service.deleteEventFromCompilation(compId, eventId);
    }

    /**
     * Endpoint of controller which adding events to compilations
     *
     * @param compId
     * @param eventId
     */
    @Override
    @PatchMapping("/{compId}/events/{eventId}")
    public void addEventToCompilation(@PathVariable Long compId,
                                      @PathVariable Long eventId) {
        log.info("add event to compilation compId:{},eventId:{}", compId, eventId);
        service.addEventToCompilation(compId, eventId);
    }

    /**
     * Endpoint of controller which unpin compilations from main page of frontend
     *
     * @param compId
     */
    @Override
    @DeleteMapping("/{compId}/pin")
    public void unpinCompilation(@PathVariable Long compId) {
        log.info("Unpin compilation id:{}", compId);
        service.unpinCompilation(compId);
    }

    /**
     * Endpoint of controller which pin compilations to main page of frontend
     *
     * @param compId
     */
    @Override
    @PatchMapping("/{compId}/pin")
    public void pinCompilation(@PathVariable Long compId) {
        log.info("Pin compilation id:{}", compId);
        service.pinCompilation(compId);
    }
}