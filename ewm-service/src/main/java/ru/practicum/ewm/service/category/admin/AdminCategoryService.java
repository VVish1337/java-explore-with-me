package ru.practicum.ewm.service.category.admin;

import ru.practicum.ewm.dto.category.PatchCategoryDto;
import ru.practicum.ewm.dto.category.PostCategoryDto;
import ru.practicum.ewm.model.category.Category;

public interface AdminCategoryService {
    Category addCategory(PostCategoryDto categoryDto);

    Category updateCategory(PatchCategoryDto categoryDto);

    void deleteCategory(Long categoryId);
}
