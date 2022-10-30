package ru.practicum.ewm.stat.dto;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class HitDto {
    private long id;
    private String app;
    private String uri;
    private String ip;
    private String timestamp;
}
