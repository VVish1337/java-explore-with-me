package ru.practicum.ewm.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * Class which describes new user dto
 *
 * @author Timur Kiyamov
 * @version 1.0
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NewUserDto {
    @NotBlank
    private String email;
    @NotBlank
    private String name;
}