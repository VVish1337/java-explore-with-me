package ru.practicum.ewm.event.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.service.PublicEventService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/events")
public class PublicEventController {

    private final PublicEventService service;

    @Autowired
    public PublicEventController(PublicEventService service) {
        this.service = service;
    }

    @GetMapping("/{eventId}")
    public EventFullDto getFullEventInfoById(@PathVariable Long eventId, HttpServletRequest request){
        return service.getFullEventInfoById(eventId,request);
    }

    @GetMapping
    public List<EventShortDto> getFilteredEvents(@RequestParam String text,
                                                 @RequestParam List<Long> categories,
                                                 @RequestParam Boolean paid,
                                                 @RequestParam String rangeStart,
                                                 @RequestParam String rangeEnd,
                                                 @RequestParam Boolean onlyAvailable,
                                                 @RequestParam(defaultValue = "EVENT_DATE") String sort,
                                                 @RequestParam Integer from,
                                                 @RequestParam Integer size,
                                                 HttpServletRequest request){
        return service.getFilteredEvents(text,categories,paid,rangeStart,rangeEnd,onlyAvailable,sort,from,size,request);
    }
}
