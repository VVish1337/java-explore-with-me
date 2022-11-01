package ru.practicum.ewm.service.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.user.NewUserDto;
import ru.practicum.ewm.dto.user.UserDto;
import ru.practicum.ewm.mapper.user.UserMapper;
import ru.practicum.ewm.model.user.User;
import ru.practicum.ewm.repository.user.UserRepository;
import ru.practicum.ewm.util.PaginationUtil;

import java.util.List;

@Service
public class AdminUserServiceImpl implements AdminUserService {

    private final UserRepository userRepository;

    @Autowired
    public AdminUserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User addUser(NewUserDto newUser) {
        return userRepository.save(UserMapper.toModel(newUser));
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public List<UserDto> getUsers(List<Long> users, Integer from, Integer size) {
        Pageable pageable = PaginationUtil.getPageable(from, size, Sort.unsorted());
        if (users == null) {
            return UserMapper.toDtoList(userRepository.findAll(pageable).toList());
        } else {
            return UserMapper.toDtoList(userRepository.findAllById(users));
        }
    }
}