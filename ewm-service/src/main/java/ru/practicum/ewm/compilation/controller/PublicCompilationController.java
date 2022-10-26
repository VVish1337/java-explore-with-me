package ru.practicum.ewm.compilation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.compilation.dto.CompilationDto;
import ru.practicum.ewm.compilation.service.PublicCompilationService;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.util.List;

import static ru.practicum.ewm.util.DefaultValues.DEFAULT_FROM_VALUE;
import static ru.practicum.ewm.util.DefaultValues.DEFAULT_SIZE_VALUE;

@Validated
@RestController
@RequestMapping("/compilations")
public class PublicCompilationController {
    private final PublicCompilationService service;

    @Autowired
    public PublicCompilationController(PublicCompilationService service) {
        this.service = service;
    }

    @GetMapping()
    public List<CompilationDto> getCompilationList(@RequestParam Boolean pinned,
                                                   @Min(0) @RequestParam(defaultValue = DEFAULT_FROM_VALUE)
                                                   Integer from,
                                                   @Positive @RequestParam(defaultValue = DEFAULT_SIZE_VALUE)
                                                   Integer size) {
//        return service.getCompilationList(pinned,from, size);
        return null;
    }

    @GetMapping("/{compId}")
    public CompilationDto getCompilationById(@PathVariable Long compId){
        return null;
    }
}
