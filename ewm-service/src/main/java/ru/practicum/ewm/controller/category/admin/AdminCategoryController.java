package ru.practicum.ewm.controller.category.admin;

import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.category.PatchCategoryDto;
import ru.practicum.ewm.dto.category.PostCategoryDto;
import ru.practicum.ewm.model.category.Category;

import javax.validation.Valid;

/**
 * Interface describing category controller for Admin api.
 *
 * @author Timur Kiyamov
 * @version 1.0
 */

@RequestMapping(path = "/admin/categories")
public interface AdminCategoryController {

    /**
     * Endpoint of controller which adding category
     *
     * @param categoryDto
     * @return Category
     */
    @PostMapping
    Category addCategory(@RequestBody @Valid PostCategoryDto categoryDto);

    /**
     * Endpoint of controller which updating category
     *
     * @param categoryDto
     * @return Category
     */
    @PatchMapping
    Category updateCategory(@RequestBody @Valid PatchCategoryDto categoryDto);

    /**
     * Endpoint of controller which delete category
     *
     * @param categoryId
     */
    @DeleteMapping("/{categoryId}")
    void deleteCategory(@PathVariable Long categoryId);
}