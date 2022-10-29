package ru.practicum.ewm.user.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.user.dto.NewUserDto;
import ru.practicum.ewm.user.dto.UserDto;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.service.AdminUserService;

import javax.validation.constraints.Min;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

import static ru.practicum.ewm.util.DefaultValues.DEFAULT_FROM_VALUE;
import static ru.practicum.ewm.util.DefaultValues.DEFAULT_SIZE_VALUE;

@Slf4j
@RestController
@RequestMapping(path="/admin/users")
public class AdminUserController {
    private final AdminUserService adminUserService;

    @Autowired
    public AdminUserController(AdminUserService adminUserService) {
        this.adminUserService = adminUserService;
    }

    @GetMapping
    public List<UserDto> getUsers(@RequestParam List<Long> ids,
                                  @RequestParam(defaultValue = DEFAULT_FROM_VALUE) @Min(0) Integer from,
                                  @RequestParam(defaultValue = DEFAULT_SIZE_VALUE) @PositiveOrZero Integer size) {
        log.info("Get users ids:{},from:{},size:{}",ids,from,size);
        return adminUserService.getUsers(ids,from,size);
    }

    @PostMapping
    public User addUser(@RequestBody NewUserDto newUser) {
        log.info("Add user dto:{}",newUser);
        return adminUserService.addUser(newUser);
    }

    @DeleteMapping("{userId}")
    public void deleteUser(@PathVariable Long userId) {
        log.info("Delete user id:{}",userId);
        adminUserService.deleteUser(userId);
    }
}
