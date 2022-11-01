package ru.practicum.ewm.service.user;

import ru.practicum.ewm.dto.user.NewUserDto;
import ru.practicum.ewm.dto.user.UserDto;
import ru.practicum.ewm.model.user.User;

import java.util.List;

public interface AdminUserService {
    User addUser(NewUserDto newUser);

    void deleteUser(Long userId);

    List<UserDto> getUsers(List<Long> users, Integer from, Integer size);
}
