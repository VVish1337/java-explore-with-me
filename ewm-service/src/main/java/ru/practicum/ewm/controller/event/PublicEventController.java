package ru.practicum.ewm.controller.event;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.ewm.dto.event.EventFullDto;
import ru.practicum.ewm.dto.event.EventShortDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("/events")
public interface PublicEventController {
    @GetMapping("/{eventId}")
    EventFullDto getFullEventInfoById(@PathVariable Long eventId, HttpServletRequest request);

    @GetMapping
    List<EventShortDto> getFilteredEvents(@RequestParam String text,
                                          @RequestParam List<Long> categories,
                                          @RequestParam Boolean paid,
                                          @RequestParam String rangeStart,
                                          @RequestParam String rangeEnd,
                                          @RequestParam Boolean onlyAvailable,
                                          @RequestParam(defaultValue = "EVENT_DATE") String sort,
                                          @RequestParam Integer from,
                                          @RequestParam Integer size,
                                          HttpServletRequest request);
}