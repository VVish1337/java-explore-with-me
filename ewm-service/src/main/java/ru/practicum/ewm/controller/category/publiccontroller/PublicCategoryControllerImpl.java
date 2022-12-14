package ru.practicum.ewm.controller.category.publiccontroller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.model.category.Category;
import ru.practicum.ewm.service.category.publicsrv.PublicCategoryService;

import java.util.List;

import static ru.practicum.ewm.util.DefaultValues.DEFAULT_FROM_VALUE;
import static ru.practicum.ewm.util.DefaultValues.DEFAULT_SIZE_VALUE;

/**
 * Class describing category controller for Public api.
 *
 * @author Timur Kiyamov
 * @version 1.0
 */

@Slf4j
@Validated
@RestController
@RequestMapping(path = "/categories")
public class PublicCategoryControllerImpl implements PublicCategoryController {
    private final PublicCategoryService service;

    @Autowired
    public PublicCategoryControllerImpl(PublicCategoryService service) {
        this.service = service;
    }


    /**
     * Endpoint of controller which getting all categories
     *
     * @param from
     * @param size
     * @return List of Category
     */
    @Override
    @GetMapping
    public List<Category> getAllCategories(@RequestParam(defaultValue = DEFAULT_FROM_VALUE) int from,
                                           @RequestParam(defaultValue = DEFAULT_SIZE_VALUE) int size) {
        log.info("get all categories from:{},size:{}", from, size);
        return service.getAllCategories(from, size);
    }

    /**
     * Endpoint of controller which getting category by id
     *
     * @param catId
     * @return List of Category
     */
    @Override
    @GetMapping("{catId}")
    public Category getCategoryById(@PathVariable Long catId) {
        log.info("get category by id:{}", catId);
        return service.getCategoryById(catId);
    }
}