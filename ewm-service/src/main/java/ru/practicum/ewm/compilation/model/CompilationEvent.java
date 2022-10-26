package ru.practicum.ewm.compilation.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "compilation_events")
public class CompilationEvent {
    @Id
    @Column(name = "compilation_id")
    private Long compilationId;
    @Id
    @Column(name = "event_id")
    private Long eventId;
}
