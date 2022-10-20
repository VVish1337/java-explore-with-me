package ru.practicum.ewm.category.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.category.dto.PatchCategoryDto;
import ru.practicum.ewm.category.dto.PostCategoryDto;
import ru.practicum.ewm.category.mapper.CategoryMapper;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.category.repository.CategoryRepository;

@Service
public class AdminCategoryService {

    private final CategoryRepository repository;

    @Autowired
    public AdminCategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public Category addCategory(PostCategoryDto categoryDto) {
        return repository.save(CategoryMapper.postDtoToCategory(categoryDto));
    }

    public Category updateCategory(PatchCategoryDto categoryDto) {
        Category oldCategory = repository.findById(categoryDto.getId()).orElseThrow();
        if(categoryDto.getName()!=null){
            oldCategory.setName(categoryDto.getName());
        }
        return repository.save(oldCategory);
    }

    public void deleteCategory(Long categoryId) {
        repository.deleteById(categoryId);
    }
}
