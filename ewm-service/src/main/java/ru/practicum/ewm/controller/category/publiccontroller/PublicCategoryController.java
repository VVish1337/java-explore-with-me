package ru.practicum.ewm.controller.category.publiccontroller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.ewm.model.category.Category;

import java.util.List;

import static ru.practicum.ewm.util.DefaultValues.DEFAULT_FROM_VALUE;
import static ru.practicum.ewm.util.DefaultValues.DEFAULT_SIZE_VALUE;

/**
 * Interface describing category controller for Public api.
 *
 * @author Timur Kiyamov
 * @version 1.0
 */

@RequestMapping(path = "/categories")
public interface PublicCategoryController {

    /**
     * Endpoint of controller which getting all categories
     *
     * @param from
     * @param size
     * @return List of Category
     */
    @GetMapping
    List<Category> getAllCategories(@RequestParam(defaultValue = DEFAULT_FROM_VALUE) int from,
                                    @RequestParam(defaultValue = DEFAULT_SIZE_VALUE) int size);

    /**
     * Endpoint of controller which getting category by id
     *
     * @param catId
     * @return List of Category
     */
    @GetMapping("{catId}")
    Category getCategoryById(@PathVariable Long catId);
}