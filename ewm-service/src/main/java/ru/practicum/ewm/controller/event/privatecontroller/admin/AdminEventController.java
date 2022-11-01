package ru.practicum.ewm.controller.event.privatecontroller.admin;

import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.event.AdminUpdateEventDto;
import ru.practicum.ewm.dto.event.EventFullDto;

import java.util.List;

@RequestMapping("/admin/events")
public interface AdminEventController {
    @GetMapping
    List<EventFullDto> getFilteredEvents(@RequestParam List<Long> users,
                                         @RequestParam List<String> states,
                                         @RequestParam List<Long> categories,
                                         @RequestParam String rangeStart,
                                         @RequestParam String rangeEnd,
                                         @RequestParam Integer from,
                                         @RequestParam Integer size);

    @PutMapping("/{eventId}")
    EventFullDto updateEventByAdmin(@PathVariable Long eventId,
                                    @RequestBody AdminUpdateEventDto dto);

    @PatchMapping("/{eventId}/publish")
    EventFullDto publishEvent(@PathVariable Long eventId);

    @PatchMapping("/{eventId}/reject")
    EventFullDto rejectEvent(@PathVariable Long eventId);
}