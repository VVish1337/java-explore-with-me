package ru.practicum.ewm.user.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.user.dto.NewUserDto;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.service.AdminUserService;

import java.util.List;

@RestController()
@RequestMapping(path="/admin/users")
public class AdminUserController {
    private final AdminUserService adminUserService;

    @Autowired
    public AdminUserController(AdminUserService adminUserService) {
        this.adminUserService = adminUserService;
    }

//    @GetMapping
//    public List<User> getUsers() {
//        return adminUserService.getUsers();
//    }

    @PostMapping
    public User addUser(@RequestBody NewUserDto newUser) {
        return adminUserService.addUser(newUser);
    }

    @DeleteMapping("{userId}")
    public void deleteUser(@PathVariable Long userId) {
        adminUserService.deleteUser(userId);
    }
}
