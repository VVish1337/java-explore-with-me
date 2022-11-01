package ru.practicum.ewm.service.category.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.category.PatchCategoryDto;
import ru.practicum.ewm.dto.category.PostCategoryDto;
import ru.practicum.ewm.mapper.category.CategoryMapper;
import ru.practicum.ewm.model.category.Category;
import ru.practicum.ewm.repository.category.CategoryRepository;

@Service
public class AdminCategoryServiceImpl implements AdminCategoryService{

    private final CategoryRepository repository;

    @Autowired
    public AdminCategoryServiceImpl(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public Category addCategory(PostCategoryDto categoryDto) {
        return repository.save(CategoryMapper.postDtoToCategory(categoryDto));
    }

    @Override
    public Category updateCategory(PatchCategoryDto categoryDto) {
        Category oldCategory = repository.findById(categoryDto.getId()).orElseThrow();
        if (categoryDto.getName() != null) {
            oldCategory.setName(categoryDto.getName());
        }
        return repository.save(oldCategory);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        repository.deleteById(categoryId);
    }
}