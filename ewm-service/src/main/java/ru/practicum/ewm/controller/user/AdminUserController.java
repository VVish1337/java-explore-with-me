package ru.practicum.ewm.controller.user;

import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.user.NewUserDto;
import ru.practicum.ewm.dto.user.UserDto;
import ru.practicum.ewm.model.user.User;

import javax.validation.constraints.Min;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

import static ru.practicum.ewm.util.DefaultValues.DEFAULT_FROM_VALUE;
import static ru.practicum.ewm.util.DefaultValues.DEFAULT_SIZE_VALUE;

@RequestMapping(path = "/admin/users")
public interface AdminUserController {
    @GetMapping
    List<UserDto> getUsers(@RequestParam List<Long> ids,
                           @RequestParam(defaultValue = DEFAULT_FROM_VALUE) @Min(0) Integer from,
                           @RequestParam(defaultValue = DEFAULT_SIZE_VALUE) @PositiveOrZero Integer size);

    @PostMapping
    User addUser(@RequestBody NewUserDto newUser);

    @DeleteMapping("{userId}")
    void deleteUser(@PathVariable Long userId);
}