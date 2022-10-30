package ru.practicum.ewm.stat.controller;


import ru.practicum.ewm.stat.dto.HitDto;
import ru.practicum.ewm.stat.model.Hit;

import java.time.LocalDateTime;

import static ru.practicum.ewm.stat.StatService.formatter;

public class StatMapper {

    public static Hit toModel(HitDto dto) {
            return Hit.builder()
                .id(dto.getId())
                .app(dto.getApp())
                .uri(dto.getUri())
                .ip(dto.getIp())
                .timestamp(LocalDateTime.parse(dto.getTimestamp(),formatter))
                .build();
    }
}