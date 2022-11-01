package ru.practicum.ewm.controller.category;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.ewm.model.category.Category;

import java.util.List;

import static ru.practicum.ewm.util.DefaultValues.DEFAULT_FROM_VALUE;
import static ru.practicum.ewm.util.DefaultValues.DEFAULT_SIZE_VALUE;

@RequestMapping(path = "/categories")
public interface PublicCategoryController {
    @GetMapping
    List<Category> getAllCategories(@RequestParam(defaultValue = DEFAULT_FROM_VALUE) int from,
                                    @RequestParam(defaultValue = DEFAULT_SIZE_VALUE) int size);

    @GetMapping("{catId}")
    Category getCategoryById(@PathVariable Long catId);
}