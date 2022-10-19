package ru.practicum.ewm.user.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.user.dto.NewUserDto;
import ru.practicum.ewm.user.mapper.UserMapper;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.repository.UserRepository;

@Service
public class AdminUserService {

    private final UserRepository userRepository;

    @Autowired
    public AdminUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User addUser(NewUserDto newUser) {
        return userRepository.save(UserMapper.newUserDtoToUser(newUser));
    }
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

//    private User checkUserExists(Long userId) {
//        return userRepository.findById(userId)
//                .orElseThrow(() -> new IllegalArgumentException("User not found"));
//    }
}
