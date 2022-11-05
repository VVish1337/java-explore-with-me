package ru.practicum.ewm.service.user;

import ru.practicum.ewm.dto.user.NewUserDto;
import ru.practicum.ewm.dto.user.UserDto;
import ru.practicum.ewm.model.user.User;

import java.util.List;

/**
 * Interface which describes User service of Admin api
 *
 * @author Timur Kiyamov
 * @version 1.0
 */
public interface AdminUserService {
    /**
     * Method of service which add new User
     *
     * @param newUser
     * @return User
     */
    User addUser(NewUserDto newUser);

    /**
     * Method of service which delete User
     *
     * @param userId
     */
    void deleteUser(Long userId);

    /**
     * Method of service which get User list
     *
     * @param users
     * @param from
     * @param size
     * @return List of UserDto
     */
    List<UserDto> getUsers(List<Long> users, Integer from, Integer size);
}
