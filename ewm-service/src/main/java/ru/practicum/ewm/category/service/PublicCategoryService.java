package ru.practicum.ewm.category.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.category.repository.CategoryRepository;
import ru.practicum.ewm.util.PaginationUtil;

import java.util.List;

@Service
public class PublicCategoryService {
    public final CategoryRepository repository;

    @Autowired
    public PublicCategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public List<Category> getAllCategories(int from, int size) {
        return repository.findAll(PaginationUtil.getPageable(from,size, Sort.unsorted())).toList();
    }

    public Category getCategoryById(Long categoryId) {
        return repository.findById(categoryId).orElseThrow();
    }
}