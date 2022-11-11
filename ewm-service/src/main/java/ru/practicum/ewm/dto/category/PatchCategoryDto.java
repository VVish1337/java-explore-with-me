package ru.practicum.ewm.dto.category;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Dto of category class.
 *
 * @author Timur Kiyamov
 * @version 1.0
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatchCategoryDto {
    @NotNull
    private Long id;
    @NotNull
    @NotBlank
    private String name;
}