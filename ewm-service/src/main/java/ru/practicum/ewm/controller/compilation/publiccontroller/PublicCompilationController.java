package ru.practicum.ewm.controller.compilation.publiccontroller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.ewm.dto.compilation.CompilationDto;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.util.List;

import static ru.practicum.ewm.util.DefaultValues.DEFAULT_FROM_VALUE;
import static ru.practicum.ewm.util.DefaultValues.DEFAULT_SIZE_VALUE;

/**
 * Interface describing compilation controller for Public api.
 *
 * @author Timur Kiyamov
 * @version 1.0
 */


@RequestMapping("/compilations")
public interface PublicCompilationController {
    /**
     * Endpoint of controller which get List of Compilations
     *
     * @param pinned Boolean variable. Which showing we need only pinned compilation or no
     * @param from   Integer from variable. From which element we start
     * @param size   Integer size variable. How many elements we need
     * @return List of CompilationDto
     */
    @GetMapping
    List<CompilationDto> getCompilationList(@RequestParam(defaultValue = "false") Boolean pinned,
                                            @Min(0) @RequestParam(defaultValue = DEFAULT_FROM_VALUE)
                                            Integer from,
                                            @Positive @RequestParam(defaultValue = DEFAULT_SIZE_VALUE)
                                            Integer size);

    /**
     * Endpoint of controller which get compilation by ID
     *
     * @param compId ID of compilation
     * @return CompilationDto
     */
    @GetMapping("/{compId}")
    CompilationDto getCompilationById(@PathVariable Long compId);
}