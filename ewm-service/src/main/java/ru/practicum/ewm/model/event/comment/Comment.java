package ru.practicum.ewm.model.event.comment;

import lombok.*;
import ru.practicum.ewm.model.event.Event;
import ru.practicum.ewm.model.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Class describing comment
 *
 * @author Timur Kiyamov
 * @version 1.0
 */
@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;
    @Column(name = "comment_text")
    private String text;
    @Column(name = "created_on")
    private LocalDateTime createdOn;
}