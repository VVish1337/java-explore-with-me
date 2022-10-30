package ru.practicum.ewm.user.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.user.dto.NewUserDto;
import ru.practicum.ewm.user.dto.UserDto;
import ru.practicum.ewm.user.mapper.UserMapper;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.repository.UserRepository;
import ru.practicum.ewm.util.PaginationUtil;

import java.util.List;

@Service
public class AdminUserService {

    private final UserRepository userRepository;

    @Autowired
    public AdminUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User addUser(NewUserDto newUser) {
        return userRepository.save(UserMapper.toModel(newUser));
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public List<UserDto> getUsers(List<Long> users, Integer from, Integer size) {
        Pageable pageable = PaginationUtil.getPageable(from, size, Sort.unsorted());
        if (users == null) {
            return UserMapper.toDtoList(userRepository.findAll(pageable).toList());
        } else {
            return UserMapper.toDtoList(userRepository.findAllById(users));
        }
    }
}