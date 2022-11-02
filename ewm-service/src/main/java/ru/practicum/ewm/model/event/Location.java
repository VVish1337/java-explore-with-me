package ru.practicum.ewm.model.event;

import lombok.*;

import javax.persistence.*;

/**
 * Filter class for events
 * @author Timur Kiyamov
 * @version 1.0
 */

@Getter
@Setter
@Entity
@ToString
@Table(name = "locations")
@NoArgsConstructor
@AllArgsConstructor
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private Long id;
    private double lat;
    private double lon;
}