package ru.practicum.ewm.mapper.category;

import ru.practicum.ewm.dto.category.CategoryDto;
import ru.practicum.ewm.dto.category.PostCategoryDto;
import ru.practicum.ewm.model.category.Category;

/**
 * Final class which describes category mapping from Category to CategoryDto
 *
 * @author Timur Kiyamov
 * @version 1.0
 */

public final class CategoryMapper {
    public static Category postDtoToCategory(PostCategoryDto dto) {
        return new Category(null, dto.getName());
    }

    public static CategoryDto toDto(Category category) {
        return new CategoryDto(category.getId(), category.getName());
    }
}