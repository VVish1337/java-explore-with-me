package ru.practicum.ewm.stat.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.stat.StatService;
import ru.practicum.ewm.stat.dto.HitDto;
import ru.practicum.ewm.stat.model.ViewStats;

import java.util.List;

@Slf4j
@RestController
@RequestMapping
public class StatController {

    private final StatService service;

    @Autowired
    public StatController(StatService service) {
        this.service = service;
    }

    @PostMapping("/hit")
    public void saveHit(@RequestBody HitDto hitDto) {
        log.info("save hit dto:{}",hitDto);
        service.saveHit(hitDto);
    }

    @GetMapping("/stats")
    public List<ViewStats> getStats(@RequestParam String start, @RequestParam String end, @RequestParam List<String> uris,
                                    @RequestParam(defaultValue = "false") Boolean unique) {
        return service.getStats(start, end, uris, unique);
    }
}