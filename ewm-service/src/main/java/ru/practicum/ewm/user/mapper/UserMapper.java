package ru.practicum.ewm.user.mapper;

import ru.practicum.ewm.user.dto.NewUserDto;
import ru.practicum.ewm.user.dto.UserDto;
import ru.practicum.ewm.user.dto.UserShortDto;
import ru.practicum.ewm.user.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {
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