package ru.practicum.ewm.mapper.category;

import ru.practicum.ewm.dto.category.CategoryDto;
import ru.practicum.ewm.dto.category.PostCategoryDto;
import ru.practicum.ewm.model.category.Category;

public final class CategoryMapper {
    public static Category postDtoToCategory(PostCategoryDto dto) {
        return new Category(null, dto.getName());
    }

    public static CategoryDto toDto(Category category) {
        return new CategoryDto(category.getId(), category.getName());
    }
}