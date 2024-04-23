package com.SneakStyle.ProductService.Service.Impl;

import com.SneakStyle.ProductService.Repository.CategoryRepository;
import com.SneakStyle.ProductService.Service.CategoryService;
import com.SneakStyle.ProductService.dto.CategoryDto;
import com.SneakStyle.ProductService.exception.RecordNotFoundException;
import com.SneakStyle.ProductService.model.Category;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = toEntity(categoryDto);
        category.setStatus(true);
        Category createdCategory = categoryRepository.save(category);
        return toDto(createdCategory);
    }

    @Override
    public List<CategoryDto> getAllCategories(Boolean status) {
        List<Category> categories = categoryRepository.findAllByStatusOrderByIdDesc(status);
        return categories.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Category Not found at id => %d", id)));
        return toDto(category);
    }

    @Override
    @Transactional
    public CategoryDto update(Long id, CategoryDto categoryDto) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Category Not found at id => %d", id)));

        existingCategory.setName(categoryDto.getName());
        existingCategory.setDescription(categoryDto.getDescription());

        Category updatedCategory = categoryRepository.save(existingCategory);
        return toDto(updatedCategory);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Category Not found at id => %d", id)));
        categoryRepository.setStatusWhereId(category.getId(), false);
    }

    @Override
    @Transactional
    public void setToActive(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Category Not found at id => %d", id)));
        categoryRepository.setStatusWhereId(category.getId(), true);
    }

    public CategoryDto toDto(Category category){
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .status(category.getStatus())
                .build();
    }

    public Category toEntity(CategoryDto categoryDto){
        return Category.builder()
                .id(categoryDto.getId())
                .name(categoryDto.getName())
                .description(categoryDto.getDescription())
                .status(categoryDto.getStatus())
                .build();
    }
}
