package ru.practicum.ewm.model.compilation;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
public class CompilationEventId implements Serializable {
    private Long compilationId;
    private Long eventId;
}