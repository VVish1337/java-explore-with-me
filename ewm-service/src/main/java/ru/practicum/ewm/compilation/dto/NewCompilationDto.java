package ru.practicum.ewm.compilation.dto;

import lombok.*;

import javax.persistence.Entity;
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
