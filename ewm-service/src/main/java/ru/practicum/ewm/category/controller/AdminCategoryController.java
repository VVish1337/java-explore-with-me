package ru.practicum.ewm.category.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.category.dto.PatchCategoryDto;
import ru.practicum.ewm.category.dto.PostCategoryDto;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.category.service.AdminCategoryService;

@Slf4j
@RestController
@RequestMapping(path="/admin/categories")
public class AdminCategoryController {
    private final AdminCategoryService adminCategoryService;

    @Autowired
    public AdminCategoryController(AdminCategoryService adminCategoryService) {
        this.adminCategoryService = adminCategoryService;
    }

    @PostMapping
    public Category addCategory(@RequestBody PostCategoryDto categoryDto) {
        log.info("add category :{}",categoryDto);
        return adminCategoryService.addCategory(categoryDto);
    }

    @PatchMapping
    public Category updateCategory(@RequestBody PatchCategoryDto categoryDto){
        log.info("add update category :{}",categoryDto);
        return adminCategoryService.updateCategory(categoryDto);
    }

    @DeleteMapping("/{categoryId}")
    public void deleteCategory(@PathVariable Long categoryId){
        log.info("delete category id:{}",categoryId);
        adminCategoryService.deleteCategory(categoryId);
    }
}