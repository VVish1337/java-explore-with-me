package ru.practicum.ewm.model.request;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "participation")
public class ParticipationRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participation_id")
    private Long id;
    private LocalDateTime created;
    @Column(name = "event_id")
    private Long eventId;
    @Column(name = "requester_id")
    private Long requester;
    @Column(name = "state_name")
    private Status status;
}