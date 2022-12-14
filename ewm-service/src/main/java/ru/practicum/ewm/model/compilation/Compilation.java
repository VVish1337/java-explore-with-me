package ru.practicum.ewm.model.compilation;

import lombok.*;
import ru.practicum.ewm.model.event.Event;

import javax.persistence.*;
import java.util.List;

/**
 * Class describing compilations of events
 * @author Timur Kiyamov
 * @version 1.0
 */

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "compilations")
public class Compilation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "compilation_id")
    private Long id;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "compilation_events",
            joinColumns = {@JoinColumn(name = "compilation_id")},
            inverseJoinColumns = {@JoinColumn(name = "event_id")}
    )
    private List<Event> events;
    private Boolean pinned;
    private String title;
}