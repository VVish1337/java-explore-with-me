package ru.practicum.ewm.mapper;


import ru.practicum.ewm.dto.HitDto;
import ru.practicum.ewm.model.Hit;

import java.time.LocalDateTime;

import static ru.practicum.ewm.service.StatServiceImpl.formatter;

/**
 * Final class which convert HitDto to Hit
 *
 * @author Timur Kiyamov
 * @version 1.0
 */
public final class StatMapper {

    public static Hit toModel(HitDto dto) {
        return Hit.builder()
                .id(dto.getId())
                .app(dto.getApp())
                .uri(dto.getUri())
                .ip(dto.getIp())
                .timestamp(LocalDateTime.parse(dto.getTimestamp(), formatter))
                .build();
    }
}