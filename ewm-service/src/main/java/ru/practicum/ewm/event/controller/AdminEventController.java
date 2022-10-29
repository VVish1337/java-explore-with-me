package ru.practicum.ewm.event.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.dto.AdminUpdateEventDto;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.service.AdminEventService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin/events")
public class AdminEventController {

    private final AdminEventService service;

    @Autowired
    public AdminEventController(AdminEventService service) {
        this.service = service;
    }

    @GetMapping
    public List<EventFullDto> getFilteredEvents(@RequestParam List<Long> users,
                                                @RequestParam List<String> states,
                                                @RequestParam List<Long> categories,
                                                @RequestParam String rangeStart,
                                                @RequestParam String rangeEnd,
                                                @RequestParam Integer from,
                                                @RequestParam Integer size){
        log.info("get Filtered events ");
        return service.getFilteredEvents(users,states,categories,rangeStart,rangeEnd,from,size);
    }

    @PutMapping("/{eventId}")
    public EventFullDto updateEventByAdmin(@PathVariable Long eventId,
                                           @RequestBody AdminUpdateEventDto dto){
        log.info("update event by admin id:{},dto:{}",eventId,dto);
        return service.updateEventByAdmin(eventId, dto);
    }

    @PatchMapping("/{eventId}/publish")
    public EventFullDto publishEvent(@PathVariable Long eventId){
        log.info("publish event id:"+eventId);
        return service.publishEvent(eventId);
    }

    @PatchMapping("/{eventId}/reject")
    public EventFullDto rejectEvent(@PathVariable Long eventId){
        log.info("reject event id:"+eventId);
        return service.rejectEvent(eventId);
    }
}
