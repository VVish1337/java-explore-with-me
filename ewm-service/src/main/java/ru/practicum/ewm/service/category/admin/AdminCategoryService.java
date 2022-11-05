package ru.practicum.ewm.service.category.admin;

import ru.practicum.ewm.dto.category.PatchCategoryDto;
import ru.practicum.ewm.dto.category.PostCategoryDto;
import ru.practicum.ewm.model.category.Category;

/**
 * Interface which describes category service of Admin api
 *
 * @author Timur Kiyamov
 * @version 1.0
 */
public interface AdminCategoryService {
    /**
     * Method of service which add Category
     *
     * @param categoryDto
     * @return Category
     */
    Category addCategory(PostCategoryDto categoryDto);

    /**
     * Method of service which update Category
     *
     * @param categoryDto
     * @return Category
     */
    Category updateCategory(PatchCategoryDto categoryDto);

    /**
     * Method of service which delete Category by ID
     *
     * @param categoryId
     */
    void deleteCategory(Long categoryId);
}
