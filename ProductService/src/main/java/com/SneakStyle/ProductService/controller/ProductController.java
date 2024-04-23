package com.SneakStyle.ProductService.controller;

import com.SneakStyle.ProductService.Service.ProductService;
import com.SneakStyle.ProductService.dto.ProductDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("/product")
    public ResponseEntity<ProductDto> addProduct(@Valid @RequestBody ProductDto productDto) {
        ProductDto product = productService.createProduct(productDto);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/product")
    public ResponseEntity<List<ProductDto>> getAllProducts(@RequestParam(name = "status") Boolean status) {
        List<ProductDto> productDtoList = productService.getAllProducts(status);
        return ResponseEntity.ok(productDtoList);
    }

    @GetMapping("/product/category")
    public ResponseEntity<List<ProductDto>> getAllProductsByCategory(@RequestParam(name = "category") String category) {
        List<ProductDto> productDtoList = productService.getAllProductsByCategoryName(category);
        return ResponseEntity.ok(productDtoList);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        ProductDto productDto = productService.getProductById(id);
        return ResponseEntity.ok(productDto);
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto) {
        ProductDto product = productService.update(id, productDto);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/product/{id}/status")
    public ResponseEntity<Void> setProductStatusToActiveById(@PathVariable Long id) {
        productService.setToActive(id);
        return ResponseEntity.ok().build();
    }
}
