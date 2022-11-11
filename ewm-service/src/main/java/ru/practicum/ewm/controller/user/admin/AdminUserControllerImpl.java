package ru.practicum.ewm.controller.user.admin;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.controller.user.admin.AdminUserController;
import ru.practicum.ewm.dto.user.NewUserDto;
import ru.practicum.ewm.dto.user.UserDto;
import ru.practicum.ewm.model.user.User;
import ru.practicum.ewm.service.user.AdminUserService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

import static ru.practicum.ewm.util.DefaultValues.DEFAULT_FROM_VALUE;
import static ru.practicum.ewm.util.DefaultValues.DEFAULT_SIZE_VALUE;
/**
 * Class describing user controller for Admin api.
 * @author Timur Kiyamov
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping(path = "/admin/users")
public class AdminUserControllerImpl implements AdminUserController {
    private final AdminUserService adminUserService;

    @Autowired
    public AdminUserControllerImpl(AdminUserService adminUserService) {
        this.adminUserService = adminUserService;
    }

    /**
     * Endpoint of controller which get User List
     * @param ids
     * @param from
     * @param size
     * @return List of UserDto
     */
    @Override
    @GetMapping
    public List<UserDto> getUsers(@RequestParam List<Long> ids,
                                  @RequestParam(defaultValue = DEFAULT_FROM_VALUE) @Min(0) Integer from,
                                  @RequestParam(defaultValue = DEFAULT_SIZE_VALUE) @PositiveOrZero Integer size) {
        log.info("Get users ids:{},from:{},size:{}", ids, from, size);
        return adminUserService.getUsers(ids, from, size);
    }

    /**
     * Endpoint of controller which add user
     * @param newUser
     * @return User
     */
    @Override
    @PostMapping
    public User addUser(@RequestBody @Valid NewUserDto newUser) {
        log.info("Add user dto:{}", newUser);
        return adminUserService.addUser(newUser);
    }

    /**
     * Endpoint of controller which delete user
     * @param userId
     */
    @Override
    @DeleteMapping("{userId}")
    public void deleteUser(@PathVariable Long userId) {
        log.info("Delete user id:{}", userId);
        adminUserService.deleteUser(userId);
    }
}