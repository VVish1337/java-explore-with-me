package ru.practicum.ewm.service.category.publicsrv;

import ru.practicum.ewm.model.category.Category;

import java.util.List;

/**
 * Interface which describes category service of Public api
 *
 * @author Timur Kiyamov
 * @version 1.0
 */
public interface PublicCategoryService {
    /**
     * Method of class which getting  List of categories
     *
     * @param from
     * @param size
     * @return List of Category
     */
    List<Category> getAllCategories(int from, int size);

    /**
     * Method of class which getting Category by ID
     *
     * @param categoryId
     * @return Category
     */
    Category getCategoryById(Long categoryId);
}
