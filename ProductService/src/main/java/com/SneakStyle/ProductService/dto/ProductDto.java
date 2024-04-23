package com.SneakStyle.ProductService.dto;

import com.SneakStyle.ProductService.model.Category;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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

    @NotBlank(message = "Name cannot be null.")
    private String name;

    @NotBlank(message = "Brand cannot be null.")
    private String brand;

    @NotNull(message = "Price cannot be null.")
    private Double price;

    private String description;
    private String imageUrl;
    private Boolean status;
    private Set<Category> categories = new HashSet<>();
}
