package com.SneakStyle.OrderService.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Long id;
    private LocalDateTime createdAt;
    private String name;
    private String brand;
    private Double price;
    private String description;
    private String imageUrl;
    private Boolean status;
    private Set<CategoryDto> categories = new HashSet<>();
}
