package ru.practicum.ewm.event.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String annotation;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    private Long confirmedRequests;
    private String createdOn;
    private String description;
    private String eventDate;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User initiator;
    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;
    private Boolean paid;
    private int participantLimit;
    private LocalDateTime publishedOn;
    private PublicationState state;
    private String title;
    private Long views;
}
