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
import java.util.Objects;

/**
 * Class which describes User service of Admin api
 *
 * @author Timur Kiyamov
 * @version 1.0
 */
@Service
public class AdminUserServiceImpl implements AdminUserService {

    private final UserRepository userRepository;

    @Autowired
    public AdminUserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Method of service which add new User
     *
     * @param newUser
     * @return User
     */
    @Override
    public User addUser(NewUserDto newUser) {
        Objects.requireNonNull(newUser);
        return userRepository.save(UserMapper.toModel(newUser));
    }

    /**
     * Method of service which delete User
     *
     * @param userId
     */
    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    /**
     * Method of service which get User list
     *
     * @param users
     * @param from
     * @param size
     * @return List of UserDto
     */
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