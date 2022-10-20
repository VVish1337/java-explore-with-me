package ru.practicum.ewm.category.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.category.service.PublicCategoryService;

import java.util.List;

@RestController
@RequestMapping(path="/categories")
public class PublicCategoryController {
    private final PublicCategoryService service;

    @Autowired
    public PublicCategoryController(PublicCategoryService service) {
        this.service = service;
    }

    public List<Category> getAllCategories(int from, int size) {
        return service.getAllCategories(from, size);
    }

    public Category getCategoryById(Long categoryId) {
        return service.getCategoryById(categoryId);
    }
}
