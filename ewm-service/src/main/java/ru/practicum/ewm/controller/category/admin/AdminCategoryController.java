package ru.practicum.ewm.controller.category.admin;

import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.category.PatchCategoryDto;
import ru.practicum.ewm.dto.category.PostCategoryDto;
import ru.practicum.ewm.model.category.Category;

@RequestMapping(path = "/admin/categories")
public interface AdminCategoryController {
    @PostMapping
    Category addCategory(@RequestBody PostCategoryDto categoryDto);

    @PatchMapping
    Category updateCategory(@RequestBody PatchCategoryDto categoryDto);

    @DeleteMapping("/{categoryId}")
    void deleteCategory(@PathVariable Long categoryId);
}