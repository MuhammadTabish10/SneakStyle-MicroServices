package com.SneakStyle.ProductService.Service;

import com.SneakStyle.ProductService.dto.ProductDto;

import java.util.List;

public interface ProductService {
    ProductDto createProduct(ProductDto productDto);
    List<ProductDto> getAllProducts(Boolean status);
    List<ProductDto> getAllProductsByCategoryName(String categoryName);
    ProductDto getProductById(Long id);
    ProductDto update(Long id, ProductDto productDto);
    void delete(Long id);
    void setToActive(Long id);
}
