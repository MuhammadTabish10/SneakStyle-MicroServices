package com.SneakStyle.ProductService.Service;

import com.SneakStyle.ProductService.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto createCategory(CategoryDto categoryDto);
    List<CategoryDto> getAllCategories(Boolean status);
    CategoryDto getCategoryById(Long id);
    CategoryDto update(Long id, CategoryDto categoryDto);
    void delete(Long id);
    void setToActive(Long id);
}
