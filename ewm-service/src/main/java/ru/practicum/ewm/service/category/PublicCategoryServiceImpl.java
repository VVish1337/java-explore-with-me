package ru.practicum.ewm.service.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.model.category.Category;
import ru.practicum.ewm.repository.category.CategoryRepository;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.util.PaginationUtil;

import java.util.List;

@Service
public class PublicCategoryServiceImpl implements PublicCategoryService{
    public final CategoryRepository repository;

    @Autowired
    public PublicCategoryServiceImpl(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Category> getAllCategories(int from, int size) {
        return repository.findAll(PaginationUtil.getPageable(from, size, Sort.unsorted())).toList();
    }

    @Override
    public Category getCategoryById(Long categoryId) {
        return repository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Not found category with id" + categoryId));
    }
}