package ru.practicum.ewm.event.controller;

import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.dto.AdminUpdateEventDto;
import ru.practicum.ewm.event.dto.EventFullDto;

import java.util.List;

@RestController
@RequestMapping("/admin/events")
public class AdminEventController {
    @GetMapping
    public List<EventFullDto> getFilteredEvents(@RequestParam Long[] users,
                                                @RequestParam String[] states,
                                                @RequestParam Long[] categories,
                                                @RequestParam String rangeStart,
                                                @RequestParam String rangeEnd,
                                                @RequestParam Integer from,
                                                @RequestParam Integer size){
        return null;
    }

    @PutMapping("/{eventId}")
    public EventFullDto updateEventByAdmin(@PathVariable Long eventId,
                                           @RequestBody AdminUpdateEventDto dto){
        return null;
    }

    @PatchMapping("/{eventId}/publish")
    public EventFullDto publishEvent(@PathVariable Long eventId){
        return null;
    }

    @PatchMapping("/{eventId}/reject")
    public EventFullDto rejectEvent(@PathVariable Long eventId){
        return null;
    }
}
