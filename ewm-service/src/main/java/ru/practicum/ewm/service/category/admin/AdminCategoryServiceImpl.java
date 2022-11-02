package ru.practicum.ewm.service.category.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.category.PatchCategoryDto;
import ru.practicum.ewm.dto.category.PostCategoryDto;
import ru.practicum.ewm.mapper.category.CategoryMapper;
import ru.practicum.ewm.model.category.Category;
import ru.practicum.ewm.repository.category.CategoryRepository;

import java.util.Objects;

/**
 * Class which describes category service of Admin api
 *
 * @author Timur Kiyamov
 * @version 1.0
 */
@Service
public class AdminCategoryServiceImpl implements AdminCategoryService {

    private final CategoryRepository repository;

    @Autowired
    public AdminCategoryServiceImpl(CategoryRepository repository) {
        this.repository = repository;
    }

    /**
     * Method of service which add Category
     *
     * @param categoryDto
     * @return Category
     */
    @Override
    public Category addCategory(PostCategoryDto categoryDto) {
        return repository.save(CategoryMapper.postDtoToCategory(categoryDto));
    }

    /**
     * Method of service which update Category
     *
     * @param categoryDto
     * @return Category
     */
    @Override
    public Category updateCategory(PatchCategoryDto categoryDto) {
        Objects.requireNonNull(categoryDto, "Category is empty");
        Category oldCategory = repository.findById(categoryDto.getId()).orElseThrow();
        if (categoryDto.getName() != null) {
            oldCategory.setName(categoryDto.getName());
        }
        return repository.save(oldCategory);
    }

    /**
     * Method of service which delete Category by ID
     *
     * @param categoryId
     */
    @Override
    public void deleteCategory(Long categoryId) {
        repository.deleteById(categoryId);
    }
}