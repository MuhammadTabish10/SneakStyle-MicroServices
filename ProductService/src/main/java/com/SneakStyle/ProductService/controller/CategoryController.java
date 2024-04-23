package com.SneakStyle.ProductService.controller;

import com.SneakStyle.ProductService.Service.CategoryService;
import com.SneakStyle.ProductService.dto.CategoryDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/category")
    public ResponseEntity<CategoryDto> addCategory(@Valid @RequestBody CategoryDto categoryDto) {
        CategoryDto category = categoryService.createCategory(categoryDto);
        return ResponseEntity.ok(category);
    }

    @GetMapping("/category")
    public ResponseEntity<List<CategoryDto>> getAllCategories(@RequestParam(name = "status") Boolean status) {
        List<CategoryDto> categoryDtoList = categoryService.getAllCategories(status);
        return ResponseEntity.ok(categoryDtoList);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long id) {
        CategoryDto categoryDto = categoryService.getCategoryById(id);
        return ResponseEntity.ok(categoryDto);
    }

    @DeleteMapping("/category/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/category/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable Long id, @RequestBody CategoryDto categoryDto) {
        CategoryDto category = categoryService.update(id, categoryDto);
        return ResponseEntity.ok(category);
    }

    @PatchMapping("/category/{id}/status")
    public ResponseEntity<Void> setCategoryStatusToActiveById(@PathVariable Long id) {
        categoryService.setToActive(id);
        return ResponseEntity.ok().build();
    }
}
