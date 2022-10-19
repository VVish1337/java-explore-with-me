package ru.practicum.ewm.user.mapper;

import ru.practicum.ewm.user.dto.NewUserDto;
import ru.practicum.ewm.user.model.User;

public class UserMapper {
    public static User newUserDtoToUser(NewUserDto newUser) {
        return new User(null,
                newUser.getEmail(),
                newUser.getName());
    }
}
