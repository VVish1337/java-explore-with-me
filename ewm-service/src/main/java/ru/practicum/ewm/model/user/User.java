package ru.practicum.ewm.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Class describing user
 * @author Timur Kiyamov
 * @version 1.0
 */

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "users")
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    private String email;
    @Column(name = "user_name")
    private String name;
}