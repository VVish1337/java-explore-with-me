package ru.practicum.ewm.controller.compilation.publiccontroller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.compilation.CompilationDto;
import ru.practicum.ewm.service.compilation.publicsrv.PublicCompilationService;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.util.List;

import static ru.practicum.ewm.util.DefaultValues.DEFAULT_FROM_VALUE;
import static ru.practicum.ewm.util.DefaultValues.DEFAULT_SIZE_VALUE;

/**
 * Class describing compilation controller for Public api.
 *
 * @author Timur Kiyamov
 * @version 1.0
 */

@Slf4j
@Validated
@RestController
@RequestMapping("/compilations")
public class PublicCompilationControllerImpl implements PublicCompilationController {
    private final PublicCompilationService service;

    @Autowired
    public PublicCompilationControllerImpl(PublicCompilationService service) {
        this.service = service;
    }

    /**
     * Endpoint of controller which get List of Compilations
     *
     * @param pinned Boolean variable. Which showing we need only pinned compilation or no
     * @param from   Integer from variable. From which element we start
     * @param size   Integer size variable. How many elements we need
     * @return List of CompilationDto
     */
    @Override
    @GetMapping
    public List<CompilationDto> getCompilationList(@RequestParam(defaultValue = "false") Boolean pinned,
                                                   @Min(0) @RequestParam(defaultValue = DEFAULT_FROM_VALUE)
                                                   Integer from,
                                                   @Positive @RequestParam(defaultValue = DEFAULT_SIZE_VALUE)
                                                   Integer size) {
        log.info("Get compilation list pinned:{},from:{},size:{}", pinned, from, size);
        return service.getCompilationList(pinned, from, size);
    }

    /**
     * Endpoint of controller which get compilation by ID
     *
     * @param compId ID of compilation
     * @return CompilationDto
     */
    @Override
    @GetMapping("/{compId}")
    public CompilationDto getCompilationById(@PathVariable Long compId) {
        log.info("Get compilation by id:{}", compId);
        return service.getCompilationById(compId);
    }
}