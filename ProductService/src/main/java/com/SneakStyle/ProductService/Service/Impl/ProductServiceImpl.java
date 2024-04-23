package com.SneakStyle.ProductService.Service.Impl;

import com.SneakStyle.ProductService.Repository.CategoryRepository;
import com.SneakStyle.ProductService.Repository.ProductRepository;
import com.SneakStyle.ProductService.Service.ProductService;
import com.SneakStyle.ProductService.dto.ProductDto;
import com.SneakStyle.ProductService.exception.RecordNotFoundException;
import com.SneakStyle.ProductService.model.Category;
import com.SneakStyle.ProductService.model.Product;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public ProductDto createProduct(ProductDto productDto) {
        Product product = toEntity(productDto);
        product.setStatus(true);

        Set<Category> categories = product.getCategories().stream()
                .map(category -> categoryRepository.findByNameAndStatusIsTrue(category.getName())
                        .orElseThrow(() -> new RecordNotFoundException(String.format("Category Not found by name => %s", category.getName()))))
                .collect(Collectors.toSet());

        product.setCategories(categories);
        Product createdProduct = productRepository.save(product);
        return toDto(createdProduct);
    }

    @Override
    public List<ProductDto> getAllProducts(Boolean status) {
        List<Product> productList = productRepository.findAllByStatusOrderByIdDesc(status);
        return productList.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getAllProductsByCategoryName(String categoryName) {
        List<Product> products = productRepository.findAllByCategories_NameAndStatusIsTrue(categoryName);
        return products.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Product Not found at id => %d", id)));
        return toDto(product);
    }

    @Override
    @Transactional
    public ProductDto update(Long id, ProductDto productDto) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Product Not found at id => %d", id)));

        existingProduct.setName(productDto.getName());
        existingProduct.setBrand(productDto.getBrand());
        existingProduct.setDescription(productDto.getDescription());
        existingProduct.setPrice(productDto.getPrice());
        existingProduct.setImageUrl(productDto.getImageUrl());
        existingProduct.getCategories().removeIf(category -> !productDto.getCategories().contains(category));

        Set<Category> categories = productDto.getCategories().stream()
                .map(category -> categoryRepository.findById(category.getId())
                        .orElseThrow(() -> new RecordNotFoundException(String.format("Category Not found by name => %s", category.getId()))))
                .collect(Collectors.toSet());

        existingProduct.setCategories(categories);
        Product updatedProduct = productRepository.save(existingProduct);
        return toDto(updatedProduct);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Product Not found at id => %d", id)));
        productRepository.setStatusWhereId(product.getId(), false);
    }

    @Override
    @Transactional
    public void setToActive(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Product Not found at id => %d", id)));
        productRepository.setStatusWhereId(product.getId(), true);
    }

    public ProductDto toDto(Product product){
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .brand(product.getBrand())
                .price(product.getPrice())
                .description(product.getDescription())
                .imageUrl(product.getImageUrl())
                .createdAt(product.getCreatedAt())
                .status(product.getStatus())
                .categories(product.getCategories())
                .build();
    }

    public Product toEntity(ProductDto productDto){
        return Product.builder()
                .id(productDto.getId())
                .name(productDto.getName())
                .brand(productDto.getBrand())
                .price(productDto.getPrice())
                .description(productDto.getDescription())
                .imageUrl(productDto.getImageUrl())
                .createdAt(productDto.getCreatedAt())
                .status(productDto.getStatus())
                .categories(productDto.getCategories())
                .build();
    }
}
