package ru.practicum.ewm.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.model.user.User;

/**
 * Class describing jpa user repository
 *
 * @author Timur Kiyamov
 * @version 1.0
 */
public interface UserRepository extends JpaRepository<User, Long> {
}