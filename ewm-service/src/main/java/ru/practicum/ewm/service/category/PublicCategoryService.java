package ru.practicum.ewm.service.category;

import ru.practicum.ewm.model.category.Category;

import java.util.List;

public interface PublicCategoryService {
    List<Category> getAllCategories(int from, int size);

    Category getCategoryById(Long categoryId);
}
