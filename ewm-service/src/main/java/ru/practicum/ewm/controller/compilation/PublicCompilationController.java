package ru.practicum.ewm.controller.compilation;

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

@RequestMapping("/compilations")
public interface PublicCompilationController {
    @GetMapping()
    List<CompilationDto> getCompilationList(@RequestParam(defaultValue = "false") Boolean pinned,
                                            @Min(0) @RequestParam(defaultValue = DEFAULT_FROM_VALUE)
                                            Integer from,
                                            @Positive @RequestParam(defaultValue = DEFAULT_SIZE_VALUE)
                                            Integer size);

    @GetMapping("/{compId}")
    CompilationDto getCompilationById(@PathVariable Long compId);
}