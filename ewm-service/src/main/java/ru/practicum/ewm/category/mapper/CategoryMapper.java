package ru.practicum.ewm.category.mapper;

import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.dto.PatchCategoryDto;
import ru.practicum.ewm.category.dto.PostCategoryDto;
import ru.practicum.ewm.category.model.Category;

public class CategoryMapper {
    public static Category postDtoToCategory(PostCategoryDto dto){
        return new Category(null, dto.getName());
    }

    public static CategoryDto toDto(Category category){
        return new CategoryDto(category.getId(), category.getName());
    }
}
