package ru.practicum.ewm.dto.compilation;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewCompilationDto {
    private List<Long> events;
    private Boolean pinned;
    private String title;
}