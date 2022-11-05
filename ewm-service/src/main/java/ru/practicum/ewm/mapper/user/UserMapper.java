package ru.practicum.ewm.mapper.user;

import ru.practicum.ewm.dto.user.NewUserDto;
import ru.practicum.ewm.dto.user.UserDto;
import ru.practicum.ewm.dto.user.UserShortDto;
import ru.practicum.ewm.model.user.User;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Final class which describes category mapping from User to UserDto
 *
 * @author Timur Kiyamov
 * @version 1.0
 */
public final class UserMapper {
    public static User toModel(NewUserDto newUser) {
        return new User(null,
                newUser.getEmail(),
                newUser.getName());
    }

    public static UserShortDto toShortDto(User user) {
        return new UserShortDto(user.getId(),
                user.getName());
    }

    public static UserDto toDto(User user) {
        return new UserDto(user.getId(), user.getName(), user.getEmail());
    }

    public static List<UserDto> toDtoList(List<User> users) {
        return users.stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }
}