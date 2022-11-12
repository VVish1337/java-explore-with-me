package ru.practicum.ewm.dto.compilation;


import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Dto of new compilation class.
 *
 * @author Timur Kiyamov
 * @version 1.0
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewCompilationDto {
    private List<Long> events;
    private Boolean pinned;
    @NotNull
    private String title;
}