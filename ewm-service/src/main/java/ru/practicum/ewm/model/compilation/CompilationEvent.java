package ru.practicum.ewm.model.compilation;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Builder
@IdClass(CompilationEventId.class)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "compilation_events")
public class CompilationEvent implements Serializable {
    @Id
    @Column(name = "compilation_id")
    private Long compilationId;
    @Id
    @Column(name = "event_id")
    private Long eventId;
}