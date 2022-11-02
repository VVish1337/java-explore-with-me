package ru.practicum.ewm.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.HitDto;
import ru.practicum.ewm.model.ViewStat;
import ru.practicum.ewm.service.StatService;

import java.util.List;

/**
 * Class which describes statistic controller
 *
 * @author Timur Kiyamov
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping
public class StatController {

    private final StatService service;


    @Autowired
    public StatController(StatService service) {
        this.service = service;
    }

    /**
     * Method of controller which get static from main-service
     *
     * @param hitDto
     */
    @PostMapping("/hit")
    public void saveHit(@RequestBody HitDto hitDto) {
        log.info("save hit dto:{}", hitDto);
        service.saveHit(hitDto);
    }

    /**
     * Method of controller which get statictic
     *
     * @param start
     * @param end
     * @param uris
     * @param unique
     * @return
     */
    @GetMapping("/stats")
    public List<ViewStat> getStats(@RequestParam String start, @RequestParam String end, @RequestParam List<String> uris,
                                   @RequestParam(defaultValue = "false") Boolean unique) {
        return service.getStats(start, end, uris, unique);
    }
}