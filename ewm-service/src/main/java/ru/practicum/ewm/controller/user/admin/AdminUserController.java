package ru.practicum.ewm.controller.user.admin;

import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.user.NewUserDto;
import ru.practicum.ewm.dto.user.UserDto;
import ru.practicum.ewm.model.user.User;

import javax.validation.constraints.Min;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

import static ru.practicum.ewm.util.DefaultValues.DEFAULT_FROM_VALUE;
import static ru.practicum.ewm.util.DefaultValues.DEFAULT_SIZE_VALUE;
/**
 * Interface describing user controller for Admin api.
 * @author Timur Kiyamov
 * @version 1.0
 */
@RequestMapping(path = "/admin/users")
public interface AdminUserController {
    /**
     * Endpoint of controller which get User List
     * @param ids
     * @param from
     * @param size
     * @return List of UserDto
     */
    @GetMapping
    List<UserDto> getUsers(@RequestParam List<Long> ids,
                           @RequestParam(defaultValue = DEFAULT_FROM_VALUE) @Min(0) Integer from,
                           @RequestParam(defaultValue = DEFAULT_SIZE_VALUE) @PositiveOrZero Integer size);

    /**
     * Endpoint of controller which add user
     * @param newUser
     * @return User
     */
    @PostMapping
    User addUser(@RequestBody NewUserDto newUser);

    /**
     * Endpoint of controller which delete user
     * @param userId
     */
    @DeleteMapping("{userId}")
    void deleteUser(@PathVariable Long userId);
}