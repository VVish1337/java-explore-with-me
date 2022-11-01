package ru.practicum.ewm.controller.category.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.category.PatchCategoryDto;
import ru.practicum.ewm.dto.category.PostCategoryDto;
import ru.practicum.ewm.model.category.Category;
import ru.practicum.ewm.service.category.admin.AdminCategoryService;

@Slf4j
@RestController
@RequestMapping(path = "/admin/categories")
public class AdminCategoryControllerImpl implements AdminCategoryController {
    private final AdminCategoryService adminCategoryService;

    @Autowired
    public AdminCategoryControllerImpl(AdminCategoryService adminCategoryService) {
        this.adminCategoryService = adminCategoryService;
    }

    @Override
    @PostMapping
    public Category addCategory(@RequestBody PostCategoryDto categoryDto) {
        log.info("add category :{}", categoryDto);
        return adminCategoryService.addCategory(categoryDto);
    }

    @Override
    @PatchMapping
    public Category updateCategory(@RequestBody PatchCategoryDto categoryDto) {
        log.info("add update category :{}", categoryDto);
        return adminCategoryService.updateCategory(categoryDto);
    }

    @Override
    @DeleteMapping("/{categoryId}")
    public void deleteCategory(@PathVariable Long categoryId) {
        log.info("delete category id:{}", categoryId);
        adminCategoryService.deleteCategory(categoryId);
    }
}