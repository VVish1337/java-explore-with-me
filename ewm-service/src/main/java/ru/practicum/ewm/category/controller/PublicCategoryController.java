package ru.practicum.ewm.category.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.category.service.PublicCategoryService;

import java.util.List;

import static ru.practicum.ewm.util.DefaultValues.DEFAULT_FROM_VALUE;
import static ru.practicum.ewm.util.DefaultValues.DEFAULT_SIZE_VALUE;

@Slf4j
@Validated
@RestController
@RequestMapping(path = "/categories")
public class PublicCategoryController {
    private final PublicCategoryService service;

    @Autowired
    public PublicCategoryController(PublicCategoryService service) {
        this.service = service;
    }

    @GetMapping
    public List<Category> getAllCategories(@RequestParam(defaultValue = DEFAULT_FROM_VALUE) int from,
                                           @RequestParam(defaultValue = DEFAULT_SIZE_VALUE) int size) {
        log.info("get all categories from:{},size:{}", from, size);
        return service.getAllCategories(from, size);
    }

    @GetMapping("{catId}")
    public Category getCategoryById(@PathVariable Long catId) {
        log.info("get category by id:{}", catId);
        return service.getCategoryById(catId);
    }
}